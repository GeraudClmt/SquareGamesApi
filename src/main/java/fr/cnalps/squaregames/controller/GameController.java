package fr.cnalps.squaregames.controller;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.cnalps.squaregames.request.GameCreationParamsRequest;
import fr.cnalps.squaregames.request.GameMoveTokenParamsRequest;
import fr.cnalps.squaregames.service.GameServiceInterface;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameStatus;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    GameServiceInterface gameServiceInterface;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody GameCreationParamsRequest gameRequest) {
        try {
            UUID gameId = gameServiceInterface.create(gameRequest.getGameName(), gameRequest.getPlayerCount(),
                gameRequest.getBoardSize());
            
            return ResponseEntity.ok(gameId.toString());

        } catch (IllegalArgumentException exeption) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exeption.getMessage());
        }
    }

    @GetMapping("/{gameId}/status")
    public ResponseEntity<String> getGameStatus(@PathVariable UUID gameId) {
        try{
            GameStatus gameStatus =  gameServiceInterface.getStatus(gameId);

            return  ResponseEntity.ok(gameStatus.toString());

        }catch(IllegalArgumentException exeption){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exeption.getMessage());
        }
    }

    @GetMapping("/{gameId}/allowed-moves/from-cell")
    public ResponseEntity<Set<CellPosition>> getAllowedMovesWithCell(@PathVariable UUID gameId, @RequestParam int x, @RequestParam int y) {
        try{
            Set<CellPosition> allowedMoves = gameServiceInterface.getAllowedMovesWithCellPositions(gameId, x, y);
            return ResponseEntity.ok(allowedMoves);

        }catch(IllegalArgumentException exeption){
            return ResponseEntity.badRequest().body(Collections.emptySet());
        }
        
    }

    @GetMapping("/{gameId}/allowed-moves/from-remaining")
    public ResponseEntity<Set<CellPosition>> getAllowedMovesWithTokenName(@PathVariable UUID gameId, @RequestParam String tokenName) {
        try{
            Set<CellPosition> allowedMoves = gameServiceInterface.getAllowedMovesWithRemainingToken(gameId, tokenName);
            return ResponseEntity.ok(allowedMoves);

        }catch(IllegalArgumentException exeption){
            return ResponseEntity.badRequest().body(Collections.emptySet());
        }
        
    }

    @PutMapping("/move-token")
    public String putMethodName(@RequestBody GameMoveTokenParamsRequest gameMoveRequest) {
        return "dd";
    }

     @GetMapping("/{gameId}")
    public ResponseEntity<?> getGame(@PathVariable UUID gameId) {
        try{
            Game game =  gameServiceInterface.getGame(gameId);

            return  ResponseEntity.ok(game);

        }catch(IllegalArgumentException exeption){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exeption.getMessage());
        }
    }
    

}
