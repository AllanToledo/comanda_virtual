--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1 (Debian 15.1-1.pgdg110+1)
-- Dumped by pg_dump version 15.1 (Debian 15.1-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categoria_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria_produto (
    id_categoria integer NOT NULL,
    categoria character varying(120)
);


ALTER TABLE public.categoria_produto OWNER TO postgres;

--
-- Name: categoria_produto_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_produto_id_categoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_produto_id_categoria_seq OWNER TO postgres;

--
-- Name: categoria_produto_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_produto_id_categoria_seq OWNED BY public.categoria_produto.id_categoria;


--
-- Name: comanda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comanda (
    id_comanda integer NOT NULL,
    id_mesa integer NOT NULL,
    valor_total numeric(9,2),
    data_abertura timestamp without time zone,
    data_fechamento timestamp without time zone
);


ALTER TABLE public.comanda OWNER TO postgres;

--
-- Name: comanda_id_comanda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comanda_id_comanda_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comanda_id_comanda_seq OWNER TO postgres;

--
-- Name: comanda_id_comanda_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comanda_id_comanda_seq OWNED BY public.comanda.id_comanda;


--
-- Name: comanda_id_mesa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comanda_id_mesa_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comanda_id_mesa_seq OWNER TO postgres;

--
-- Name: comanda_id_mesa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comanda_id_mesa_seq OWNED BY public.comanda.id_mesa;


--
-- Name: consome; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consome (
    id_produto integer NOT NULL,
    id_comanda integer NOT NULL,
    quantidade integer,
    valor numeric(9,2),
    custo numeric(9,2),
    id_consome integer NOT NULL
);


ALTER TABLE public.consome OWNER TO postgres;

--
-- Name: consome_id_comanda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.consome_id_comanda_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.consome_id_comanda_seq OWNER TO postgres;

--
-- Name: consome_id_comanda_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.consome_id_comanda_seq OWNED BY public.consome.id_comanda;


--
-- Name: consome_id_consome_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.consome_id_consome_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.consome_id_consome_seq OWNER TO postgres;

--
-- Name: consome_id_consome_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.consome_id_consome_seq OWNED BY public.consome.id_consome;


--
-- Name: consome_id_produto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.consome_id_produto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.consome_id_produto_seq OWNER TO postgres;

--
-- Name: consome_id_produto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.consome_id_produto_seq OWNED BY public.consome.id_produto;


--
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pagamento (
    id_forma_pagamento integer NOT NULL,
    tipo character varying(120)
);


ALTER TABLE public.forma_pagamento OWNER TO postgres;

--
-- Name: forma_pagamento_id_forma_pagamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.forma_pagamento_id_forma_pagamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forma_pagamento_id_forma_pagamento_seq OWNER TO postgres;

--
-- Name: forma_pagamento_id_forma_pagamento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forma_pagamento_id_forma_pagamento_seq OWNED BY public.forma_pagamento.id_forma_pagamento;


--
-- Name: mesa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mesa (
    id_mesa integer NOT NULL,
    nome character varying(20)
);


ALTER TABLE public.mesa OWNER TO postgres;

--
-- Name: mesa_id_mesa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mesa_id_mesa_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mesa_id_mesa_seq OWNER TO postgres;

--
-- Name: mesa_id_mesa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mesa_id_mesa_seq OWNED BY public.mesa.id_mesa;


--
-- Name: pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pagamento (
    id_pagamento integer NOT NULL,
    id_comanda integer NOT NULL,
    id_forma_pagamento integer NOT NULL,
    valor numeric(9,2),
    data timestamp without time zone,
    cpf_cliente character(11)
);


ALTER TABLE public.pagamento OWNER TO postgres;

--
-- Name: pagamento_id_comanda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagamento_id_comanda_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pagamento_id_comanda_seq OWNER TO postgres;

--
-- Name: pagamento_id_comanda_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagamento_id_comanda_seq OWNED BY public.pagamento.id_comanda;


--
-- Name: pagamento_id_forma_pagamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagamento_id_forma_pagamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pagamento_id_forma_pagamento_seq OWNER TO postgres;

--
-- Name: pagamento_id_forma_pagamento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagamento_id_forma_pagamento_seq OWNED BY public.pagamento.id_forma_pagamento;


--
-- Name: pagamento_id_pagamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagamento_id_pagamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pagamento_id_pagamento_seq OWNER TO postgres;

--
-- Name: pagamento_id_pagamento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagamento_id_pagamento_seq OWNED BY public.pagamento.id_pagamento;


--
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    id_produto integer NOT NULL,
    id_categoria integer NOT NULL,
    valor numeric(9,2),
    custo numeric(9,2),
    nome character varying(120)
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- Name: produto_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_id_categoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_id_categoria_seq OWNER TO postgres;

--
-- Name: produto_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_id_categoria_seq OWNED BY public.produto.id_categoria;


--
-- Name: produto_id_produto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_id_produto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_id_produto_seq OWNER TO postgres;

--
-- Name: produto_id_produto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_id_produto_seq OWNED BY public.produto.id_produto;


--
-- Name: categoria_produto id_categoria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria_produto ALTER COLUMN id_categoria SET DEFAULT nextval('public.categoria_produto_id_categoria_seq'::regclass);


--
-- Name: comanda id_comanda; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda ALTER COLUMN id_comanda SET DEFAULT nextval('public.comanda_id_comanda_seq'::regclass);


--
-- Name: comanda id_mesa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda ALTER COLUMN id_mesa SET DEFAULT nextval('public.comanda_id_mesa_seq'::regclass);


--
-- Name: consome id_produto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consome ALTER COLUMN id_produto SET DEFAULT nextval('public.consome_id_produto_seq'::regclass);


--
-- Name: consome id_comanda; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consome ALTER COLUMN id_comanda SET DEFAULT nextval('public.consome_id_comanda_seq'::regclass);


--
-- Name: consome id_consome; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consome ALTER COLUMN id_consome SET DEFAULT nextval('public.consome_id_consome_seq'::regclass);


--
-- Name: forma_pagamento id_forma_pagamento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento ALTER COLUMN id_forma_pagamento SET DEFAULT nextval('public.forma_pagamento_id_forma_pagamento_seq'::regclass);


--
-- Name: mesa id_mesa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mesa ALTER COLUMN id_mesa SET DEFAULT nextval('public.mesa_id_mesa_seq'::regclass);


--
-- Name: pagamento id_pagamento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento ALTER COLUMN id_pagamento SET DEFAULT nextval('public.pagamento_id_pagamento_seq'::regclass);


--
-- Name: pagamento id_comanda; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento ALTER COLUMN id_comanda SET DEFAULT nextval('public.pagamento_id_comanda_seq'::regclass);


--
-- Name: pagamento id_forma_pagamento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento ALTER COLUMN id_forma_pagamento SET DEFAULT nextval('public.pagamento_id_forma_pagamento_seq'::regclass);


--
-- Name: produto id_produto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN id_produto SET DEFAULT nextval('public.produto_id_produto_seq'::regclass);


--
-- Name: produto id_categoria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN id_categoria SET DEFAULT nextval('public.produto_id_categoria_seq'::regclass);


--
-- Data for Name: categoria_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categoria_produto (id_categoria, categoria) FROM stdin;
1	Bebidas
3	Doces
4	Porções
2	Pizzas Especiais
6	Pizzas
7	Vinhos
\.


--
-- Data for Name: comanda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comanda (id_comanda, id_mesa, valor_total, data_abertura, data_fechamento) FROM stdin;
3	8	\N	2022-12-02 00:39:53.372495	\N
8	4	\N	2022-12-03 22:59:42.748522	\N
10	5	\N	2022-12-03 23:02:59.008485	\N
6	6	80.00	2022-12-02 00:42:47.53419	2022-12-04 00:55:54.880516
9	3	30.00	2022-12-03 23:02:48.486478	2022-12-04 01:12:27.775918
11	6	65.00	2022-12-04 00:56:02.832908	2022-12-05 13:50:24.090737
7	7	14.00	2022-12-03 21:33:27.787989	2022-12-05 14:46:20.132336
12	7	45.00	2022-12-05 14:46:26.350076	2022-12-05 14:49:07.819638
14	2	21.00	2022-12-05 22:23:50.792867	2022-12-05 22:24:52.929517
15	21	7.00	2022-12-05 22:42:39.968884	2022-12-05 22:42:55.295193
13	1	54.00	2022-12-05 14:56:10.567233	2022-12-06 14:29:09.263527
16	1	\N	2022-12-07 18:31:15.42975	\N
17	23	\N	2022-12-07 18:58:10.666721	\N
18	2	22.00	2022-12-07 19:14:39.122873	2022-12-07 19:15:05.586554
19	2	22.00	2022-12-08 01:06:03.928655	2022-12-08 01:06:34.919871
\.


--
-- Data for Name: consome; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.consome (id_produto, id_comanda, quantidade, valor, custo, id_consome) FROM stdin;
3	16	1	7.00	5.00	21
6	16	1	15.00	10.00	23
4	16	1	5.00	3.00	22
1	6	8	10.00	6.00	1
1	9	3	10.00	6.00	2
3	18	1	7.00	5.00	25
2	18	1	15.00	5.00	26
3	19	1	7.00	5.00	27
6	19	1	15.00	10.00	28
4	11	3	5.00	3.00	9
1	11	5	10.00	6.00	3
3	7	2	7.00	5.00	10
1	12	1	10.00	6.00	11
6	12	1	15.00	10.00	12
12	12	1	20.00	14.00	13
5	13	2	2.00	1.00	18
1	13	3	10.00	6.00	14
3	14	3	7.00	5.00	19
3	15	1	7.00	5.00	20
4	13	4	5.00	3.00	16
8	16	2	60.00	45.00	24
\.


--
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.forma_pagamento (id_forma_pagamento, tipo) FROM stdin;
1	Pix
2	Débito
3	Crédito
4	Dinheiro
6	Cheque
7	Bitcoin
\.


--
-- Data for Name: mesa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mesa (id_mesa, nome) FROM stdin;
1	1
2	2
3	3
4	4
5	5
6	6
7	7
8	8
9	9
10	10
11	11
12	12
13	13
14	14
15	15
16	16
17	17
18	18
19	19
20	20
22	Casal 1
21	VIP
23	Aniversariante
\.


--
-- Data for Name: pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pagamento (id_pagamento, id_comanda, id_forma_pagamento, valor, data, cpf_cliente) FROM stdin;
1	6	4	20.00	2022-12-03 20:50:36.401245	12345678955
2	6	1	22.00	2022-12-03 21:26:37.778679	\N
3	6	1	25.00	2022-12-03 21:27:14.652137	\N
4	6	1	3.00	2022-12-03 21:34:21.830353	\N
5	6	1	1.00	2022-12-04 00:49:30.054438	11111111111
6	6	2	9.00	2022-12-04 00:49:49.635811	12312312322
7	9	1	12.00	2022-12-04 01:01:38.175084	91569908044
10	9	1	0.01	2022-12-04 01:10:02.389092	\N
11	9	3	17.99	2022-12-04 01:12:25.736012	\N
12	11	1	5.00	2022-12-04 01:14:44.748256	\N
13	11	1	60.00	2022-12-05 13:21:05.620423	\N
14	7	1	5.00	2022-12-05 14:44:01.386878	\N
15	7	3	1.00	2022-12-05 14:44:18.446579	\N
16	7	2	8.00	2022-12-05 14:45:13.923746	\N
17	12	3	45.00	2022-12-05 14:49:03.779998	\N
18	13	1	43.99	2022-12-05 14:58:23.987136	\N
19	13	4	0.01	2022-12-05 14:58:44.559388	\N
20	13	2	4.30	2022-12-05 15:03:57.143284	06414896179
21	13	1	0.10	2022-12-05 15:04:24.531057	\N
22	14	1	14.00	2022-12-05 22:24:09.694659	\N
23	14	2	7.00	2022-12-05 22:24:41.518648	\N
24	15	1	7.00	2022-12-05 22:42:54.15433	\N
25	13	4	0.60	2022-12-05 23:00:04.189944	\N
26	13	3	5.00	2022-12-06 14:29:05.935029	\N
27	16	1	5.00	2022-12-07 18:32:41.564663	\N
28	16	2	22.00	2022-12-07 18:33:32.34515	\N
29	18	1	11.00	2022-12-07 19:14:55.460419	\N
30	18	2	11.00	2022-12-07 19:15:02.294941	\N
31	19	1	11.00	2022-12-08 01:06:26.649525	\N
32	19	2	11.00	2022-12-08 01:06:31.504441	\N
\.


--
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produto (id_produto, id_categoria, valor, custo, nome) FROM stdin;
2	3	15.00	5.00	Açaí 500ml
3	1	7.00	5.00	Fanta 1L
4	1	5.00	3.00	Coca Lata
6	4	15.00	10.00	Batata Média
7	4	25.00	20.00	Batata Grande
8	2	60.00	45.00	Camarão
9	2	65.00	50.00	Margherita
10	6	45.00	20.00	Calabresa
11	6	45.00	25.00	Bacon
12	7	20.00	14.00	Sangue De Boi Suave
1	1	10.00	11.00	Coca Cola 1L
5	3	1.50	1.00	Halls
\.


--
-- Name: categoria_produto_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_produto_id_categoria_seq', 8, true);


--
-- Name: comanda_id_comanda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comanda_id_comanda_seq', 19, true);


--
-- Name: comanda_id_mesa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comanda_id_mesa_seq', 1, false);


--
-- Name: consome_id_comanda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.consome_id_comanda_seq', 1, false);


--
-- Name: consome_id_consome_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.consome_id_consome_seq', 28, true);


--
-- Name: consome_id_produto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.consome_id_produto_seq', 1, false);


--
-- Name: forma_pagamento_id_forma_pagamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forma_pagamento_id_forma_pagamento_seq', 7, true);


--
-- Name: mesa_id_mesa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mesa_id_mesa_seq', 23, true);


--
-- Name: pagamento_id_comanda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagamento_id_comanda_seq', 1, false);


--
-- Name: pagamento_id_forma_pagamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagamento_id_forma_pagamento_seq', 1, false);


--
-- Name: pagamento_id_pagamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagamento_id_pagamento_seq', 32, true);


--
-- Name: produto_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_id_categoria_seq', 1, false);


--
-- Name: produto_id_produto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_id_produto_seq', 12, true);


--
-- Name: categoria_produto categoria_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT categoria_produto_pkey PRIMARY KEY (id_categoria);


--
-- Name: comanda comanda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT comanda_pkey PRIMARY KEY (id_comanda);


--
-- Name: consome consome_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consome
    ADD CONSTRAINT consome_pkey PRIMARY KEY (id_consome);


--
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id_forma_pagamento);


