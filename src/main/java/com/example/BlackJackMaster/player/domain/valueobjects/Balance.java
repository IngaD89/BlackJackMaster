package com.example.BlackJackMaster.player.domain.valueobjects;

import com.example.BlackJackMaster.player.domain.exceptions.InsufficientAmountException;
import com.example.BlackJackMaster.player.domain.exceptions.InvalidAmountException;

public class Balance {

    private final double amount;

    public Balance(double amount) {
        if(amount < 0){
            throw new InsufficientAmountException("Cannot create Balance with a negative amount.");
        }
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public Balance increaseBalance(double amount){

        return new Balance(this.amount + amount);
    }

    public Balance decreaseBalance(double amount){

        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }
        if(this.amount < amount){
            throw new InsufficientAmountException("Insufficient balance to perform this operation.");
        }
        return new Balance(this.amount - amount);
    }
}
