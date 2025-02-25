package com.example.BlackJackMaster.player.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {
    public Mono<Player> findByNickname(String nickname);
}
