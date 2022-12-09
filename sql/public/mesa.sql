create table mesa
(
    id_mesa serial
        primary key,
    nome    varchar(20)
);

alter table mesa
    owner to postgres;

