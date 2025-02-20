package com.example.BlackJackMaster.blackjakgame.domain.valueobjects;

import com.example.BlackJackMaster.blackjakgame.domain.exceptions.DuplicateCardException;
import com.example.BlackJackMaster.blackjakgame.domain.exceptions.EmptyDeckException;
import com.example.BlackJackMaster.blackjakgame.domain.enums.CardValue;
import com.example.BlackJackMaster.blackjakgame.domain.enums.Suit;

import java.util.*;

public class Deck {
    private List<Card> cardList;

    public Deck(List<Card> cardList) {
        this.cardList = cardList;
    }

    public static Deck createDeck(){
        Set<Card> uniqueCards = new HashSet<>();
        List<Card> cardList = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (CardValue cardValue : CardValue.values()) {
                Card card = new Card(suit, cardValue);
                if (!uniqueCards.add(card)) {
                    throw new DuplicateCardException("Duplicate card detected: " + card);
                }
                cardList.add(card);
            }
        }

        Collections.shuffle(cardList);
        return new Deck(cardList);
    }

    public Card dealCard() {
        if (cardList.isEmpty()) {
            throw new EmptyDeckException("Deck is empty");
        }
        return cardList.removeFirst();
    }

    public int getRemainingCards() {
        return cardList.size();
    }

    public List<Card> getCardList() {
        return Collections.unmodifiableList(cardList);
    }
}
