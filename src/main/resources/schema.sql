create table if not exists t_user(
    uuid varchar(255) primary key,
    c_name varchar(255),
    email varchar(255) unique not null,
    password varchar(255) not null,
    c_role varchar(255) not null,
    create_at timestamp not null,
    update_at timestamp not null
    );

create table if not exists t_task(
    uuid varchar(255) primary key,
    title varchar(255) not null,
    description text default null,
    status varchar(255) not null,
    create_at timestamp not null,
    expiration_date timestamp not null,
    user_id varchar(255) references t_user(uuid) on delete cascade
    );
create table if not exists t_img_task(
    img_id varchar(255) primary key,
    img varchar(255) not null,
    task_id varchar(255) references t_task(uuid) on delete cascade
    );
create table if not exists t_role(
    role_id varchar(255) primary key,
    c_role varchar(255) not null,
    task_id varchar(255) references t_task(uuid) on delete cascade
);