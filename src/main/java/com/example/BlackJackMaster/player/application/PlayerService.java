package com.example.BlackJackMaster.player.application;

import com.example.BlackJackMaster.blackjakgame.application.exceptions.NicknameAlreadyInUse;
import com.example.BlackJackMaster.player.application.exceptions.PlayerNotFoundException;
import com.example.BlackJackMaster.player.domain.Player;
import com.example.BlackJackMaster.player.domain.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Flux<Player> getAllPlayers() {
        return this.playerRepository
                .findAll()
                .filter(player -> !player.isDeleted());
    }

    public Mono<Player> getById(String id) {
        return this.playerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(() -> new PlayerNotFoundException(
                        "Player with id " + id + " not found"
                )));
    }

    public Mono<Player> findByNickname(String nickname) {
        return this.playerRepository
                .findByNickname(nickname)
                .switchIfEmpty(Mono.error(() -> new PlayerNotFoundException(
                        "Player with nickname " + nickname + " not found"
                )));

    }

    public Mono<Player> updatePlayer(
            String id,
            String nickname,
            double amount
    ) {
        return this.playerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(() -> new PlayerNotFoundException(
                        "Player with id " + id + " not found"
                )))
                .flatMap(player -> {
                    if(!player.getNickname().equals(nickname)){
                        return this.playerRepository
                                .findByNickname(nickname)
                                .flatMap(existingPlayer
                                        -> Mono.error(new NicknameAlreadyInUse("Nickname " + nickname + " is taken")))
                                .then(Mono.just(player));
                    }
                    return Mono.just(player);
                })
                .flatMap(player -> {
                    if(player.getNickname() != null && !player.getNickname().equals(nickname)){
                        player.updateNickname(nickname);
                    }
                    if(player.getBalance() != null && amount > 0){
                        player.updateBalance(amount);
                    }
                    return playerRepository.save(player);
                });
    }

    public Mono<Player> deletePlayer(String id){
        return this.playerRepository
                .findById(id)
                .switchIfEmpty(Mono.<Player>error(() -> new PlayerNotFoundException(
                        "Player not found"
                )))
                .flatMap(player -> {
                    if(!player.isDeleted()){
                        player.delete();
                    }
                    return playerRepository.save(player);
                });

    }
}
