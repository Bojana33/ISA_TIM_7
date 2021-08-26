INSERT INTO loyalty_program(id, appointment_points, consultation_points, discount_gold, discount_regular, discount_silver, gold_points, regular_points, silver_points)
VALUES(1,0,0,0,0,0,0,0,0);

INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role)
VALUES ('Bojana', 'Todorovic', 'bojana@gmail.com', 'bojana', 'Novi Sad', 'Novi Sad', 'Srbija', '061111111', 'system_administrator', true, false, 0);
INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, loyalty_program_id, user_role)
VALUES('Goran', 'Markovic', 'goran@hotmail.com', 'goran', 'Novi Sad', 'Novi Sad', 'Srbija', '061111111', 'Patient', true, false, 1, 1);

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

INSERT INTO pharmacy(name, address, average_rating) VALUES ('Jankovic', 'Strazilovska 31', 3.2);
INSERT INTO pharmacy(name, address, average_rating) VALUES ('Jankovic2', 'Ive Lole Ribara 17a', 4.5);

INSERT INTO appointment(loyalty_points, price, dermatologist) VALUES (10, 4500.0, 1);