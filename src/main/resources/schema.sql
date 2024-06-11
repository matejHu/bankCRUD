CREATE DATABASE bank;

CREATE TABLE `account` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `balance` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `account` (`id`, `name`, `balance`) VALUES (1, 'Danko Popradsky', 1000);

