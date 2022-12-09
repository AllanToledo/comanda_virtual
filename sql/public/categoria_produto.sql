create table categoria_produto
(
    id_categoria serial
        primary key,
    categoria    varchar(120)
);

alter table categoria_produto
    owner to postgres;

