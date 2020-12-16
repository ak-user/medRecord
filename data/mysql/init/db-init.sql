# drop database if exists`;
drop database if exists `medrecord`;

# create database
create database if not exists `medrecord`;

create user 'user'@'%' identified by'password';

# grant rights on medrecord
grant all on medrecord.* to 'user'@'%';
create table if not exists patients
(
    id int auto_increment
        primary key,
    dob date null,
    email varchar(255) null,
    first_name varchar(255) null,
    gender varchar(255) null,
    last_name varchar(255) null
);


INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10001, 'Georgi', 'Facello', 'M');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10002, 'Bezalel', 'Simmel', 'F');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10003, 'Parto', 'Bamford', 'M');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10004, 'Chirstian', 'Koblick', 'M');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10005, 'Kyoichi', 'Maliniak', 'M');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10006, 'Anneke', 'Preusig', 'F');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10007, 'Tzvetan', 'Zielinski', 'F');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10008, 'Saniya', 'Kalloufi', 'M');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10009, 'Sumant', 'Peac', 'F');
INSERT INTO patients(id, first_name, last_name, gender, dob) VALUES (10010, 'Duangkaew', 'Piveteau', 'F');
dob
