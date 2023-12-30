--liquibase formatted sql
--changeset Klizlo:2

create table categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    constraint UQ_category_name unique (name)
);