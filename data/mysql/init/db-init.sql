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


INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10001, 'Georgi', 'Facello', 'male', 'georgi.facello@example.com', '1994-02-14T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10002, 'Bezalel', 'Simmel', 'female', 'bezalel.simmel@example.com', '1998-07-09T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10003, 'Parto', 'Bamford', 'male', 'parto.bamford@example.com', '1974-06-06T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10004, 'Chirstian', 'Koblick', 'male', 'chirstian.koblick@example.com', '1978-11-07T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10005, 'Kyoichi', 'Maliniak', 'male', 'kyoichi.maliniak@example.com', '1982-07-07T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10006, 'Anneke', 'Preusig', 'female', 'anneke.preusig@example.com', '1942-07-23T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10007, 'Tzvetan', 'Zielinski', 'female', 'tzvetan.zielinski@example.com', '1932-07-07T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10008, 'Saniya', 'Kalloufi', 'male', 'saniya.kalloufi@example.com', '1922-07-09T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10009, 'Sumant', 'Peac', 'female', 'sumant.peac@example.com', '1934-02-06T22:18:26.625Z');
INSERT INTO patients(id, first_name, last_name, gender, email, dob) VALUES (10010, 'Duangkaew', 'Piveteau', 'female', 'duangkaew.piveteau@example.com', '1934-02-08T22:18:26.625Z');
