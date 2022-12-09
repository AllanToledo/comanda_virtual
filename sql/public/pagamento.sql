create table pagamento
(
    id_pagamento       serial
        primary key,
    id_comanda         serial
        references comanda,
    id_forma_pagamento serial
        references forma_pagamento,
    valor              numeric(9, 2),
    data               timestamp,
    cpf_cliente        char(11)
);

alter table pagamento
    owner to postgres;

