package example.demo.config;

import java.util.stream.IntStream;

public class draft {

    public static void main(String[] args) {
        IntStream.range(1,10).map(i -> i*i).forEach(i -> System.out.println(i));
    }
}
