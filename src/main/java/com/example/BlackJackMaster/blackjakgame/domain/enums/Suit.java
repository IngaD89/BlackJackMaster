package com.example.BlackJackMaster.blackjakgame.domain.enums;

public enum Suit {
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES;

    @Override
    public String toString() {
        return this.name();
    }
}
