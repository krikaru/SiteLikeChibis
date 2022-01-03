create sequence hibernate_sequence start 1 increment 1;

create table news (
    id bigint not null,
    creation_date timestamp,
    head varchar(255),
    text varchar(255),
    user_id bigint,
    primary key (id)
);

create table user_role (
    user_id bigint not null,
    roles varchar(255)
);

create table usr (
    id bigint not null,
    active boolean not null,
    email varchar(255),
    name varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table if exists news
    add constraint news_user_fk
    foreign key (user_id) references usr;
alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr;

