#########################################################################################################################################
# setting up an new user:
#########################################################################################################################################

DROP USER IF EXISTS 'spring-security'@'localhost';
CREATE USER 'spring-security'@'localhost' IDENTIFIED BY 'spring-security';
#GRANT ALL PRIVILEGES ON * . * TO 'spring-security'@'localhost';
ALTER USER 'spring-security'@'localhost' IDENTIFIED WITH mysql_native_password BY 'spring-security';


#########################################################################################################################################
# setting up a new database:
#########################################################################################################################################

DROP DATABASE IF EXISTS `spring-security`;
CREATE DATABASE `spring-security`;
USE `spring-security`;


#########################################################################################################################################
# creating new tables for the database:
#########################################################################################################################################

DROP TABLE IF EXISTS `spring-security`.`meeting`;
CREATE TABLE `spring-security`.`meeting` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `date_time` datetime NOT NULL,
  `display` tinyint(1) DEFAULT '1',
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);

#########################################################################################################################################

DROP TABLE IF EXISTS `spring-security`.`user`;
CREATE TABLE `spring-security`.`user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `password` char(60) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `company` varchar(100) NOT NULL,
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);

#########################################################################################################################################

DROP TABLE IF EXISTS `spring-security`.`role`;
CREATE TABLE `spring-security`.`role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL UNIQUE,
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);

#########################################################################################################################################

DROP TABLE IF EXISTS `spring-security`.`user_role`;
CREATE TABLE `spring-security`.`user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL UNIQUE,
  `id_role` INT NOT NULL,
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_user`) REFERENCES `spring-security`.`user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`id_role`) REFERENCES `spring-security`.`role`(`id`) ON DELETE CASCADE,
  UNIQUE KEY `id_UNIQUE` (`id`)
);

#########################################################################################################################################

DROP TABLE IF EXISTS `spring-security`.`meeting_user`;
CREATE TABLE `spring-security`.`meeting_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_meeting` INT NOT NULL,
  `id_user` INT NOT NULL,
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_meeting`) REFERENCES `spring-security`.`meeting`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`id_user`) REFERENCES `spring-security`.`user`(`id`) ON DELETE CASCADE,
  UNIQUE KEY `id_UNIQUE` (`id`)
);


#########################################################################################################################################
# filling up the new tables with sample data:
#########################################################################################################################################

INSERT INTO `spring-security`.`meeting` (
    `name`,
    `date_time`) 
VALUES
	('Thementag - Wie leite ich ein Thema richtig ein?', '2020-10-01 10:00:00'),
	('Ernährungsberatung', '2020-10-01 12:00:00'),
	('Workshop Datenverwaltung', '2020-10-01 14:00:00'),
	('Vortrag Algorithmen', '2020-10-20 10:30:00'),
	('Vortrag Datenstrukturen', '2020-10-20 12:30:00'),
	('SQL Einführung', '2020-10-06 08:30:00'),
	('Einführung in komplexe Systeme', '2020-10-06 15:00:00');

#########################################################################################################################################

INSERT INTO `spring-security`.`user` (
    `username`,
    `password`,
    `firstname`,
    `lastname`,
    `email`,
    `company`) 
VALUES
	('admin', '$2a$10$5L6RdmKXIm4QBNgNIV0kU.lNglfZ6IcWjy2efHS8t3OYK9ohQ2LZK', 'admin', 'admin', 'admin', 'admin'),
	('jdun', '$2a$10$tdn0T9dQWeXSJ6NO/PGCe.2rrHfpd1BihEVADHVGqbzQffhF0bF6u', 'Jax', 'Dunlop', 'J.Dunlop@dunlop-gmbh.com', 'Dunlop'),
	('mmus', '$2a$10$tdn0T9dQWeXSJ6NO/PGCe.2rrHfpd1BihEVADHVGqbzQffhF0bF6u', 'Max', 'Mustermann', 'M.Mustermann@beispielfirma.com', 'Beispielfirma'),
	('gdin', '$2a$10$tdn0T9dQWeXSJ6NO/PGCe.2rrHfpd1BihEVADHVGqbzQffhF0bF6u', 'Gerda', 'Dinkel', 'G.Dinkel@email.com', 'Post'),
	('sfle', '$2a$10$tdn0T9dQWeXSJ6NO/PGCe.2rrHfpd1BihEVADHVGqbzQffhF0bF6u', 'Sammy', 'Fleischbällchen', 'S.Fleisch@hotmail.com', 'Burgerking'),
	('smue', '$2a$10$tdn0T9dQWeXSJ6NO/PGCe.2rrHfpd1BihEVADHVGqbzQffhF0bF6u', 'Sabine', 'Müller', 'S.Müller@gmail.com', 'DHL');
    
#########################################################################################################################################

INSERT INTO `spring-security`.`role` (
    `name`) 
VALUES
	('ROLE_ADMIN'),
	('ROLE_USER');
    
#########################################################################################################################################

INSERT INTO `spring-security`.`user_role` (
    `id_user`,
    `id_role`)
VALUES
	(1, 1),
	(2, 2),
	(3, 2),
	(4, 2),
	(5, 2),
	(6, 2);
    
#########################################################################################################################################

INSERT INTO `spring-security`.`meeting_user` (
    `id_meeting`,
    `id_user`) 
VALUES
	(1, 2),
	(2, 2),
	(3, 2),
	(1, 3),
	(3, 3),
	(5, 3),
	(2, 4),
	(3, 4),
	(4, 4),
	(3, 5),
	(4, 5),
	(6, 5),
	(4, 6),
	(6, 6),
	(7, 6);