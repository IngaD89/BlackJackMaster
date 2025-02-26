package com.example.BlackJackMaster.blackjakgame.web;

import com.example.BlackJackMaster.blackjakgame.application.GameService;
import com.example.BlackJackMaster.blackjakgame.application.exceptions.GameNotFoundException;
import com.example.BlackJackMaster.blackjakgame.domain.Game;
import com.example.BlackJackMaster.blackjakgame.web.dto.GameDTO;
import com.example.BlackJackMaster.blackjakgame.web.dto.GameStartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Mono<ResponseEntity<Game>> createGame(
            @RequestBody GameDTO gameDTO
    ) {
        return gameService.createGame(gameDTO.getNickname(), gameDTO.getBalance())
                .map(ResponseEntity::ok);
    }


    @PostMapping("/{gameId}/start")
    public Mono<ResponseEntity<Game>> startGame(
            @PathVariable String gameId,
            @RequestBody GameStartDTO gameStartDTO
    ) {
        return gameService.startGame(gameId, gameStartDTO.getBetAmount())
                .map(ResponseEntity::ok);
    }


    @PostMapping("/{gameId}/hit")
    public Mono<ResponseEntity<Game>> hit(@PathVariable String gameId) {
        return gameService.hit(gameId)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{gameId}/stand")
    public Mono<ResponseEntity<Game>> stand(@PathVariable String gameId) {
        return gameService.stand(gameId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<Game>> getGameById(@PathVariable String gameId) {
        return gameService.getGameById(gameId)
                .map(ResponseEntity::ok)
                .onErrorResume(GameNotFoundException.class, e -> Mono.just(ResponseEntity.notFound().build()));
    }


    @PutMapping("/{gameId}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String gameId) {
        return gameService.deleteGame(gameId)
                .map(ResponseEntity::ok)
                .onErrorResume(GameNotFoundException.class,
                        e -> Mono.just(ResponseEntity.notFound().build()));
    }


}

