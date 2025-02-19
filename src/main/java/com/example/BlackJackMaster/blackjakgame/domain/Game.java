package com.example.BlackJackMaster.blackjakgame.domain;

import com.example.BlackJackMaster.blackjakgame.domain.enums.GameActions;
import com.example.BlackJackMaster.blackjakgame.domain.enums.GameStatus;
import com.example.BlackJackMaster.blackjakgame.domain.valueobjects.Bet;
import com.example.BlackJackMaster.blackjakgame.domain.valueobjects.Hand;

import java.time.Instant;
import java.util.List;

public class Game {
    private Long id;
    private Long playerId;
    private Instant startedAt;
    private Instant finishedAt;
    private GameStatus gameStatus;
    private GameActions gameActions;
    private Bet bet;
    private List<Hand> handList;
    private boolean deleted;

}
