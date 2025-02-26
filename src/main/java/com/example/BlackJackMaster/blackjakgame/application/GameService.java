package com.example.BlackJackMaster.blackjakgame.application;

import com.example.BlackJackMaster.blackjakgame.application.exceptions.GameNotFoundException;
import com.example.BlackJackMaster.blackjakgame.application.exceptions.InsufficientBalanceException;
import com.example.BlackJackMaster.blackjakgame.domain.Game;
import com.example.BlackJackMaster.blackjakgame.domain.GameRepository;
import com.example.BlackJackMaster.blackjakgame.domain.enums.GameResult;
import com.example.BlackJackMaster.blackjakgame.domain.enums.GameStatus;
import com.example.BlackJackMaster.blackjakgame.domain.exceptions.InvalidBetAmountException;
import com.example.BlackJackMaster.blackjakgame.domain.exceptions.InvalidGameActionException;
import com.example.BlackJackMaster.blackjakgame.domain.valueobjects.Hand;
import com.example.BlackJackMaster.player.application.exceptions.PlayerNotFoundException;
import com.example.BlackJackMaster.player.domain.Player;
import com.example.BlackJackMaster.player.domain.PlayerRepository;
import com.example.BlackJackMaster.player.domain.valueobjects.Balance;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameService(
            GameRepository gameRepository,
            PlayerRepository playerRepository
    ) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    public Mono<Game> createGame(String nickname, double balance) {
        return playerRepository.findByNickname(nickname)
                .switchIfEmpty(Mono.defer(() -> {

                    Player newPlayer = new Player(nickname, new Balance(balance));
                    return playerRepository.save(newPlayer);
                }))
                .flatMap(player -> {

                    Game game = new Game(player.getId(), player.getBalance().getAmount());

                    return playerRepository.save(player)
                            .then(gameRepository.save(game));
                });
    }

    public Mono<Game> startGame(String gameId, int betAmount) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found")))  // Verificar que el juego existe
                .flatMap(game -> {
                    if (game.getGameStatus() != GameStatus.WAITING) {
                        return Mono.error(new InvalidGameActionException("Game is already in progress or finished"));
                    }

                    return playerRepository.findById(game.getPlayerId())
                            .flatMap(player -> {
                                if (betAmount <= 0 || betAmount > player.getBalance().getAmount()) {
                                    return Mono.error(new InvalidBetAmountException("Invalid bet amount"));
                                }

                                game.setBetAmount(betAmount);
                                player.getBalance().decreaseBalance(betAmount);

                                game.startGame();

                                return playerRepository.save(player)
                                        .then(gameRepository.save(game));
                            });
                });
    }


    public Mono<Game> hit(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found")))
                .flatMap(game -> {

                    Hand updatedPlayerHand = game.hit();

                    game.setPlayerHand(updatedPlayerHand);

                    if (game.isPlayerBusted(updatedPlayerHand)) {
                        return finishGame(game, GameResult.DEALER_WINS);
                    }

                    return gameRepository.save(game);
                });
    }


    public Mono<Game> stand(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found")))
                .flatMap(game -> {
                    game.stand();

                    GameResult result = game.determineWinner(game);

                    return finishGame(game, result);
                });
    }


    private Mono<Game> finishGame(Game game, GameResult result) {
        game.setGameResult(result);
        game.finishGame();

        if (result == GameResult.PLAYER_WINS) {
            return playerRepository.findById(game.getPlayerId())
                    .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found")))
                    .flatMap(player -> {
                        player.getBalance().increaseBalance(game.getBetAmount());
                        return playerRepository.save(player);
                    })
                    .flatMap(p -> gameRepository.save(game));
        }

        return gameRepository.save(game);
    }

    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found")))
                .flatMap(game -> {
                    game.setDeleted(true);
                    return gameRepository.save(game);
                })
                .then();
    }

    public Mono<Game> getGameById(String gameId) {
        return gameRepository.findById(gameId)
                .filter(game -> !game.isDeleted())
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found")));
    }
}


