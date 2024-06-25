CREATE TABLE MEMBER(
    id serial primary key,
    username varchar unique not null,
    password varchar not null,
    nickname varchar not null,
    role varchar not null,
    created_at timestamp not null,
    updated_at timestamp not null
);