package com.example.BlackJackMaster.blackjakgame.domain.exceptions;

public class GameAlreadyFinishedException extends RuntimeException{
    public GameAlreadyFinishedException(String message) {
        super(message);
    }
}
