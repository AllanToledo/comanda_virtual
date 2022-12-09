create table forma_pagamento
(
    id_forma_pagamento serial
        primary key,
    tipo               varchar(120)
);

alter table forma_pagamento
    owner to postgres;

