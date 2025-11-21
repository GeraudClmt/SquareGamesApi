CREATE SCHEMA IF NOT EXISTS `squaregames`;

CREATE TABLE IF NOT EXISTS `squaregames`.`games` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uuid` varchar(500) NOT NULL,
  `board_size` int NOT NULL,
  `factory_id` varchar(500) NOT NULL,
  CONSTRAINT `pk_games_id` PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `squaregames`.`players` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uuid` varchar(500) NOT NULL,
  `game_id` int NOT NULL,
  `token_name` varchar(500) NOT NULL,
  CONSTRAINT `pk_table_2_id` PRIMARY KEY (`id`),
  CONSTRAINT `fk_players_game` FOREIGN KEY (`game_id`) 
      REFERENCES `games`(`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `squaregames`.`removed_tokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  CONSTRAINT `pk_table_3_id` PRIMARY KEY (`id`),
  CONSTRAINT `fk_removed_token_players` FOREIGN KEY (`player_id`) 
      REFERENCES `players`(`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `squaregames`.`board_tokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  CONSTRAINT `pk_table_3_id` PRIMARY KEY (`id`),
  CONSTRAINT `fk_board_tokens_players` FOREIGN KEY (`player_id`) 
      REFERENCES `players`(`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

ALTER TABLE `squaregames`.`board_tokens` ADD CONSTRAINT `board_tokens_player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `squaregames`.`players` (`id`);
ALTER TABLE `squaregames`.`players` ADD CONSTRAINT `players_game_id_fk` FOREIGN KEY (`game_id`) REFERENCES `squaregames`.`games` (`id`);
ALTER TABLE `squaregames`.`removed_tokens` ADD CONSTRAINT `removed_tokens_player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `squaregames`.`players` (`id`);

CREATE INDEX idx_games_uuid ON `squaregames`.`games` (`uuid`);
CREATE INDEX idx_players_uuid ON `squaregames`.`players` (`uuid`);
CREATE INDEX idx_removed_tokens_player_id ON `squaregames`.`removed_tokens` (`player_id`);
CREATE INDEX idx_board_tokens_player_id ON `squaregames`.`board_tokens` (`player_id`);