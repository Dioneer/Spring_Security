create table t_task(
    id serial primary key,
    c_details text,
    c_complete booleb not null default false
);