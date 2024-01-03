--liquibase formatted sql
--changeset Klizlo:5

create table refresh_token(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token varchar(255) not null,
    expiryDate timestamp not null,
    user_id BIGINT not null,
    constraint RT_user_fk foreign key (user_id) references users(id)
);