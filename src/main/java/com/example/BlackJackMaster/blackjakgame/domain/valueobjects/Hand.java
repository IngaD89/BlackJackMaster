package com.example.BlackJackMaster.blackjakgame.domain.valueobjects;

import com.example.BlackJackMaster.blackjakgame.domain.enums.CardValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    private List<Card> cardList;

    public Hand() {
        this.cardList = new ArrayList<>();
    }

    public Hand(List<Card> cardList) {
        this.cardList = cardList;
    }

    public static Hand createEmptyHand() {
        return new Hand();
    }

    public Hand addCard(Card card) {
        List<Card> newCardList = new ArrayList<>(this.cardList);
        newCardList.add(card);
        return new Hand(newCardList);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(this.cardList);
    }

    public int getTotalValue() {
        int sum = 0;
        int aceCount = 0;

        for (Card card : cardList) {
            int cardValue = card.getValue().getNumericValue();
            sum += cardValue;

            if (card.getValue() == CardValue.ACE) {
                aceCount++;
            }
        }
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }

        return sum;
    }

}

