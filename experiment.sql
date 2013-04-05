CREATE DATABASE IF NOT EXISTS loddb;
USE loddb;

CREATE TABLE results (
	film INTEGER,
	user INTEGER,
	rank INTEGER,
	distance INTEGER,
	profile INTEGER,
	filmName VARCHAR(50),
	distanceName VARCHAR(30),
	profileName VARCHAR(30),
	PRIMARY KEY (film, user, distance, profile)
);
