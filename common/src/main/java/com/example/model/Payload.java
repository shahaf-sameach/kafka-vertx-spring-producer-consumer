package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payload {

    private long timeStamp;
    private String message;

    public Payload(String message) {
        this.message = message;
    }

}
