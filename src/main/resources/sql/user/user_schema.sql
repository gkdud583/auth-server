drop table if exists `user` cascade;
create table user (
    id bigint not null auto_increment,
    email varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
);
