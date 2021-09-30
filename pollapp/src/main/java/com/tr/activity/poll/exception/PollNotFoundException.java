package com.tr.activity.poll.exception;

public class PollNotFoundException extends RuntimeException{

    public PollNotFoundException(String message) {
        super(message);
    }
}
