package com.example.BlackJackMaster.blackjakgame.web.exception;

import com.example.BlackJackMaster.blackjakgame.application.exceptions.GameNotFoundException;
import com.example.BlackJackMaster.blackjakgame.application.exceptions.InsufficientBalanceException;
import com.example.BlackJackMaster.blackjakgame.application.exceptions.NicknameAlreadyInUse;
import com.example.BlackJackMaster.blackjakgame.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameExceptionsHandler {

    @ExceptionHandler(InvalidBetAmountException.class)
    public ResponseEntity<String> handleInvalidBetAmount(InvalidBetAmountException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidGameActionException.class)
    public ResponseEntity<String> handleInvalidGameAction(InvalidGameActionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(GameAlreadyFinishedException.class)
    public ResponseEntity<String> handleGameAlreadyFinished(GameAlreadyFinishedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(GameAlreadyDeletedException.class)
    public ResponseEntity<String> handleGameAlreadyDeleted(GameAlreadyDeletedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyDeckException.class)
    public ResponseEntity<String> handleEmptyDeckException(EmptyDeckException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateCardException.class)
    public ResponseEntity<String> handleDuplicateCardException(DuplicateCardException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(NicknameAlreadyInUse.class)
    public ResponseEntity<String> handleNicknameAlreadyInUse(NicknameAlreadyInUse ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<String> handleGameNotFound(GameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
