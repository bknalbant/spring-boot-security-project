package com.tr.activity.poll.exception;

public class UserAlreadyException extends RuntimeException{

    public UserAlreadyException(String message) {
        super(message);
    }
}
