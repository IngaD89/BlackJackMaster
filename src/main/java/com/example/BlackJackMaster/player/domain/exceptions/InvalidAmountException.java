package com.example.BlackJackMaster.player.domain.exceptions;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(String message) {
        super(message);
    }
}
