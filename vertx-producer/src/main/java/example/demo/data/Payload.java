package example.demo.data;

public class Payload {

    private long timeStamp;
    private String message;

    public Payload(){
        this.timeStamp = System.currentTimeMillis();
    }

    public Payload(String message){
        this();
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
