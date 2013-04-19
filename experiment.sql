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
INSERT INTO DISTANCE_NAMES VALUES (1,'PASSANT_C');
INSERT INTO DISTANCE_NAMES VALUES (2,'PASSANT_D_W');
INSERT INTO DISTANCE_NAMES VALUES (3,'PASSANT_C_W');

CREATE TABLE PROFILE_NAMES (
	ID INTEGER,
	NAME VARCHAR(30),
	PRIMARY KEY (ID)
);

INSERT INTO PROFILE_NAMES VALUES (0,'NOT_WEIGHTED');
INSERT INTO PROFILE_NAMES VALUES (1,'WEIGHTED');

CREATE TABLE FILM_NAMES (
	ID INTEGER,
	NAME VARCHAR(50),
	PRIMARY KEY (ID)
);

INSERT INTO FILM_NAMES VALUES (4,"Get_Shorty_(film)");
INSERT INTO FILM_NAMES VALUES (6,"Shanghai_Triad");
INSERT INTO FILM_NAMES VALUES (8,"Babe_(film)");
INSERT INTO FILM_NAMES VALUES (9,"Dead_Man_Walking_(film)");
INSERT INTO FILM_NAMES VALUES (12,"The_Usual_Suspects");
INSERT INTO FILM_NAMES VALUES (13,"Mighty_Aphrodite");
INSERT INTO FILM_NAMES VALUES (22,"Braveheart");
INSERT INTO FILM_NAMES VALUES (23,"Taxi_Driver_(1954_film)");
INSERT INTO FILM_NAMES VALUES (24,"Rumble_in_the_Bronx");
INSERT INTO FILM_NAMES VALUES (26,"The_Brothers_McMullen");
INSERT INTO FILM_NAMES VALUES (27,"Bad_Boys_(1995_film)");
INSERT INTO FILM_NAMES VALUES (28,"Apollo_13_(film)");
INSERT INTO FILM_NAMES VALUES (30,"Belle_de_Jour_(film)");
INSERT INTO FILM_NAMES VALUES (31,"Crimson_Tide_(film)");
INSERT INTO FILM_NAMES VALUES (33,"Desperado_(film)");
INSERT INTO FILM_NAMES VALUES (35,"Free_Willy_2__The_Adventure_Home");
INSERT INTO FILM_NAMES VALUES (36,"Mad_Love_(1995_film)");
INSERT INTO FILM_NAMES VALUES (39,"Strange_Days_(film)");
INSERT INTO FILM_NAMES VALUES (41,"Billy_Madison");
INSERT INTO FILM_NAMES VALUES (44,"Dolores_Claiborne_(film)");
INSERT INTO FILM_NAMES VALUES (47,"Ed_Wood_(film)");
INSERT INTO FILM_NAMES VALUES (49,"I_Q__(film)");
INSERT INTO FILM_NAMES VALUES (51,"Legends_of_the_Fall");
INSERT INTO FILM_NAMES VALUES (54,"Outbreak_(film)");
INSERT INTO FILM_NAMES VALUES (55,"The_Professional_(film)");
INSERT INTO FILM_NAMES VALUES (56,"Pulp_Fiction");
INSERT INTO FILM_NAMES VALUES (58,"Quiz_Show");
INSERT INTO FILM_NAMES VALUES (62,"Stargate_(film)");
INSERT INTO FILM_NAMES VALUES (65,"What%27s_Eating_Gilbert_Grape");
INSERT INTO FILM_NAMES VALUES (66,"While_You_Were_Sleeping");
INSERT INTO FILM_NAMES VALUES (68,"The_Crow_(film)");
INSERT INTO FILM_NAMES VALUES (70,"Four_Weddings_and_a_Funeral");
INSERT INTO FILM_NAMES VALUES (73,"Maverick_(film)");
INSERT INTO FILM_NAMES VALUES (74,"Faster__Pussycat!_Kill!_Kill!");
INSERT INTO FILM_NAMES VALUES (77,"The_Firm_(1993_film)");
INSERT INTO FILM_NAMES VALUES (79,"The_Fugitive_(1993_film)");
INSERT INTO FILM_NAMES VALUES (80,"Hot_Shots!_Part_Deux");
INSERT INTO FILM_NAMES VALUES (81,"The_Hudsucker_Proxy");
INSERT INTO FILM_NAMES VALUES (83,"Much_Ado_About_Nothing_(1993_film)");
INSERT INTO FILM_NAMES VALUES (86,"The_Remains_of_the_Day_(film)");
INSERT INTO FILM_NAMES VALUES (87,"Searching_for_Bobby_Fischer");
INSERT INTO FILM_NAMES VALUES (88,"Sleepless_in_Seattle");
INSERT INTO FILM_NAMES VALUES (92,"True_Romance");
INSERT INTO FILM_NAMES VALUES (93,"Welcome_to_the_Dollhouse");
INSERT INTO FILM_NAMES VALUES (98,"The_Silence_of_the_Lambs_(film)");
INSERT INTO FILM_NAMES VALUES (100,"Fargo_(film)");
INSERT INTO FILM_NAMES VALUES (101,"Heavy_Metal_(film)");
INSERT INTO FILM_NAMES VALUES (107,"Moll_Flanders_(1996_film)");
INSERT INTO FILM_NAMES VALUES (118,"Twister_(1996_film)");
INSERT INTO FILM_NAMES VALUES (120,"Striptease_(film)");
INSERT INTO FILM_NAMES VALUES (123,"The_Frighteners");
INSERT INTO FILM_NAMES VALUES (125,"Phenomenon_(film)");
INSERT INTO FILM_NAMES VALUES (132,"The_Wizard_of_Oz_(1982_film)");
INSERT INTO FILM_NAMES VALUES (133,"Gone_with_the_Wind_(film)");
INSERT INTO FILM_NAMES VALUES (135,"2001__A_Space_Odyssey");
INSERT INTO FILM_NAMES VALUES (136,"Mr__Smith_Goes_to_Washington");
INSERT INTO FILM_NAMES VALUES (137,"Big_Night");
INSERT INTO FILM_NAMES VALUES (142,"Bedknobs_and_Broomsticks");
INSERT INTO FILM_NAMES VALUES (143,"The_Sound_of_Music_(film)");
INSERT INTO FILM_NAMES VALUES (145,"The_Lawnmower_Man_(film)");
INSERT INTO FILM_NAMES VALUES (147,"The_Long_Kiss_Goodnight");
INSERT INTO FILM_NAMES VALUES (148,"The_Ghost_and_the_Darkness");
INSERT INTO FILM_NAMES VALUES (150,"Swingers_(1996_film)");
INSERT INTO FILM_NAMES VALUES (153,"A_Fish_Called_Wanda");
INSERT INTO FILM_NAMES VALUES (163,"The_Return_of_the_Pink_Panther");
INSERT INTO FILM_NAMES VALUES (167,"Private_Benjamin");
INSERT INTO FILM_NAMES VALUES (170,"Cinema_Paradiso");
INSERT INTO FILM_NAMES VALUES (171,"Delicatessen_(film)");
INSERT INTO FILM_NAMES VALUES (173,"The_Princess_Bride_(film)");
INSERT INTO FILM_NAMES VALUES (174,"Raiders_of_the_Lost_Ark");
INSERT INTO FILM_NAMES VALUES (178,"12_Angry_Men_(1957_film)");
INSERT INTO FILM_NAMES VALUES (179,"A_Clockwork_Orange_(film)");
INSERT INTO FILM_NAMES VALUES (184,"Army_of_Darkness");
INSERT INTO FILM_NAMES VALUES (186,"The_Blues_Brothers_(film)");
INSERT INTO FILM_NAMES VALUES (188,"Full_Metal_Jacket");
INSERT INTO FILM_NAMES VALUES (189,"A_Grand_Day_Out");
INSERT INTO FILM_NAMES VALUES (191,"Amadeus_(film)");
INSERT INTO FILM_NAMES VALUES (196,"Dead_Poets_Society");
INSERT INTO FILM_NAMES VALUES (199,"The_Bridge_on_the_River_Kwai");
INSERT INTO FILM_NAMES VALUES (200,"The_Shining_(film)");
INSERT INTO FILM_NAMES VALUES (201,"Evil_Dead_II");
INSERT INTO FILM_NAMES VALUES (202,"Groundhog_Day_(film)");
INSERT INTO FILM_NAMES VALUES (205,"Patton_(film)");
INSERT INTO FILM_NAMES VALUES (210,"Indiana_Jones_and_the_Last_Crusade");
INSERT INTO FILM_NAMES VALUES (212,"The_Unbearable_Lightness_of_Being_(film)");
INSERT INTO FILM_NAMES VALUES (213,"A_Room_with_a_View_(film)");
INSERT INTO FILM_NAMES VALUES (214,"Pink_Floyd%E2%80%94The_Wall");
INSERT INTO FILM_NAMES VALUES (215,"Field_of_Dreams");
INSERT INTO FILM_NAMES VALUES (219,"A_Nightmare_on_Elm_Street_(2010_film)");
INSERT INTO FILM_NAMES VALUES (220,"The_Mirror_Has_Two_Faces");
INSERT INTO FILM_NAMES VALUES (221,"Breaking_the_Waves");
INSERT INTO FILM_NAMES VALUES (222,"Star_Trek__First_Contact");
INSERT INTO FILM_NAMES VALUES (225,"101_Dalmatians_(1996_film)");
INSERT INTO FILM_NAMES VALUES (226,"Die_Hard_2");
INSERT INTO FILM_NAMES VALUES (227,"Star_Trek_VI__The_Undiscovered_Country");
INSERT INTO FILM_NAMES VALUES (228,"Star_Trek_II__The_Wrath_of_Khan");
INSERT INTO FILM_NAMES VALUES (229,"Star_Trek_III__The_Search_for_Spock");
INSERT INTO FILM_NAMES VALUES (230,"Star_Trek_IV__The_Voyage_Home");
INSERT INTO FILM_NAMES VALUES (235,"Mars_Attacks!");
INSERT INTO FILM_NAMES VALUES (237,"Jerry_Maguire");
INSERT INTO FILM_NAMES VALUES (239,"Sneakers_(film)");
INSERT INTO FILM_NAMES VALUES (240,"Beavis_and_Butt_head_Do_America");
INSERT INTO FILM_NAMES VALUES (241,"The_Last_of_the_Mohicans_(1992_film)");
INSERT INTO FILM_NAMES VALUES (246,"Chasing_Amy");
INSERT INTO FILM_NAMES VALUES (248,"Grosse_Pointe_Blank");
INSERT INTO FILM_NAMES VALUES (249,"Austin_Powers__International_Man_of_Mystery");
INSERT INTO FILM_NAMES VALUES (250,"The_Fifth_Element");
INSERT INTO FILM_NAMES VALUES (253,"The_Pillow_Book_(film)");
INSERT INTO FILM_NAMES VALUES (254,"Batman_&_Robin_(film)");
INSERT INTO FILM_NAMES VALUES (255,"My_Best_Friend%27s_Wedding");
INSERT INTO FILM_NAMES VALUES (260,"Eve's_Bayou");
INSERT INTO FILM_NAMES VALUES (264,"Mimic_(film)");
INSERT INTO FILM_NAMES VALUES (265,"The_Hunt_for_Red_October_(film)");
INSERT INTO FILM_NAMES VALUES (268,"Chasing_Amy");
INSERT INTO FILM_NAMES VALUES (270,"Gattaca");
INSERT INTO FILM_NAMES VALUES (271,"Starship_Troopers_(film)");
INSERT INTO FILM_NAMES VALUES (272,"Good_Will_Hunting");
INSERT INTO FILM_NAMES VALUES (274,"Sabrina_(1954_film)");
INSERT INTO FILM_NAMES VALUES (275,"Sense_and_Sensibility_(film)");
INSERT INTO FILM_NAMES VALUES (276,"Leaving_Las_Vegas");
INSERT INTO FILM_NAMES VALUES (281,"The_River_Wild");
INSERT INTO FILM_NAMES VALUES (284,"Tin_Cup");
INSERT INTO FILM_NAMES VALUES (285,"Secrets_&_Lies_(film)");
INSERT INTO FILM_NAMES VALUES (286,"The_English_Patient_(film)");
INSERT INTO FILM_NAMES VALUES (287,"Marvin's_Room_(film)");
INSERT INTO FILM_NAMES VALUES (289,"Evita_(film)");
INSERT INTO FILM_NAMES VALUES (291,"Absolute_Power_(film)");
INSERT INTO FILM_NAMES VALUES (293,"Donnie_Brasco_(film)");
INSERT INTO FILM_NAMES VALUES (300,"Air_Force_One_(film)");
INSERT INTO FILM_NAMES VALUES (302,"L_A__Confidential_(film)");
INSERT INTO FILM_NAMES VALUES (304,"Fly_Away_Home");
INSERT INTO FILM_NAMES VALUES (305,"The_Ice_Storm_(film)");
INSERT INTO FILM_NAMES VALUES (306,"Mrs__Brown");
INSERT INTO FILM_NAMES VALUES (307,"The_Devil's_Advocate_(1997_film)");
INSERT INTO FILM_NAMES VALUES (308,"FairyTale__A_True_Story");
INSERT INTO FILM_NAMES VALUES (311,"The_Wings_of_the_Dove_(1997_film)");
INSERT INTO FILM_NAMES VALUES (313,"Titanic_(1997_film)");
INSERT INTO FILM_NAMES VALUES (315,"Apt_Pupil_(film)");
INSERT INTO FILM_NAMES VALUES (316,"As_Good_as_It_Gets");
INSERT INTO FILM_NAMES VALUES (317,"In_the_Name_of_the_Father_(film)");
INSERT INTO FILM_NAMES VALUES (318,"Schindler's_List");
INSERT INTO FILM_NAMES VALUES (325,"Crash_(1996_film)");
INSERT INTO FILM_NAMES VALUES (326,"G_I__Jane");
INSERT INTO FILM_NAMES VALUES (327,"Cop_Land");
INSERT INTO FILM_NAMES VALUES (328,"Conspiracy_Theory_(film)");
INSERT INTO FILM_NAMES VALUES (332,"Kiss_the_Girls_(film)");
INSERT INTO FILM_NAMES VALUES (334,"U_Turn_(1997_film)");
INSERT INTO FILM_NAMES VALUES (338,"Bean_(film)");
INSERT INTO FILM_NAMES VALUES (339,"Mad_City_(film)");
INSERT INTO FILM_NAMES VALUES (342,"The_Man_Who_Knew_Too_Little");
INSERT INTO FILM_NAMES VALUES (345,"Deconstructing_Harry");
INSERT INTO FILM_NAMES VALUES (346,"Jackie_Brown_(film)");
INSERT INTO FILM_NAMES VALUES (347,"Wag_the_Dog");
INSERT INTO FILM_NAMES VALUES (349,"Hard_Rain_(film)");
INSERT INTO FILM_NAMES VALUES (352,"Spice_World_(film)");
INSERT INTO FILM_NAMES VALUES (355,"Sphere_(film)");
INSERT INTO FILM_NAMES VALUES (357,"One_Flew_Over_the_Cuckoo's_Nest_(film)");
INSERT INTO FILM_NAMES VALUES (358,"Spawn_(film)");
INSERT INTO FILM_NAMES VALUES (362,"Blues_Brothers_2000");
INSERT INTO FILM_NAMES VALUES (364,"Ace_Ventura__When_Nature_Calls");
INSERT INTO FILM_NAMES VALUES (367,"Clueless_(film)");
INSERT INTO FILM_NAMES VALUES (369,"Black_Sheep_(1996_film)");
INSERT INTO FILM_NAMES VALUES (370,"Mary_Reilly_(film)");
INSERT INTO FILM_NAMES VALUES (371,"The_Bridges_of_Madison_County_(film)");
INSERT INTO FILM_NAMES VALUES (372,"Jeffrey_(film)");
INSERT INTO FILM_NAMES VALUES (373,"Judge_Dredd_(film)");
INSERT INTO FILM_NAMES VALUES (381,"Muriel's_Wedding");
INSERT INTO FILM_NAMES VALUES (383,"The_Flintstones_(film)");
INSERT INTO FILM_NAMES VALUES (387,"The_Age_of_Innocence_(film)");
INSERT INTO FILM_NAMES VALUES (388,"Beverly_Hills_Cop_III");
INSERT INTO FILM_NAMES VALUES (394,"Radioland_Murders");
INSERT INTO FILM_NAMES VALUES (395,"Robin_Hood__Men_in_Tights");
INSERT INTO FILM_NAMES VALUES (396,"Serial_Mom");
INSERT INTO FILM_NAMES VALUES (397,"Striking_Distance");
INSERT INTO FILM_NAMES VALUES (398,"Super_Mario_Bros__(film)");
INSERT INTO FILM_NAMES VALUES (400,"The_Little_Rascals_(film)");
INSERT INTO FILM_NAMES VALUES (403,"Batman_(1989_film)");
INSERT INTO FILM_NAMES VALUES (407,"Spy_Hard");
INSERT INTO FILM_NAMES VALUES (410,"Kingpin_(film)");
INSERT INTO FILM_NAMES VALUES (411,"The_Nutty_Professor_(1996_film)");
INSERT INTO FILM_NAMES VALUES (418,"Cinderella_(1950_film)");
INSERT INTO FILM_NAMES VALUES (419,"Mary_Poppins_(film)");
INSERT INTO FILM_NAMES VALUES (422,"Aladdin_and_the_King_of_Thieves");
INSERT INTO FILM_NAMES VALUES (425,"Bob_Roberts");
INSERT INTO FILM_NAMES VALUES (427,"To_Kill_a_Mockingbird_(film)");
INSERT INTO FILM_NAMES VALUES (428,"Harold_and_Maude");
INSERT INTO FILM_NAMES VALUES (430,"Duck_Soup_(1933_film)");
INSERT INTO FILM_NAMES VALUES (433,"Heathers");
INSERT INTO FILM_NAMES VALUES (435,"Butch_Cassidy_and_the_Sundance_Kid");
INSERT INTO FILM_NAMES VALUES (436,"An_American_Werewolf_in_London");
INSERT INTO FILM_NAMES VALUES (439,"Amityville__A_New_Generation");
INSERT INTO FILM_NAMES VALUES (442,"The_Amityville_Curse");
INSERT INTO FILM_NAMES VALUES (444,"The_Blob_(1988_film)");
INSERT INTO FILM_NAMES VALUES (445,"The_Body_Snatcher_(film)");
INSERT INTO FILM_NAMES VALUES (446,"Burnt_Offerings_(film)");
INSERT INTO FILM_NAMES VALUES (447,"Carrie_(1976_film)");
INSERT INTO FILM_NAMES VALUES (450,"Star_Trek_V__The_Final_Frontier");
INSERT INTO FILM_NAMES VALUES (451,"Grease_(film)");
INSERT INTO FILM_NAMES VALUES (452,"Jaws_2");
INSERT INTO FILM_NAMES VALUES (456,"Beverly_Hills_Ninja");
INSERT INTO FILM_NAMES VALUES (457,"Free_Willy_3__The_Rescue");
INSERT INTO FILM_NAMES VALUES (460,"The_Crossing_Guard");
INSERT INTO FILM_NAMES VALUES (463,"The_Secret_of_Roan_Inish");
INSERT INTO FILM_NAMES VALUES (467,"A_Bronx_Tale");
INSERT INTO FILM_NAMES VALUES (471,"Courage_Under_Fire");
INSERT INTO FILM_NAMES VALUES (473,"James_and_the_Giant_Peach_(film)");
INSERT INTO FILM_NAMES VALUES (475,"Trainspotting_(film)");
INSERT INTO FILM_NAMES VALUES (476,"The_First_Wives_Club");
INSERT INTO FILM_NAMES VALUES (478,"The_Philadelphia_Story_(film)");
INSERT INTO FILM_NAMES VALUES (483,"Casablanca_(film)");
INSERT INTO FILM_NAMES VALUES (484,"The_Maltese_Falcon_(1941_film)");
INSERT INTO FILM_NAMES VALUES (486,"Sabrina_(1995_film)");
INSERT INTO FILM_NAMES VALUES (490,"To_Catch_a_Thief");
INSERT INTO FILM_NAMES VALUES (491,"The_Adventures_of_Robin_Hood_(film)");
INSERT INTO FILM_NAMES VALUES (492,"East_of_Eden_(film)");
INSERT INTO FILM_NAMES VALUES (495,"Around_the_World_in_80_Days_(1956_film)");
INSERT INTO FILM_NAMES VALUES (497,"Bringing_Up_Baby");
INSERT INTO FILM_NAMES VALUES (499,"Cat_on_a_Hot_Tin_Roof_(film)");
INSERT INTO FILM_NAMES VALUES (500,"Fly_Away_Home");
INSERT INTO FILM_NAMES VALUES (502,"Bananas_(film)");
INSERT INTO FILM_NAMES VALUES (503,"The_Candidate_(1972_film)");
INSERT INTO FILM_NAMES VALUES (505,"Dial_M_for_Murder");
INSERT INTO FILM_NAMES VALUES (506,"Rebel_Without_a_Cause");
INSERT INTO FILM_NAMES VALUES (507,"A_Streetcar_Named_Desire_(1951_film)");
INSERT INTO FILM_NAMES VALUES (508,"The_People_vs__Larry_Flynt");
INSERT INTO FILM_NAMES VALUES (511,"Lawrence_of_Arabia_(film)");
INSERT INTO FILM_NAMES VALUES (512,"Wings_of_Desire");
INSERT INTO FILM_NAMES VALUES (514,"Annie_Hall");
INSERT INTO FILM_NAMES VALUES (518,"Miller's_Crossing");
INSERT INTO FILM_NAMES VALUES (520,"The_Great_Escape_(film)");
INSERT INTO FILM_NAMES VALUES (521,"The_Deer_Hunter");
INSERT INTO FILM_NAMES VALUES (522,"Down_by_Law_(film)");
INSERT INTO FILM_NAMES VALUES (524,"The_Great_Dictator");
INSERT INTO FILM_NAMES VALUES (526,"Ben_Hur_(1959_film)");
INSERT INTO FILM_NAMES VALUES (528,"The_Killing_Fields_(film)");
INSERT INTO FILM_NAMES VALUES (529,"My_Life_as_a_Dog");
INSERT INTO FILM_NAMES VALUES (530,"The_Man_Who_Would_Be_King_(film)");
INSERT INTO FILM_NAMES VALUES (532,"Kama_Sutra__A_Tale_of_Love");
INSERT INTO FILM_NAMES VALUES (535,"Addicted_to_Love_(film)");
INSERT INTO FILM_NAMES VALUES (537,"My_Own_Private_Idaho");
INSERT INTO FILM_NAMES VALUES (538,"Anastasia_(1997_film)");
INSERT INTO FILM_NAMES VALUES (541,"Mortal_Kombat_(film)");
INSERT INTO FILM_NAMES VALUES (544,"Things_to_Do_in_Denver_When_You%27re_Dead");
INSERT INTO FILM_NAMES VALUES (547,"The_Young_Poisoner's_Handbook");
INSERT INTO FILM_NAMES VALUES (548,"The_NeverEnding_Story_III");
INSERT INTO FILM_NAMES VALUES (549,"Rob_Roy_(film)");
INSERT INTO FILM_NAMES VALUES (550,"Die_Hard_with_a_Vengeance");
INSERT INTO FILM_NAMES VALUES (553,"A_Walk_in_the_Clouds");
INSERT INTO FILM_NAMES VALUES (558,"Heavenly_Creatures");
INSERT INTO FILM_NAMES VALUES (562,"The_Quick_and_the_Dead_(1995_film)");
INSERT INTO FILM_NAMES VALUES (567,"Wes_Craven's_New_Nightmare");
INSERT INTO FILM_NAMES VALUES (572,"Blown_Away_(1994_film)");
INSERT INTO FILM_NAMES VALUES (573,"Body_Snatchers_(1993_film)");
INSERT INTO FILM_NAMES VALUES (574,"Boxing_Helena");
INSERT INTO FILM_NAMES VALUES (575,"City_Slickers_II%3A_The_Legend_of_Curly's_Gold");
INSERT INTO FILM_NAMES VALUES (576,"Cliffhanger_(film)");
INSERT INTO FILM_NAMES VALUES (577,"Coneheads_(film)");
INSERT INTO FILM_NAMES VALUES (578,"Demolition_Man_(film)");
INSERT INTO FILM_NAMES VALUES (581,"Kalifornia");
INSERT INTO FILM_NAMES VALUES (588,"Beauty_and_the_Beast_(1991_film)");
INSERT INTO FILM_NAMES VALUES (590,"Hellraiser__Bloodline");
INSERT INTO FILM_NAMES VALUES (595,"The_Fan_(1996_film)");
INSERT INTO FILM_NAMES VALUES (596,"The_Hunchback_of_Notre_Dame_(1996_film)");
INSERT INTO FILM_NAMES VALUES (602,"An_American_in_Paris_(film)");
INSERT INTO FILM_NAMES VALUES (603,"Rear_Window_(1998_film)");
INSERT INTO FILM_NAMES VALUES (604,"It_Happened_One_Night");
INSERT INTO FILM_NAMES VALUES (606,"All_About_Eve");
INSERT INTO FILM_NAMES VALUES (607,"Rebecca_(1940_film)");
INSERT INTO FILM_NAMES VALUES (608,"Spellbound_(1945_film)");
INSERT INTO FILM_NAMES VALUES (611,"Laura_(1944_film)");
INSERT INTO FILM_NAMES VALUES (612,"Lost_Horizon_(1937_film)");
INSERT INTO FILM_NAMES VALUES (616,"Night_of_the_Living_Dead_(1990_film)");
INSERT INTO FILM_NAMES VALUES (617,"The_Blue_Angel");
INSERT INTO FILM_NAMES VALUES (624,"The_Three_Caballeros");
INSERT INTO FILM_NAMES VALUES (628,"Sleepers_(film)");
INSERT INTO FILM_NAMES VALUES (630,"The_Great_Race");
INSERT INTO FILM_NAMES VALUES (632,"Sophie's_Choice_(film)");
INSERT INTO FILM_NAMES VALUES (637,"The_Howling_(film)");
INSERT INTO FILM_NAMES VALUES (639,"The_Tin_Drum_(film)");
INSERT INTO FILM_NAMES VALUES (640,"The_Cook__the_Thief__His_Wife_&_Her_Lover");
INSERT INTO FILM_NAMES VALUES (641,"Paths_of_Glory");
INSERT INTO FILM_NAMES VALUES (642,"The_Grifters_(film)");
INSERT INTO FILM_NAMES VALUES (647,"Ran_(film)");
INSERT INTO FILM_NAMES VALUES (650,"The_Seventh_Seal");
INSERT INTO FILM_NAMES VALUES (652,"Rosencrantz_and_Guildenstern_Are_Dead");
INSERT INTO FILM_NAMES VALUES (653,"Touch_of_Evil");
INSERT INTO FILM_NAMES VALUES (655,"Stand_by_Me_(film)");
INSERT INTO FILM_NAMES VALUES (657,"The_Manchurian_Candidate_(1962_film)");
INSERT INTO FILM_NAMES VALUES (659,"Arsenic_and_Old_Lace_(film)");
INSERT INTO FILM_NAMES VALUES (660,"Fried_Green_Tomatoes_(film)");
INSERT INTO FILM_NAMES VALUES (662,"Somewhere_in_Time_(film)");
INSERT INTO FILM_NAMES VALUES (670,"Body_Snatchers_(1993_film)");
INSERT INTO FILM_NAMES VALUES (671,"Bride_of_Frankenstein");
INSERT INTO FILM_NAMES VALUES (672,"Candyman_(film)");
INSERT INTO FILM_NAMES VALUES (674,"Cat_People_(1942_film)");
INSERT INTO FILM_NAMES VALUES (676,"The_Crucible_(1996_film)");
INSERT INTO FILM_NAMES VALUES (679,"Conan_the_Barbarian_(1982_film)");
INSERT INTO FILM_NAMES VALUES (681,"Wishmaster_(film)");
INSERT INTO FILM_NAMES VALUES (686,"A_Perfect_World");
INSERT INTO FILM_NAMES VALUES (690,"Seven_Years_in_Tibet_(1997_film)");
INSERT INTO FILM_NAMES VALUES (692,"The_American_President");
INSERT INTO FILM_NAMES VALUES (696,"City_Hall_(film)");
INSERT INTO FILM_NAMES VALUES (699,"Little_Women_(1994_film)");
INSERT INTO FILM_NAMES VALUES (704,"The_House_of_the_Spirits_(film)");
INSERT INTO FILM_NAMES VALUES (719,"Canadian_Bacon");
INSERT INTO FILM_NAMES VALUES (720,"First_Knight");
INSERT INTO FILM_NAMES VALUES (721,"Mallrats");
INSERT INTO FILM_NAMES VALUES (722,"Nine_Months");
INSERT INTO FILM_NAMES VALUES (723,"Boys_on_the_Side");
INSERT INTO FILM_NAMES VALUES (731,"Corrina__Corrina_(film)");
INSERT INTO FILM_NAMES VALUES (732,"Dave_(film)");
INSERT INTO FILM_NAMES VALUES (733,"Go_Fish_(film)");
INSERT INTO FILM_NAMES VALUES (737,"Sirens_(1999_film)");
INSERT INTO FILM_NAMES VALUES (738,"Threesome_(film)");
INSERT INTO FILM_NAMES VALUES (740,"Jane_Eyre_(1996_film)");
INSERT INTO FILM_NAMES VALUES (741,"The_Last_Supper_(1995_film)");
INSERT INTO FILM_NAMES VALUES (742,"Ransom_(1996_film)");
INSERT INTO FILM_NAMES VALUES (744,"Michael_Collins_(film)");
INSERT INTO FILM_NAMES VALUES (747,"Benny_&_Joon");
INSERT INTO FILM_NAMES VALUES (753,"Burnt_by_the_Sun");
INSERT INTO FILM_NAMES VALUES (755,"Jumanji");
INSERT INTO FILM_NAMES VALUES (761,"Nick_of_Time_(film)");
INSERT INTO FILM_NAMES VALUES (763,"Happy_Gilmore");
INSERT INTO FILM_NAMES VALUES (768,"Casper_(film)");
INSERT INTO FILM_NAMES VALUES (771,"Johnny_Mnemonic_(film)");
INSERT INTO FILM_NAMES VALUES (775,"Something_to_Talk_About_(film)");
INSERT INTO FILM_NAMES VALUES (777,"Castle_Freak");
INSERT INTO FILM_NAMES VALUES (781,"French_Kiss_(film)");
INSERT INTO FILM_NAMES VALUES (791,"The_Baby_sitters_Club");
INSERT INTO FILM_NAMES VALUES (792,"Bullets_Over_Broadway");
INSERT INTO FILM_NAMES VALUES (795,"Richie_Rich_(film)");
INSERT INTO FILM_NAMES VALUES (798,"Bad_Company_(1995_film)");
INSERT INTO FILM_NAMES VALUES (800,"In_the_Mouth_of_Madness");
INSERT INTO FILM_NAMES VALUES (801,"The_Air_Up_There");
INSERT INTO FILM_NAMES VALUES (802,"Hard_Target");
INSERT INTO FILM_NAMES VALUES (805,"Manhattan_Murder_Mystery");
INSERT INTO FILM_NAMES VALUES (807,"Poetic_Justice_(film)");
INSERT INTO FILM_NAMES VALUES (824,"The_Great_White_Hype");
INSERT INTO FILM_NAMES VALUES (825,"The_Arrival_(film)");
INSERT INTO FILM_NAMES VALUES (827,"Daylight_(film)");
INSERT INTO FILM_NAMES VALUES (831,"Escape_from_L_A_");
INSERT INTO FILM_NAMES VALUES (833,"Bulletproof_(1996_film)");
INSERT INTO FILM_NAMES VALUES (836,"Ninotchka");
INSERT INTO FILM_NAMES VALUES (837,"Meet_John_Doe");
INSERT INTO FILM_NAMES VALUES (840,"Last_Man_Standing_(film)");
INSERT INTO FILM_NAMES VALUES (851,"Two_or_Three_Things_I_Know_About_Her");
INSERT INTO FILM_NAMES VALUES (853,"Braindead_(film)");
INSERT INTO FILM_NAMES VALUES (857,"A_Woman_of_Paris");
INSERT INTO FILM_NAMES VALUES (865,"The_Ice_Storm_(film)");
INSERT INTO FILM_NAMES VALUES (866,"Michael_(1996_film)");
INSERT INTO FILM_NAMES VALUES (867,"The_Whole_Wide_World");
INSERT INTO FILM_NAMES VALUES (873,"Picture_Perfect_(1997_film)");
INSERT INTO FILM_NAMES VALUES (878,"That_Darn_Cat!_(1965_film)");
INSERT INTO FILM_NAMES VALUES (879,"The_Peacemaker_(1997_film)");
INSERT INTO FILM_NAMES VALUES (882,"Washington_Square_(film)");
INSERT INTO FILM_NAMES VALUES (885,"Phantoms_(film)");
INSERT INTO FILM_NAMES VALUES (886,"A_Life_Less_Ordinary");
INSERT INTO FILM_NAMES VALUES (887,"Even_Cowgirls_Get_the_Blues_(film)");
INSERT INTO FILM_NAMES VALUES (888,"One_Night_Stand_(1997_film)");
INSERT INTO FILM_NAMES VALUES (891,"Bent_(film)");
INSERT INTO FILM_NAMES VALUES (894,"Home_Alone_3");
INSERT INTO FILM_NAMES VALUES (896,"The_Sweet_Hereafter_(film)");
INSERT INTO FILM_NAMES VALUES (899,"The_Winter_Guest");
INSERT INTO FILM_NAMES VALUES (901,"Mr__Magoo_(film)");
INSERT INTO FILM_NAMES VALUES (902,"The_Big_Lebowski");
INSERT INTO FILM_NAMES VALUES (904,"Ma_vie_en_rose");
INSERT INTO FILM_NAMES VALUES (905,"Great_Expectations_(1998_film)");
INSERT INTO FILM_NAMES VALUES (911,"Twilight_(1998_film)");
INSERT INTO FILM_NAMES VALUES (914,"Wild_Things");
INSERT INTO FILM_NAMES VALUES (915,"Primary_Colors_(film)");
INSERT INTO FILM_NAMES VALUES (916,"Lost_in_Space_(film)");
INSERT INTO FILM_NAMES VALUES (917,"Mercury_Rising");
INSERT INTO FILM_NAMES VALUES (920,"Two_Bits");
INSERT INTO FILM_NAMES VALUES (921,"Farewell_My_Concubine_(film)");
INSERT INTO FILM_NAMES VALUES (923,"Raise_the_Red_Lantern");
INSERT INTO FILM_NAMES VALUES (926,"Down_Periscope");
INSERT INTO FILM_NAMES VALUES (928,"The_Craft_(film)");
INSERT INTO FILM_NAMES VALUES (930,"Chain_Reaction_(film)");
INSERT INTO FILM_NAMES VALUES (931,"The_Island_of_Dr__Moreau_(1996_film)");
INSERT INTO FILM_NAMES VALUES (933,"The_Funeral_(1996_film)");
INSERT INTO FILM_NAMES VALUES (939,"Murder_in_the_First");
INSERT INTO FILM_NAMES VALUES (940,"Airheads");
INSERT INTO FILM_NAMES VALUES (941,"With_Honors_(film)");
INSERT INTO FILM_NAMES VALUES (943,"Killing_Zoe");
INSERT INTO FILM_NAMES VALUES (945,"Charade_(1963_film)");
INSERT INTO FILM_NAMES VALUES (947,"The_Big_Blue");
INSERT INTO FILM_NAMES VALUES (955,"Before_Sunrise");
INSERT INTO FILM_NAMES VALUES (965,"Funny_Face");
INSERT INTO FILM_NAMES VALUES (966,"An_Affair_to_Remember");
INSERT INTO FILM_NAMES VALUES (971,"Mediterraneo");
INSERT INTO FILM_NAMES VALUES (988,"The_Beautician_and_the_Beast");
INSERT INTO FILM_NAMES VALUES (990,"Anna_Karenina_(1997_film)");
INSERT INTO FILM_NAMES VALUES (993,"Hercules_(1997_film)");
INSERT INTO FILM_NAMES VALUES (995,"Kiss_Me__Guido");
INSERT INTO FILM_NAMES VALUES (998,"Cabin_Boy");
INSERT INTO FILM_NAMES VALUES (1003,"That_Darn_Cat_(1997_film)");
INSERT INTO FILM_NAMES VALUES (1008,"I_Shot_Andy_Warhol");
INSERT INTO FILM_NAMES VALUES (1009,"Stealing_Beauty");
INSERT INTO FILM_NAMES VALUES (1011,"2_Days_in_the_Valley");
INSERT INTO FILM_NAMES VALUES (1012,"Private_Parts_(1997_film)");
INSERT INTO FILM_NAMES VALUES (1013,"Anaconda_(film)");
INSERT INTO FILM_NAMES VALUES (1016,"Con_Air");
INSERT INTO FILM_NAMES VALUES (1017,"Trees_Lounge");
INSERT INTO FILM_NAMES VALUES (1020,"Gaslight_(1944_film)");
INSERT INTO FILM_NAMES VALUES (1028,"Grumpier_Old_Men");
INSERT INTO FILM_NAMES VALUES (1031,"Lassie_(1994_film)");
INSERT INTO FILM_NAMES VALUES (1033,"Homeward_Bound_II__Lost_in_San_Francisco");
INSERT INTO FILM_NAMES VALUES (1035,"Cool_Runnings");
INSERT INTO FILM_NAMES VALUES (1037,"Grease_2");
INSERT INTO FILM_NAMES VALUES (1038,"Switchback_(film)");
INSERT INTO FILM_NAMES VALUES (1045,"Fearless_(1993_film)");
INSERT INTO FILM_NAMES VALUES (1049,"House_Arrest_(film)");
INSERT INTO FILM_NAMES VALUES (1050,"The_Ghost_and_Mrs__Muir");
INSERT INTO FILM_NAMES VALUES (1052,"Dracula__Dead_and_Loving_It");
INSERT INTO FILM_NAMES VALUES (1056,"Cronos_(film)");
INSERT INTO FILM_NAMES VALUES (1057,"The_Pallbearer");
INSERT INTO FILM_NAMES VALUES (1063,"A_Little_Princess_(1995_film)");
INSERT INTO FILM_NAMES VALUES (1066,"Balto_(film)");
INSERT INTO FILM_NAMES VALUES (1073,"Shallow_Grave");
INSERT INTO FILM_NAMES VALUES (1076,"The_Pagemaster");
INSERT INTO FILM_NAMES VALUES (1078,"Oliver_&_Company");
INSERT INTO FILM_NAMES VALUES (1083,"Albino_Alligator");
INSERT INTO FILM_NAMES VALUES (1089,"Speed_2__Cruise_Control");
INSERT INTO FILM_NAMES VALUES (1090,"Sliver_(film)");
INSERT INTO FILM_NAMES VALUES (1091,"Pete's_Dragon");
INSERT INTO FILM_NAMES VALUES (1098,"Flirting_with_Disaster_(film)");
INSERT INTO FILM_NAMES VALUES (1101,"Six_Degrees_of_Separation_(film)");
INSERT INTO FILM_NAMES VALUES (1102,"Two_Much");
INSERT INTO FILM_NAMES VALUES (1117,"Surviving_Picasso");
INSERT INTO FILM_NAMES VALUES (1124,"A_Farewell_to_Arms_(1932_film)");
INSERT INTO FILM_NAMES VALUES (1129,"Chungking_Express");
INSERT INTO FILM_NAMES VALUES (1136,"Ghosts_of_Mississippi");
INSERT INTO FILM_NAMES VALUES (1137,"Beautiful_Thing_(film)");
INSERT INTO FILM_NAMES VALUES (1139,"Hackers_(film)");
INSERT INTO FILM_NAMES VALUES (1145,"Blue_Chips");
INSERT INTO FILM_NAMES VALUES (1154,"Alphaville_(film)");
INSERT INTO FILM_NAMES VALUES (1157,"The_Relic_(film)");
INSERT INTO FILM_NAMES VALUES (1168,"Little_Buddha");
INSERT INTO FILM_NAMES VALUES (1172,"The_Women_(1939_film)");
INSERT INTO FILM_NAMES VALUES (1178,"Major_Payne");
INSERT INTO FILM_NAMES VALUES (1179,"Man_of_the_House_(1995_film)");
INSERT INTO FILM_NAMES VALUES (1187,"Switchblade_Sisters");
INSERT INTO FILM_NAMES VALUES (1188,"Young_Guns_II");
INSERT INTO FILM_NAMES VALUES (1193,"Before_the_Rain_(1994_film)");
INSERT INTO FILM_NAMES VALUES (1194,"Once_Were_Warriors_(film)");
INSERT INTO FILM_NAMES VALUES (1196,"Savage_Nights");
INSERT INTO FILM_NAMES VALUES (1199,"Cemetery_Man");
INSERT INTO FILM_NAMES VALUES (1203,"Top_Hat");
INSERT INTO FILM_NAMES VALUES (1208,"Kiss_of_Death_(1995_film)");
INSERT INTO FILM_NAMES VALUES (1210,"Virtuosity");
INSERT INTO FILM_NAMES VALUES (1214,"In_the_Realm_of_the_Senses");
INSERT INTO FILM_NAMES VALUES (1224,"The_Scout_(film)");
INSERT INTO FILM_NAMES VALUES (1225,"Angus_(film)");
INSERT INTO FILM_NAMES VALUES (1226,"Night_Falls_on_Manhattan");
INSERT INTO FILM_NAMES VALUES (1227,"An_Awfully_Big_Adventure");
INSERT INTO FILM_NAMES VALUES (1228,"Under_Siege_2__Dark_Territory");
INSERT INTO FILM_NAMES VALUES (1231,"Marked_for_Death");
INSERT INTO FILM_NAMES VALUES (1246,"Bushwhacked_(film)");
INSERT INTO FILM_NAMES VALUES (1248,"Blink_(film)");
INSERT INTO FILM_NAMES VALUES (1252,"Contempt_(film)");
INSERT INTO FILM_NAMES VALUES (1263,"Foxfire_(1996_film)");
INSERT INTO FILM_NAMES VALUES (1266,"Bread_and_Chocolate");
INSERT INTO FILM_NAMES VALUES (1267,"Clockers_(film)");
INSERT INTO FILM_NAMES VALUES (1268,"Bitter_Moon");
INSERT INTO FILM_NAMES VALUES (1269,"Love_in_the_Afternoon_(1957_film)");
INSERT INTO FILM_NAMES VALUES (1274,"RoboCop_3");
INSERT INTO FILM_NAMES VALUES (1276,"Sunset_Park_(film)");
INSERT INTO FILM_NAMES VALUES (1283,"Out_to_Sea");
INSERT INTO FILM_NAMES VALUES (1289,"Jack_and_Sarah");
INSERT INTO FILM_NAMES VALUES (1291,"Celtic_Pride");
INSERT INTO FILM_NAMES VALUES (1303,"The_Getaway_(1994_film)");
INSERT INTO FILM_NAMES VALUES (1313,"Palmetto_(film)");
INSERT INTO FILM_NAMES VALUES (1315,"Inventing_the_Abbotts");
INSERT INTO FILM_NAMES VALUES (1316,"The_Horse_Whisperer_(film)");
INSERT INTO FILM_NAMES VALUES (1321,"Open_Season_(film)");
INSERT INTO FILM_NAMES VALUES (1342,"The_Convent_(film)");
INSERT INTO FILM_NAMES VALUES (1403,"Caro_diario");
INSERT INTO FILM_NAMES VALUES (1404,"Withnail_and_I");
INSERT INTO FILM_NAMES VALUES (1411,"Barbarella_(film)");
INSERT INTO FILM_NAMES VALUES (1413,"Street_Fighter_(film)");
INSERT INTO FILM_NAMES VALUES (1415,"The_Next_Karate_Kid");
INSERT INTO FILM_NAMES VALUES (1423,"The_Walking_Dead_(1995_film)");
INSERT INTO FILM_NAMES VALUES (1429,"Sliding_Doors");
INSERT INTO FILM_NAMES VALUES (1441,"Moonlight_and_Valentino");
INSERT INTO FILM_NAMES VALUES (1442,"The_Scarlet_Letter_(1995_film)");
INSERT INTO FILM_NAMES VALUES (1444,"That_Darn_Cat_(1997_film)");
INSERT INTO FILM_NAMES VALUES (1454,"Angel_and_the_Badman");
INSERT INTO FILM_NAMES VALUES (1456,"Beat_the_Devil_(film)");
INSERT INTO FILM_NAMES VALUES (1468,"The_Cure_(1995_film)");
INSERT INTO FILM_NAMES VALUES (1469,"Tom_and_Huck");
INSERT INTO FILM_NAMES VALUES (1476,"Raw_Deal_(1948_film)");
INSERT INTO FILM_NAMES VALUES (1477,"Nightwatch_(1994_film)");
INSERT INTO FILM_NAMES VALUES (1478,"Dead_Presidents");
INSERT INTO FILM_NAMES VALUES (1480,"Herbie_Rides_Again");
INSERT INTO FILM_NAMES VALUES (1481,"S_F_W_");
INSERT INTO FILM_NAMES VALUES (1483,"The_Man_in_the_Iron_Mask_(1998_film)");
INSERT INTO FILM_NAMES VALUES (1487,"Event_Horizon_(film)");
INSERT INTO FILM_NAMES VALUES (1524,"The_Enigma_of_Kaspar_Hauser");
INSERT INTO FILM_NAMES VALUES (1525,"The_Object_of_My_Affection");
INSERT INTO FILM_NAMES VALUES (1526,"Witness_(1985_film)");
INSERT INTO FILM_NAMES VALUES (1527,"Senseless");
INSERT INTO FILM_NAMES VALUES (1529,"Underground_(1995_film)");
INSERT INTO FILM_NAMES VALUES (1530,"Jefferson_in_Paris");
INSERT INTO FILM_NAMES VALUES (1534,"Twin_Town");
INSERT INTO FILM_NAMES VALUES (1544,"It_Takes_Two_(1995_film)");
INSERT INTO FILM_NAMES VALUES (1549,"Tucker__The_Man_and_His_Dream");
INSERT INTO FILM_NAMES VALUES (1578,"La_Collectionneuse");
INSERT INTO FILM_NAMES VALUES (1602,"A_Price_Above_Rubies");
INSERT INTO FILM_NAMES VALUES (1619,"All_Things_Fair");
INSERT INTO FILM_NAMES VALUES (1624,"Hush_(1998_film)");
INSERT INTO FILM_NAMES VALUES (1625,"Nightwatch_(1994_film)");
INSERT INTO FILM_NAMES VALUES (1628,"Lamerica");
INSERT INTO FILM_NAMES VALUES (1632,"Land_and_Freedom");
INSERT INTO FILM_NAMES VALUES (1647,"Hana_bi");
INSERT INTO FILM_NAMES VALUES (1655,"The_Favor_(2006_film)");
INSERT INTO FILM_NAMES VALUES (1659,"Getting_Away_with_Murder_(film)");
INSERT INTO FILM_NAMES VALUES (1674,"Mamma_Roma");
INSERT INTO FILM_NAMES VALUES (1680,"Sliding_Doors");
