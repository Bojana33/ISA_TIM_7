/*LOYALTY PROGRAM*/
INSERT INTO loyalty_program(id, appointment_points, consultation_points, discount_gold, discount_regular, discount_silver, gold_points, regular_points, silver_points)
VALUES(11,0,0,0,0,0,1,5,3);

/*USERS*/
INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role)
VALUES ('Bojana', 'Todorovic', 'bojana@gmail.com', 'bojana', 'Novi Sad', 'Novi Sad', 'Srbija', '061111111', 'system_administrator', true, false, 0);

INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, loyalty_program_id, user_role)
VALUES('Goran', 'Markovic', 'goran@hotmail.com', 'goran', 'Novi Sad', 'Novi Sad', 'Srbija', '061111111', 'Patient', true, false, 11, 1);

INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role)
VALUES('Dr Milos', 'Savic', 'savic@mail.com', 'savic', 'Novi Sad', 'Novi Sad', 'Srbija', '066999999', 'Dermatologist', true, false, 5);
INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role)
VALUES('Dr Sava', 'Nedeljkovic', 'sava@mail.com', 'sava', 'Beograd', 'Beograd', 'Srbija', '011/600601', 'Dermatologist', true, false, 5);

INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role)
VALUES('Ozren', 'Soldatovic', 'ozren@mail.com', 'ozren', 'Novi Sad', 'Novi Sad', 'Srbija', '066999999', 'Pharmacist', true, false, 4);
INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role)
VALUES('Milorad', 'Milenkovic', 'mile@mail.com', 'mile', 'Beograd', 'Beograd', 'Srbija', '011/600601', 'Pharmacist', true, false, 4);
INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role)
VALUES('Branislav', 'Jovanovic', 'branislav@mail.com', 'branislav', 'Beograd', 'Beograd', 'Srbija', '011/600601', 'Pharmacist', true, false, 4);

/*DRUGS*/
INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (111,'Aspirin','crvenilo', 'neka struktura', 3, FALSE , 'Proizvodjac1', FALSE , 'koristiti posle obroka', 10, 100, 344.99, 0, 2);
INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (112,'Amoksiciklin','crvenilo, svrab', 'struktura1', 2, FALSE , 'Proizvodjac2', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3);
INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (113,'Probiotik','crvenilo, svrab', 'struktura1', 2, FALSE , 'Ivancic i sinovi', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3);
INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (114,'caffetin','crvenilo, svrab', 'struktura1', 2, FALSE , 'Alkaloid', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3);
INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (115,'caffetin menstrual','crvenilo, svrab', 'Alkaloid', 2, FALSE , 'Proizvodjac2', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3);
INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (118,'daktanol','crvenilo, svrab', 'struktura1', 2, FALSE , 'Proizvodjac2', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3);

/*PHARMACIES*/
INSERT INTO pharmacy(name, address, average_rating) VALUES ('Jankovic', 'Strazilovska 31', 3.2);
INSERT INTO pharmacy(name, address, average_rating) VALUES ('Jankovic2', 'Ive Lole Ribara 17a', 4.5);

/*PHARMACY_DRUGS*/

INSERT INTO pharmacy_drugs(pharmacy_id, drugs_id) VALUES (1,1);
INSERT INTO pharmacy_drugs(pharmacy_id, drugs_id) VALUES (1,2);
INSERT INTO pharmacy_drugs(pharmacy_id, drugs_id) VALUES (1,3);
INSERT INTO pharmacy_drugs(pharmacy_id, drugs_id) VALUES (2,4);
INSERT INTO pharmacy_drugs(pharmacy_id, drugs_id) VALUES (2,5);
INSERT INTO pharmacy_drugs(pharmacy_id, drugs_id) VALUES (2,6);

/*DERMATOLOGIST APPOINTMENTS*/
INSERT INTO appointment(id, loyalty_points, price, dermatologist_id, date, duration_in_minutes, starting_time, scheduled, pharmacy_id, advising)
VALUES (1, 10, 4500.0, 3, '20211010', 60, '20211010 11:30:00 AM', false, 1, false);
INSERT INTO appointment(id, loyalty_points, price, dermatologist_id, date, duration_in_minutes, starting_time,scheduled, pharmacy_id, advising)
VALUES (2, 8, 4000.0, 4, '20210911', 45,'20210911 08:00:00 AM', false, 1, false);
INSERT INTO appointment(id, loyalty_points, price, dermatologist_id, date, duration_in_minutes, starting_time,scheduled, pharmacy_id, advising)
VALUES (3, 8, 3000.0, 4, '20210511', 45,'20210511 08:00:00 AM', false, 2, false);

/*PHARMACIST APPOINTMENTS*/
INSERT INTO appointment(id, loyalty_points, price, pharmacist_id, date, duration_in_minutes, starting_time, scheduled, pharmacy_id, advising)
VALUES (11, 10, 3700.0, 6, '20211010', 60, '20211010 11:30:00 AM', false, 1, true);
INSERT INTO appointment(id, loyalty_points, price, pharmacist_id, date, duration_in_minutes, starting_time,scheduled, pharmacy_id, advising)
VALUES (12, 15, 2500.0, 7, '20210911', 45,'20210911 08:00:00 AM', false, 1, true);
INSERT INTO appointment(id, loyalty_points, price, pharmacist_id, date, duration_in_minutes, starting_time,scheduled, pharmacy_id, advising)
VALUES (13, 8, 3000.0, 5, '20210511', 45,'20210511 08:00:00 AM', false, 2, true);

