package fr.cnalps.squaregames.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cnalps.squaregames.request.GameCreationParamsRequest;
import fr.cnalps.squaregames.request.GameMoveTokenParamsRequest;
import fr.cnalps.squaregames.service.GameServiceInterface;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InconsistentGameDefinitionException;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameServiceInterface gameServiceInterface;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody GameCreationParamsRequest gameRequest,
            @RequestHeader("X-NamePlayer1") String playerName1, @RequestHeader("X-NamePlayer2") String playerName2) {
        try {
            UUID gameId = gameServiceInterface.create(gameRequest, playerName1, playerName2);

            return ResponseEntity.ok(gameId.toString());

        } catch (IllegalArgumentException exeption) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exeption.getMessage());
        }
    }


    @PostMapping("/{gameId}/move-token")
    public ResponseEntity<String> moveToken(@PathVariable UUID gameId,
            @RequestBody GameMoveTokenParamsRequest gameMoveRequest) {
        try {
            boolean isMooved = gameServiceInterface.moveToken(gameId, gameMoveRequest);
            if (isMooved) {
                return ResponseEntity.ok("Le pion c'est bien déplacé");
            }

            return ResponseEntity.ok("Impossible de déplacer le pion");

        } catch (IllegalArgumentException | InvalidPositionException exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exception.getMessage());
        }

    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> deleteGame(@PathVariable UUID gameId) {

        gameServiceInterface.deleteGame(gameId);
        return ResponseEntity.ok("Game supprimé");

    }

    @GetMapping("/{gameId}")
    public Game getGameById(@PathVariable UUID gameId) {
        try {
            return gameServiceInterface.getGameByid(gameId);
        } catch (DataAccessException | InconsistentGameDefinitionException exception) {
            System.out.println("Erreur get game" + exception.getMessage());
            return null;
        }
    }

}
