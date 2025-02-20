package com.example.BlackJackMaster.blackjakgame.domain.exceptions;

public class DuplicateCardException extends RuntimeException{
    public DuplicateCardException(String message) {
        super(message);
    }
}
