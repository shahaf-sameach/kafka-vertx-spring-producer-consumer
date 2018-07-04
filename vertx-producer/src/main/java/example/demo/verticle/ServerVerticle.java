package example.demo.verticle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.demo.config.ApplicationConfiguration;
import example.demo.data.Payload;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import io.vertx.kafka.client.producer.RecordMetadata;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Slf4j
@Component
public class ServerVerticle extends AbstractVerticle {

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    private KafkaProducer producer;

    @Override
    public void start() throws Exception {
        super.start();

        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfiguration.bootstrapServers());
        config.put(ProducerConfig.ACKS_CONFIG, "1");
        producer = KafkaProducer.createShared(vertx, "the-producer", config, String.class, String.class);

        vertx.createHttpServer().requestHandler(router()::accept).listen(Integer.parseInt(applicationConfiguration.port()), res -> {
            if (res.succeeded()) {
                log.info("Server is online, listening on port " + applicationConfiguration.port() );
            } else {
                log.info("Server Failed to bind!");
            }
        });
    }

    private Router router() {
        Router router = Router.router(vertx);

        router.route("/msg").handler(routingContext -> {

            String msg = routingContext.request().getParam("Payload");
            HttpServerResponse response = routingContext.response();

            if (msg == null)
                response.setStatusCode(400).end();
            else {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String data = mapper.writeValueAsString(new Payload(msg));
                    KafkaProducerRecord<String, String> record = KafkaProducerRecord.create("test", data);
                    producer.write(record, res -> {
                        AsyncResult<RecordMetadata> asyncRes = (AsyncResult<RecordMetadata>) res;
                        if (asyncRes.succeeded())
                            response.setStatusCode(200).end();
                        else
                            response.setStatusCode(503).end();
                    });
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    response.setStatusCode(500).end();
                }
            }
        });

        return router;
    }

    @Override
    public void stop() throws Exception {
        if (producer != null) {
            producer.close(voidAsyncResult -> {
                AsyncResult res = (AsyncResult) voidAsyncResult;
                if (res.succeeded())
                    log.info("Producer is now closed");
                else
                    log.error("error closing producer");
            });
        }
    }

}
