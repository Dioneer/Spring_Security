create table t_application_user(
    id serial primary key,
    c_username varchar(255) not null,
    c_username text
);

create unique index idx_application_user_isername on t_application_user(c_username);

insert into t_application_user (c_username, c_username) values('user1', '{noop}password1'),
values('user2', '{noop}password2');