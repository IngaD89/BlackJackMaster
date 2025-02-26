package com.example.BlackJackMaster.blackjakgame.web.dto;

import com.example.BlackJackMaster.player.domain.valueobjects.Balance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
    private String nickname;
    private double balance;

    public String getNickname() {
        return nickname;
    }

    public double getBalance() {
        return balance;
    }
}