--
-- Name: mesa mesa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mesa
    ADD CONSTRAINT mesa_pkey PRIMARY KEY (id_mesa);


--
-- Name: pagamento pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_pkey PRIMARY KEY (id_pagamento);


--
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id_produto);


--
-- Name: comanda comanda_id_mesa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT comanda_id_mesa_fkey FOREIGN KEY (id_mesa) REFERENCES public.mesa(id_mesa);


--
-- Name: consome consome_id_comanda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consome
    ADD CONSTRAINT consome_id_comanda_fkey FOREIGN KEY (id_comanda) REFERENCES public.comanda(id_comanda);


--
-- Name: consome consome_id_produto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consome
    ADD CONSTRAINT consome_id_produto_fkey FOREIGN KEY (id_produto) REFERENCES public.produto(id_produto);


--
-- Name: pagamento pagamento_id_comanda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_id_comanda_fkey FOREIGN KEY (id_comanda) REFERENCES public.comanda(id_comanda);


--
-- Name: pagamento pagamento_id_forma_pagamento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_id_forma_pagamento_fkey FOREIGN KEY (id_forma_pagamento) REFERENCES public.forma_pagamento(id_forma_pagamento);


--
-- Name: produto produto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoria_produto(id_categoria);


--
-- PostgreSQL database dump complete
--

