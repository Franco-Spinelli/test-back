package com.challenge.challengePlants.Exception;

public class InvalidPasswordLengthException extends RuntimeException{
    public InvalidPasswordLengthException(String message) {
        super(message);
    }
}
