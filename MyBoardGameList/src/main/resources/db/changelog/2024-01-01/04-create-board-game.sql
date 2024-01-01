-- liquibase formatted sql
-- changeset Klizlo:4

create table board_games (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    developer varchar(255) not null,
    description text,
    minNumberOfPlayers number(2, 0) default 0,
    maxNumberOfPlayers number(2, 0) default 0,
    numberOfHours number(10,2) default 0,
    picture text,
    category_id BIGINT not null,
    user_id BIGINT not null,
    constraint BG_category_fk foreign key (category_id) references categories(id),
    constraint BG_owner_fk foreign key (user_id) references users(id)
);