create table comanda
(
    id_comanda      serial
        primary key,
    id_mesa         serial
        references mesa,
    valor_total     numeric(9, 2),
    data_abertura   timestamp,
    data_fechamento timestamp
);

alter table comanda
    owner to postgres;

