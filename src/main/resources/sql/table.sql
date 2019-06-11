--用户表
create table users
(
    id integer primary key  auto_increment,
    name varchar(20),
    password varchar(30),
    roleid integer
)

--角色表
create table roles
(
id integer primary key  auto_increment,
name varchar(20),
roleLevel integer,
description varchar(255)
)

--权限表
create table Permission (
id integer primary key  auto_increment,
name varchar(20),
permissionUrl varchar(30),
method varchar(20),
description varchar(255)
)

--角色权限对应
create table role_permission(
id integer primary key  auto_increment,
role_id integer ,
permission_id integer
)