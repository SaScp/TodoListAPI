create table if not exists t_user(
    uuid varchar(255) primary key,
    c_name varchar(13),
    email varchar(255) unique not null,
    password varchar(30) not null,
    c_role varchar(16) not null,
    create_at timestamp not null,
    update_at timestamp not null,
    );

create table if not exists t_task(
    uuid varchar(255) primary key,
    title varchar(15) not null,
    description text default null,
    status varchar(10) not null,
    create_at timestamp not null,
    expiration_date timestamp not null,
    user_id varchar(255) references t_user(uuid)
    );

