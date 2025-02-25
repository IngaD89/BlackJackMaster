package com.example.BlackJackMaster.blackjakgame.domain.exceptions;

public class GameAlreadyDeletedException extends RuntimeException{
    public GameAlreadyDeletedException() {
        super("Game already deleted");
    }
}
