package com.example.BlackJackMaster.player.web.exception;

import com.example.BlackJackMaster.player.application.exceptions.PlayerNotFoundException;
import com.example.BlackJackMaster.player.domain.exceptions.InsufficientAmountException;
import com.example.BlackJackMaster.player.domain.exceptions.InvalidAmountException;
import com.example.BlackJackMaster.player.domain.exceptions.UserAlreadyDeletedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerExceptionsHandler {

    @ExceptionHandler(UserAlreadyDeletedException.class)
    public ResponseEntity<String> handleUserAlreadyDeletedException(UserAlreadyDeletedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<String> handleInvalidAmountException(InvalidAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientAmountException.class)
    public ResponseEntity<String> handleInsufficientAmountException(InsufficientAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> handlePlayerNotFoundException(PlayerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
