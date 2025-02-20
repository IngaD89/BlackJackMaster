package com.example.BlackJackMaster.player.domain.exceptions;

public class UserAlreadyDeletedException extends RuntimeException{
    public UserAlreadyDeletedException() {
        super("User already deleted");
    }
}
