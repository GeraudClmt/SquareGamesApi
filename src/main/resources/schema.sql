
-- Table des jeux
CREATE TABLE IF NOT EXISTS `game_model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(500) NOT NULL,
  `board_size` INT NOT NULL,
  `factory_id` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`)
);

-- Table des joueurs
CREATE TABLE IF NOT EXISTS `player_model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(500) NOT NULL,
  `game_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_players_game` FOREIGN KEY (`game_id`) 
      REFERENCES `game_model`(`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

-- Table des tokens retirés
CREATE TABLE IF NOT EXISTS `removed_token_model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `player_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  `token_name` VARCHAR(500) NOT NULL,
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_removed_token_model_player` FOREIGN KEY (`player_id`) 
      REFERENCES `player_model`(`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT `fk_removed_token_model_game` FOREIGN KEY (`game_id`) 
      REFERENCES `game_model`(`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

-- Table des tokens sur le plateau
CREATE TABLE IF NOT EXISTS `board_token_model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `player_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  `token_name` VARCHAR(500) NOT NULL,
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_board_token_model_player` FOREIGN KEY (`player_id`) 
      REFERENCES `player_model`(`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT `fk_board_token_model_game` FOREIGN KEY (`game_id`) 
      REFERENCES `game_model`(`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

-- Index pour rechercher rapidement par UUID ou player_id
CREATE INDEX idx_games_uuid ON `game_model` (`uuid`);
CREATE INDEX idx_players_uuid ON `player_model` (`uuid`);
CREATE INDEX idx_removed_tokens_player_id ON `removed_token_model` (`player_id`);
CREATE INDEX idx_board_tokens_player_id ON `board_token_model` (`player_id`);
