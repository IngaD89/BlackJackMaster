package com.example.BlackJackMaster.blackjakgame.domain;

import com.example.BlackJackMaster.blackjakgame.domain.enums.CardVisibility;
import com.example.BlackJackMaster.blackjakgame.domain.enums.GameActions;
import com.example.BlackJackMaster.blackjakgame.domain.enums.GameResult;
import com.example.BlackJackMaster.blackjakgame.domain.enums.GameStatus;
import com.example.BlackJackMaster.blackjakgame.domain.exceptions.GameAlreadyDeletedException;
import com.example.BlackJackMaster.blackjakgame.domain.exceptions.GameAlreadyFinishedException;
import com.example.BlackJackMaster.blackjakgame.domain.exceptions.InvalidBetAmountException;
import com.example.BlackJackMaster.blackjakgame.domain.exceptions.InvalidGameActionException;
import com.example.BlackJackMaster.blackjakgame.domain.valueobjects.Deck;
import com.example.BlackJackMaster.blackjakgame.domain.valueobjects.Hand;
import com.example.BlackJackMaster.player.domain.exceptions.UserAlreadyDeletedException;

import java.time.Instant;

public class Game {
    private String id;
    private Instant startedAt;
    private Instant finishedAt;
    private GameStatus gameStatus;
    private GameActions gameActions;
    private GameResult gameResult;
    private String playerId;
    private double initialBalance;
    private int betAmount;
    private Hand playerHand;
    private Hand dealerHand;
    private Deck deck;
    private boolean deleted;

    public Game(String playerId, double initialBalance) {
        this.playerId = playerId;
        this.initialBalance = initialBalance;
        this.betAmount = 0;
        this.gameStatus = GameStatus.WAITING;
        this.gameResult = GameResult.NOT_DETERMINED;
        this.deck = Deck.createDeck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameActions getGameActions() {
        return gameActions;
    }

    public void setGameActions(GameActions gameActions) {
        this.gameActions = gameActions;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        if (betAmount <= 0) {
            throw new InvalidBetAmountException("Bet amount must be greater than zero");
        }
        this.betAmount = betAmount;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private void checkIfDeleted(){
        if(this.deleted){
            throw new GameAlreadyDeletedException();
        }
    }

    public void startGame() {
        if (gameStatus != GameStatus.WAITING) {
            throw new InvalidGameActionException("Game has already started or finished.");
        }
        this.startedAt = Instant.now();
        this.gameStatus = GameStatus.IN_PROGRESS;

        this.playerHand = new Hand().addCard(deck.dealCard()).addCard(deck.dealCard());
        this.dealerHand = new Hand().addCard(deck.dealCard()).addCard(deck.dealCard());

        this.dealerHand.getCards().get(1).setCardVisibility(CardVisibility.FACE_DOWN);
    }


    public Hand hit() {
        if (gameStatus != GameStatus.IN_PROGRESS) {
            throw new InvalidGameActionException("Cannot hit, game is not in progress.");
        }

        playerHand = playerHand.addCard(deck.dealCard());
        return playerHand;
    }

    public void stand() {
        if (gameStatus != GameStatus.IN_PROGRESS) {
            throw new InvalidGameActionException("Cannot stand, game is not in progress.");
        }

        Hand dealerFinalHand = dealerTurn(this);
        setDealerHand(dealerFinalHand);

    }


    public Hand dealerTurn(Game game) {
        Hand dealerHand = game.getDealerHand();
        while (dealerHand.getTotalValue() < 17) {
            dealerHand = dealerHand.addCard(game.getDeck().dealCard());
        }
        return dealerHand;
    }


    public void finishGame() {
        if (gameStatus == GameStatus.FINISHED) {
            throw new GameAlreadyFinishedException("Game has already finished.");
        }

        this.gameStatus = GameStatus.FINISHED;
        this.finishedAt = Instant.now();
    }

    public boolean isPlayerBusted(Hand playerHand) {
        return playerHand.getTotalValue() > 21;
    }


    public GameResult determineWinner(Game game) {
        int playerScore = game.getPlayerHand().getTotalValue();
        int dealerScore = game.getDealerHand().getTotalValue();

        if (playerScore > 21) {
            return GameResult.DEALER_WINS;
        }

        if (dealerScore > 21) {
            return GameResult.PLAYER_WINS;
        }

        if (playerScore > dealerScore) {
            return GameResult.PLAYER_WINS;
        }

        if (playerScore == dealerScore) {
            return GameResult.TIE;
        }

        return GameResult.DEALER_WINS;
    }

}

