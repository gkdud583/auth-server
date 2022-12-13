drop table if exists `user` cascade;
create table user (
    id bigint not null auto_increment,
    email varchar(255) not null unique,
    password varchar(255) not null,
    role varchar(10) not null,
    primary key (id)
);
