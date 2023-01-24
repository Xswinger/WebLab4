package com.lab.server.exceptions;

public class WrongUserDataException extends Exception{
    public WrongUserDataException(String message) {
        super(message);
    }
}
