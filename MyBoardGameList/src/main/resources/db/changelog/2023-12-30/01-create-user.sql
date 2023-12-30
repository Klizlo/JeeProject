--liquibase formatted sql
--changeset Klizlo:1

create table users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(25) not null,
    email varchar(125) not null,
    password varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
);

alter table users
    add constraint UQ_user_email unique (email);