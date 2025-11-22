-- Insertion de 2 jeux
INSERT INTO `squaregames`.`games` (`uuid`, `board_size`, `factory_id`)
VALUES
    ('game-uuid-1', 3, 'factory-1'),
    ('game-uuid-2', 4, 'factory-2');

-- Récupération des IDs des jeux
-- (ici, on suppose que les IDs auto-increment sont 1 et 2)
SET @game1_id = 1;
SET @game2_id = 2;

-- Insertion de 2 joueurs pour le premier jeu
INSERT INTO `squaregames`.`players` (`uuid`, `game_id`, `token_name`)
VALUES
    ('player-uuid-1', @game1_id, 'X'),
    ('player-uuid-2', @game1_id, 'O');

-- Insertion de 2 joueurs pour le deuxième jeu
INSERT INTO `squaregames`.`players` (`uuid`, `game_id`, `token_name`)
VALUES
    ('player-uuid-3', @game2_id, 'X'),
    ('player-uuid-4', @game2_id, 'O');


-- Insertion de tokens pour le premier jeu (players 1 et 2)
INSERT INTO `squaregames`.`board_tokens` (`player_id`, `x`, `y`)
VALUES
    (1, 0, 0),  -- joueur 1, position (0,0)
    (1, 1, 1),  -- joueur 1, position (1,1)
    (2, 0, 1),  -- joueur 2, position (0,1)
    (2, 1, 0);  -- joueur 2, position (1,0)

-- Insertion de tokens pour le deuxième jeu (players 3 et 4)
INSERT INTO `squaregames`.`board_tokens` (`player_id`, `x`, `y`)
VALUES
    (3, 0, 0),  -- joueur 3, position (0,0)
    (3, 1, 1),  -- joueur 3, position (1,1)
    (4, 0, 1),  -- joueur 4, position (0,1)
    (4, 1, 0);  -- joueur 4, position (1,0)
