create table t_task(
    id serial primary key,
    c_details text,
    c_complete boolean not null default false
);
insert into t_task(id, c_details, c_complete) values (1,'task1',false),(2,'task2',false),(3,'task3',false);