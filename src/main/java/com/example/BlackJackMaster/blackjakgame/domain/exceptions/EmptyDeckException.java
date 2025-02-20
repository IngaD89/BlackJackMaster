package com.example.BlackJackMaster.blackjakgame.domain.exceptions;

public class EmptyDeckException extends RuntimeException{
    public EmptyDeckException(String message) {
        super(message);
    }
}
