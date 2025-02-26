package com.example.BlackJackMaster.player.web;

import com.example.BlackJackMaster.player.application.PlayerService;
import com.example.BlackJackMaster.player.domain.Player;
import com.example.BlackJackMaster.player.web.dto.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public Flux<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @PutMapping("/players/{id}")
    public Mono<Player> updatePlayer(@PathVariable String id, @RequestBody PlayerDTO playerDTO){
        return playerService.updatePlayer(id, playerDTO.getNickname(), playerDTO.getBalanceAmount());
    }

    @DeleteMapping("/players/{id}")
    public Mono<Player> deletePlayer(@PathVariable String id){
        return this.playerService
                .deletePlayer(id);
    }
}
