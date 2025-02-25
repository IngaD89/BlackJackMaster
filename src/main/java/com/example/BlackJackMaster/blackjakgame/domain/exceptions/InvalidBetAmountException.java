package com.example.BlackJackMaster.blackjakgame.domain.exceptions;

public class InvalidBetAmountException extends RuntimeException{
    public InvalidBetAmountException(String message) {
        super(message);
    }
}
