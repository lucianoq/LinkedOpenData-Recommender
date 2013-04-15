DROP DATABASE IF EXISTS LODDB;
CREATE DATABASE IF NOT EXISTS LODDB;
USE LODDB;

CREATE TABLE RECOMMENDATIONS (
	DISTANCE INTEGER,
	PROFILE INTEGER,
	USER INTEGER,
	FILM INTEGER,
	RANK INTEGER,
	PRIMARY KEY (DISTANCE, PROFILE, USER, FILM )
);


CREATE TABLE RESULTS (
	DISTANCE INTEGER,
	PROFILE INTEGER,
	K INTEGER,
	USER INTEGER,
	TP INTEGER,
	TN INTEGER,
	FP INTEGER,
	FN INTEGER,
	TP_T INTEGER,
	TN_T INTEGER,
	FP_T INTEGER,
	FN_T INTEGER,
	SUM_INVERSE_RANK DOUBLE,
	SUM_INVERSE_RANK_T DOUBLE,
	IDEAL_INVERSE_RANK DOUBLE,
	IDEAL_INVERSE_RANK_T DOUBLE,
	PREC DOUBLE,
	PREC_T DOUBLE,
	MRR DOUBLE,
	MRR_T DOUBLE,
	PRIMARY KEY (DISTANCE, PROFILE, K, USER)
);


CREATE TABLE RESULTS_AGG (
	DISTANCE INTEGER,
	PROFILE INTEGER,
	K INTEGER,
	MICRO_PREC DOUBLE,
	MACRO_PREC DOUBLE,
	MICRO_PREC_T DOUBLE,
	MACRO_PREC_T DOUBLE,
	MICRO_MRR DOUBLE,
	MACRO_MRR DOUBLE,
	MICRO_MRR_T DOUBLE,
	MACRO_MRR_T DOUBLE,
	PRIMARY KEY (DISTANCE, PROFILE, K)
);


CREATE TABLE DISTANCE_NAMES (
	ID INTEGER,
	NAME VARCHAR(30),
	PRIMARY KEY (ID)
);

INSERT INTO DISTANCE_NAMES VALUES (0,'PASSANT_D');
INSERT INTO DISTANCE_NAMES VALUES (1,'PASSANT_I');
INSERT INTO DISTANCE_NAMES VALUES (2,'PASSANT_C');
INSERT INTO DISTANCE_NAMES VALUES (3,'NOSTRA');

CREATE TABLE PROFILE_NAMES (
	ID INTEGER,
	NAME VARCHAR(30),
	PRIMARY KEY (ID)
);

INSERT INTO PROFILE_NAMES VALUES (0,'SIMPLE');
INSERT INTO PROFILE_NAMES VALUES (1,'SIMPLE_NEGATIVE');
INSERT INTO PROFILE_NAMES VALUES (2,'VOTED_NOSTRA');
INSERT INTO PROFILE_NAMES VALUES (3,'VOTED_MUSTO');

CREATE TABLE FILM_NAMES (
	ID INTEGER,
	NAME VARCHAR(50),
	PRIMARY KEY (ID)
);


