INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role) VALUES ('Bojana', 'Todorovic', 'bojana@gmail.com', 'bojana', 'Novi Sad', 'Novi Sad', 'Srbija', '061111111', 'system_administrator', true, false,0);

INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role) VALUES ('Ana', 'Anic', 'ana@gmail.com', 'ana', 'Novi Sad', 'Novi Sad', 'Srbija', '061111111', 'pharmacy_administrator', true, false,2);

INSERT INTO users(first_name, last_name, email, password, residence, city, state, phone_number, user_type, enabled, is_first_login, user_role) VALUES ('Mina', 'Bojanic', 'mina@gmail.com', 'mina', 'Novi Sad', 'Novi Sad', 'Srbija', '061111111', 'supplier', true, false,3);

INSERT INTO loyalty_program(appointment_points,consultation_points, discount_gold, discount_regular, discount_silver, gold_points, is_defined, regular_points, silver_points) values (0.0,0.0,0,0,0,0.0,true,0.0,0.0);


INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape, average_rating)
VALUES (111,'Aspirin1','crvenilo', 'neka struktura', 3, FALSE , 'Proizvodjac1', FALSE , 'koristiti posle obroka', 10, 100, 344.99, 0, 2, 1.0);

INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape, average_rating)
VALUES (112,'Aspirin2','crvenilo, svrab', 'struktura1', 2, FALSE , 'Proizvodjac2', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3, 1.0);

INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape, average_rating)
VALUES (122,'Aspirin1','crvenilo, svrab', 'struktura1', 2, FALSE , 'Proizvodjac2', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3, 2.0);

INSERT INTO drug(code, name, contraindications, structure, daily_intake, reserved, producer, on_prescription, additional_note, loyalty_points, quantity, price, drug_type, drug_shape)
VALUES (132,'Aspirin1','crvenilo, svrab', 'struktura1', 2, FALSE , 'Proizvodjac2', TRUE , 'koristiti pre obroka', 10, 150, 384.99, 1, 3);

INSERT INTO order_form(offer_due_date, creator_id) values ('2021-08-26', 2);
insert into order_form_drugs(order_form_id,quantity,drug_code) values (1,10,111);
insert into order_form_drugs(order_form_id,quantity,drug_code) values (1,10,112);

insert into supplier_drugs(supplier_id,quantity,drug_code) values (3,11,111);
insert into supplier_drugs(supplier_id,quantity,drug_code) values (3,15,112);

insert into pharmacy(name,address) values ('apoteka','adresa');
insert into pharmacy(name,address) values ('apoteka2','adresa2');
insert into pharmacy(name,address) values ('apoteka3','adresa3');

insert into pharmacy_drugs_quantity(pharmacy_id,drug_code,quantity) values (1,122,10);
insert into pharmacy_drugs_quantity(pharmacy_id,drug_code,quantity) values (2,122,0);
insert into pharmacy_drugs_quantity(pharmacy_id,drug_code,quantity) values (3,122,4);
insert into pharmacy_drugs_quantity(pharmacy_id,drug_code,quantity) values (3,111,4);
insert into pharmacy_drugs_quantity(pharmacy_id,drug_code,quantity) values (2,111,40);

insert into pharmacy_drugs(pharmacy_id,drug_code,price) values (1,122,1000.50);
insert into pharmacy_drugs(pharmacy_id,drug_code,price) values (2,122,999.99);
insert into pharmacy_drugs(pharmacy_id,drug_code,price) values (3,122,1000.00);
insert into pharmacy_drugs(pharmacy_id,drug_code,price) values (3,111,1000.00);
insert into pharmacy_drugs(pharmacy_id,drug_code,price) values (2,111,1050.00);