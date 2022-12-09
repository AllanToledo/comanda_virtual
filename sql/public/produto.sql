create table produto
(
    id_produto   serial
        primary key,
    id_categoria serial
        references categoria_produto,
    valor        numeric(9, 2),
    descricao    text,
    custo        numeric(9, 2),
    nome         varchar(120)
);

alter table produto
    owner to postgres;

