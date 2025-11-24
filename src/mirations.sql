-- ------------------------------
-- Insertion de jeux avec UUID
-- ------------------------------

INSERT INTO `squaregames`.`games` (`uuid`, `board_size`, `factory_id`)
VALUES ('3fa85f64-5717-4562-b3fc-2c963f66afa6', 3, 'tictactoe');
SET @game1_id = LAST_INSERT_ID();

INSERT INTO `squaregames`.`games` (`uuid`, `board_size`, `factory_id`)
VALUES ('1c6b147e-3d8d-4f90-bc50-ec3b7d70a53f', 4, 'tictactoe');
SET @game2_id = LAST_INSERT_ID();

-- ------------------------------
-- Insertion des joueurs pour le premier jeu
-- ------------------------------

INSERT INTO `squaregames`.`players` (`uuid`, `game_id`, `token_name`)
VALUES ('d290f1ee-6c54-4b01-90e6-d701748f0851', @game1_id, 'X');
SET @player1_id = LAST_INSERT_ID();

INSERT INTO `squaregames`.`players` (`uuid`, `game_id`, `token_name`)
VALUES ('2c6f7f7a-8e62-4a5d-9e9e-2f0ed2bdb6d8', @game1_id, 'O');
SET @player2_id = LAST_INSERT_ID();

-- ------------------------------
-- Insertion des joueurs pour le deuxième jeu
-- ------------------------------

INSERT INTO `squaregames`.`players` (`uuid`, `game_id`, `token_name`)
VALUES ('6f1e1b3c-4c7a-4f7e-9e2b-1e5c5d5e3f2a', @game2_id, 'X');
SET @player3_id = LAST_INSERT_ID();

INSERT INTO `squaregames`.`players` (`uuid`, `game_id`, `token_name`)
VALUES ('9e2c3d4b-5f6a-4b7d-8c9e-0f1a2b3c4d5e', @game2_id, 'O');
SET @player4_id = LAST_INSERT_ID();

-- ------------------------------
-- Insertion des tokens pour le premier jeu
-- ------------------------------

INSERT INTO `squaregames`.`board_tokens` (`player_id`, `x`, `y`)
VALUES
    (@player1_id, 0, 0),
    (@player1_id, 1, 1),
    (@player2_id, 0, 1),
    (@player2_id, 1, 0);

-- ------------------------------
-- Insertion des tokens pour le deuxième jeu
-- ------------------------------

INSERT INTO `squaregames`.`board_tokens` (`player_id`, `x`, `y`)
VALUES
    (@player3_id, 0, 0),
    (@player3_id, 1, 1),
    (@player4_id, 0, 1),
    (@player4_id, 1, 0);
