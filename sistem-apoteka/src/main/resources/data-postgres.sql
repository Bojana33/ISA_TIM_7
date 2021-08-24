INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role) VALUES ('Bojana', 'Todorovic', 'bojana@gmail.com', 'bojana', 'Novi Sad', 'Novi Sad', 'Srbija', '061111111', 'system_administrator', true, true,0);

INSERT into authority(name) values ('ROLE_SYS_ADMIN');
INSERT into authority(name) values ('ROLE_PATIENT');
INSERT into authority(name) values ('ROLE_PHARMACY_ADMIN');
INSERT into authority(name) values ('ROLE_PHARMACIST');
INSERT into authority(name) values ('ROLE_DERMATOLOGIST');
INSERT into authority(name) values ('ROLE_SUPPLIER');

INSERT into user_authority(user_id, authority_id) values (1,1);

INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (111,'Aspirin1','crvenilo', 'neka struktura', 3, FALSE , 'Proizvodjac1', FALSE , 'koristiti posle obroka', 10, 100, 344.99, 0, 2);

INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (112,'Aspirin2','crvenilo, svrab', 'struktura1', 2, FALSE , 'Proizvodjac2', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3);