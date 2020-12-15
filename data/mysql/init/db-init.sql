# # drop database if exists`;
# drop database if exists `medrecord`;
#
# # create database
# create database if not exists `medrecord`;
#
# create user 'user'@'%' identified by'password';
#
# # grant rights on medrecord
# grant all on medrecord.* to 'user'@'%';
# create table if not exists patients(id int(11));
