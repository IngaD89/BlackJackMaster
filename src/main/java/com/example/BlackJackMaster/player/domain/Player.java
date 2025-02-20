package com.example.BlackJackMaster.player.domain;

import com.example.BlackJackMaster.player.domain.exceptions.UserAlreadyDeletedException;
import com.example.BlackJackMaster.player.domain.valueobjects.Balance;

import java.time.Instant;

public class Player {
    private Long id;
    private Instant createdAt;
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

    public Long getId() {
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


    public Player changeNickname(String nickname){
        this.checkIfDeleted();
        if(!this.nickname.equals(nickname)){
            this.nickname = nickname;
            this.updatedAt = Instant.now();
        }
        return this;
    }

    public void incrementRankingScore() {
        this.rankingScore += 10;
    }

    public void incrementWonGameNumber() {
        this.wonGameNumber++;
    }
}
