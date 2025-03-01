package com.example.BlackJackMaster.player.domain;

import com.example.BlackJackMaster.player.domain.exceptions.UserAlreadyDeletedException;
import com.example.BlackJackMaster.player.domain.valueobjects.Balance;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "players")
public class Player {
    @Id
    private String id;
    private final Instant createdAt;
    private Instant updatedAt;
    private String nickname;
    private Balance balance;
    private int rankingScore;
    private int wonGameNumber;
    private boolean deleted;

    public Player(String nickname, Balance balance) {
        //this.id = id;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.nickname = nickname;
        this.balance = balance;
        this.rankingScore = 0;
        this.wonGameNumber = 0;
        this.deleted = false;
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getNickname() {
        return nickname;
    }

    public Balance getBalance() {
        return balance;
    }

    public int getRankingScore() {
        return rankingScore;
    }

    public int getWonGameNumber() {
        return wonGameNumber;
    }

    public boolean isDeleted() {
        return deleted;
    }

    private void checkIfDeleted(){
        if(this.deleted){
            throw new UserAlreadyDeletedException();
        }
    }

    public void delete() {
        this.checkIfDeleted();
        this.deleted = true;
        this.updatedAt = Instant.now();
    }


    public Player updateNickname(String nickname){
        this.checkIfDeleted();
        if(!this.nickname.equals(nickname)){
            this.nickname = nickname;
            this.updatedAt = Instant.now();
        }
        return this;
    }

    public void updateBalance(double amount){
        this.checkIfDeleted();
        double updatedAmount = this.balance.getAmount() + amount;
        this.balance = new Balance(updatedAmount);
        this.updatedAt = Instant.now();
    }

    public void incrementRankingScore() {
        this.rankingScore += 10;
        this.updatedAt = Instant.now();
    }

    public void incrementWonGameNumber() {
        this.wonGameNumber++;
        this.updatedAt = Instant.now();
    }
}
