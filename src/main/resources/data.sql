-- ------------------------------
-- Insertion de jeux avec UUID
-- ------------------------------

INSERT INTO game_model (id, uuid, board_size, factory_id)
VALUES (1, '3fa85f64-5717-4562-b3fc-2c963f66afa6', 3, 'tictactoe');

INSERT INTO game_model (id, uuid, board_size, factory_id)
VALUES (2, '1c6b147e-3d8d-4f90-bc50-ec3b7d70a53f', 4, 'tictactoe');

-- ------------------------------
-- Insertion des joueurs pour le premier jeu
-- ------------------------------

INSERT INTO player_model (id, uuid, game_id)
VALUES (1, 'd290f1ee-6c54-4b01-90e6-d701748f0851', 1);

INSERT INTO player_model (id, uuid, game_id)
VALUES (2, '2c6f7f7a-8e62-4a5d-9e9e-2f0ed2bdb6d8', 1);

-- ------------------------------
-- Insertion des joueurs pour le deuxième jeu
-- ------------------------------

INSERT INTO player_model (id, uuid, game_id)
VALUES (3, '6f1e1b3c-4c7a-4f7e-9e2b-1e5c5d5e3f2a', 2);

INSERT INTO player_model (id, uuid, game_id)
VALUES (4, '9e2c3d4b-5f6a-4b7d-8c9e-0f1a2b3c4d5e', 2);

-- ------------------------------
-- Insertion des tokens pour le premier jeu
-- ------------------------------

INSERT INTO board_token_model (id, player_id, game_id, token_name, x, y)
VALUES
    (1, 1, 1, 'X', 0, 0),
    (2, 1, 1, 'X', 1, 1),
    (3, 2, 1, 'O', 0, 1),
    (4, 2, 1, 'O', 1, 0);

-- ------------------------------
-- Insertion des tokens pour le deuxième jeu
-- ------------------------------

INSERT INTO board_token_model (id, player_id, game_id, token_name, x, y)
VALUES
    (5, 3, 2, 'X', 0, 0),
    (6, 3, 2, 'X', 1, 1),
    (7, 4, 2, 'O', 0, 1),
    (8, 4, 2, 'O', 1, 0);
