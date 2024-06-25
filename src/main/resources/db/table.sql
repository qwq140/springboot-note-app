CREATE TABLE MEMBER(
    id serial primary key,
    username varchar unique not null,
    password varchar not null,
    nickname varchar not null,
    role varchar not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

CREATE TABLE NOTE(
    id serial primary key,
    title varchar not null,
    content varchar not null,
    member_id integer REFERENCES MEMBER,
    created_at timestamp not null,
    updated_at timestamp not null
);