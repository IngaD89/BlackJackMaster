package com.example.BlackJackMaster.blackjakgame.application.exceptions;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String message) {
        super(message);
    }
}
