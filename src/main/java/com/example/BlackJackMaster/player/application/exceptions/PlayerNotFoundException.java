package com.example.BlackJackMaster.player.application.exceptions;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
