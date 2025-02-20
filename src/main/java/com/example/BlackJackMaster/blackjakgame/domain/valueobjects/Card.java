package com.example.BlackJackMaster.blackjakgame.domain.valueobjects;

import com.example.BlackJackMaster.blackjakgame.domain.enums.CardValue;
import com.example.BlackJackMaster.blackjakgame.domain.enums.Suit;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Card {
    private Suit suit;
    private CardValue value;

    public Card(Suit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public CardValue getValue() {
        return value;
    }

    public void setValue(CardValue value) {
        this.value = value;
    }

    public static List<Card> createCards() {
        return Arrays.stream(Suit.values())
                .flatMap(suit -> Arrays.stream(CardValue.values())
                        .map(cardValue -> new Card(suit, cardValue)))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("Card{suit='%s', cardValue='%s'}", suit, value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;
        return suit == card.suit && value == card.value;
    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
