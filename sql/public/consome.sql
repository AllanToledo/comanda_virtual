create table consome
(
    id_produto serial
        references produto,
    id_comanda serial
        references comanda,
    quantidade real,
    valor      numeric(9, 2),
    custo      numeric(9, 2),
    id_consome serial
        primary key
);

alter table consome
    owner to postgres;

