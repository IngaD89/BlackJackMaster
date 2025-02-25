package com.example.BlackJackMaster.blackjakgame.domain.exceptions;

public class InvalidGameActionException extends RuntimeException{
    public InvalidGameActionException(String message) {
        super(message);
    }
}
