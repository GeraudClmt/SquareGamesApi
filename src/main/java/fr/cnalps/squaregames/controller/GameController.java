package fr.cnalps.squaregames.controller;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    GameServiceInterface gameServiceInterface;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody GameCreationParamsRequest gameRequest) {
        try {
            UUID gameId = gameServiceInterface.create(gameRequest);
            
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

    @PostMapping("/{gameId}/move-token")
    public ResponseEntity<String> moveToken(@PathVariable UUID gameId, @RequestBody GameMoveTokenParamsRequest gameMoveRequest) {
        try{
            boolean isMooved = gameServiceInterface.moveToken(gameId, gameMoveRequest);
            if(isMooved){
                return ResponseEntity.ok("Le pion c'est bien déplacé");
            }

        }catch(IllegalArgumentException | InvalidPositionException exception){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exception.getMessage());
        }

        return ResponseEntity.ok("Impossible de déplacer le pion");
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
    
    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> deleteGame(@PathVariable UUID gameId){
        try {
            gameServiceInterface.deleteGame(gameId);
            return ResponseEntity.ok("La game est bien supprimé");

        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exception.getMessage());
        }
               
    }



}
