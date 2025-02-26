package com.example.BlackJackMaster.player.web.dto;

public class PlayerDTO {
    private String nickname;
    private double balanceAmount;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
