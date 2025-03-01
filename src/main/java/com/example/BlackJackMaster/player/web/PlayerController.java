package com.example.BlackJackMaster.player.web;

import com.example.BlackJackMaster.player.application.PlayerService;
import com.example.BlackJackMaster.player.domain.Player;
import com.example.BlackJackMaster.player.web.dto.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping()
    public Flux<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Player>> getPlayerById(
            String id
    ){
        return playerService.getById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Player>> updatePlayer(@PathVariable String id, @RequestBody PlayerDTO playerDTO){
        return playerService
                .updatePlayer(id, playerDTO.getNickname(), playerDTO.getBalanceAmount())
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Player>> deletePlayer(@PathVariable String id){
        return this.playerService
                .deletePlayer(id)
                .map(ResponseEntity::ok);
    }
}
