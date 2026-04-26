--
-- PostgreSQL database dump
--

\restrict 3XocRRp7Gy6Gzbmx7Z7uKbzpbLbH6Ibxaklh1ao05PYMhObV6WEbDgej8WhgSCU

-- Dumped from database version 18.2 (Postgres.app)
-- Dumped by pg_dump version 18.2 (Postgres.app)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: orderstatus; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.orderstatus AS ENUM (
    'CANCELLED',
    'CREATED',
    'DELIVERED',
    'PREPARING',
    'SHIPPED'
);


ALTER TYPE public.orderstatus OWNER TO postgres;

--
-- Name: CAST (public.orderstatus AS character varying); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (public.orderstatus AS character varying) WITH INOUT AS IMPLICIT;


--
-- Name: CAST (character varying AS public.orderstatus); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (character varying AS public.orderstatus) WITH INOUT AS IMPLICIT;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: category_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.category_seq OWNER TO postgres;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    id bigint NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    phone_number character varying(15)
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customer_seq OWNER TO postgres;

--
-- Name: dish; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dish (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255),
    price double precision NOT NULL,
    category_id bigint,
    menu_id bigint
);


ALTER TABLE public.dish OWNER TO postgres;

--
-- Name: dish_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dish_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.dish_seq OWNER TO postgres;

--
-- Name: menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu (
    id bigint NOT NULL,
    active boolean NOT NULL,
    description character varying(255),
    name character varying(255),
    restaurant_id bigint
);


ALTER TABLE public.menu OWNER TO postgres;

--
-- Name: menu_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.menu_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.menu_seq OWNER TO postgres;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    address character varying(255),
    amount integer NOT NULL,
    created_at timestamp(6) without time zone,
    total_price double precision NOT NULL,
    customer_id bigint,
    status public.orderstatus
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: orders_dishes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders_dishes (
    order_id bigint NOT NULL,
    dish_id bigint NOT NULL
);


ALTER TABLE public.orders_dishes OWNER TO postgres;

--
-- Name: orders_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_seq OWNER TO postgres;

--
-- Name: restaurant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.restaurant (
    id bigint NOT NULL,
    address character varying(255),
    city character varying(255),
    name character varying(255),
    rating double precision NOT NULL
);


ALTER TABLE public.restaurant OWNER TO postgres;

--
-- Name: restaurant_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.restaurant_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.restaurant_seq OWNER TO postgres;

--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (id, name) FROM stdin;
52	бургеры
252	суши
53	напитки
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (id, email, first_name, last_name, password, phone_number) FROM stdin;
53	eggg@gmail.com	егор	лесковский	1111	+37544552244
152	horny@gmail.com	Глеб	Нагорный	glebus	+37533668811
202	misha@gmail.com	Миха	Драко	vudfar-muDcu6-nyqqoq	+375259431225
203	derkachev@gmail.com	Максим	Деркачева	Witxa8-gesnic-xohbyf	+375256772903
204	checkpuk@gmail.com	Артем	Карасев	1234453123dfg	+375298436782
205	timber.enjoer@gmail.com	Дима	Хейлик	zxczxczxc	+375259939865
206	aaayyyy@gmail.com	Павел	Аникевич	1231345345	+375298439221
\.


--
-- Data for Name: dish; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dish (id, description, name, price, category_id, menu_id) FROM stdin;
52	булка соус котлета сыр	шефбургер	22	52	52
53	лаваш соус курица салат	доне	22	52	53
102	булочка сырX2 говядина кетчуп лук	дабл чиз	15	52	53
554	вода сахар зеленый чай фруктовый сок	Rich Tea	4	53	52
757	виноград вода сахар соль	виноградный сок	3	53	52
553	булочка курицаX2 беконX2 кетчуп огурцы острый соус	Дабл чикен	15	52	53
555	вода сахар апельсины	Апельсиновый сок	6	53	53
556	вода сахар вишня	Вишневый сок	6	53	53
602	сахар лимон вода газированая	спрайт	3	53	53
603	томаты вода сахар соль	Томатный сок	3	53	53
604	вода сахар яблоко	Яблочный сок	3	53	53
652	сахар арбуз вода газированая	арбузный	3	53	53
653	морковь вода сахар соль	морковный сок	3	53	53
654	виноград вода сахар соль	виноградный сок	3	53	53
703	сахар арбуз вода газированая	asdasdad	3	53	53
655	морковь вода сахар соль	морковный сок	3	53	52
103	булочка сырX3 говядинаX3 кетчуп лук	трипл чиз	22	52	202
153	булочка сырX4 говядинаX4 кетчуп лук дор блю	квадро чиз	30	52	202
252	черная булочка сыр говядинаX2 кетчуп лук дор блю огурчики перец чили	блэк бургер	27	52	202
302	булочка сыр кетчуп	мини бургер	2	52	202
552	булочка курицаX3 беконX2 кетчуп огурцы острый соус	Трипл чикен	20	52	202
152	булка курица салат соус	чикен бургер	10	52	252
202	булочка сыр говядинаX2 кетчуп лук дор блю огурчики перец чили	хот бургер	23	52	252
352	булочка сырX3 чизбургерX3 кетчуп	трипл чизбургер	13	52	252
755	сахар арбуз вода газированая	asdasdad	3	53	53
756	морковь вода сахар соль	error	3	53	53
758	морковь вода сахар соль	морковный сок	3	53	53
\.


--
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.menu (id, active, description, name, restaurant_id) FROM stdin;
53	t	15:00 - 22:00	ХАЙП	53
52	t	10:00 - 22:00	ХИТ	53
102	t	15:00 - 22:00	ХАЙП	52
202	t	23-24	лэйт	153
252	t	8:00 - 11	завтраки	52
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (id, address, amount, created_at, total_price, customer_id, status) FROM stdin;
203	кальварийская 16	3	2026-04-09 22:36:03.969044	72	152	\N
302	гикало 9	4	2026-04-14 22:48:35.791767	75	202	\N
303	Кальварийская 16, 24	7	2026-04-14 22:49:27.601842	111	152	\N
304	вокзал 1-ая платформа	6	2026-04-14 22:53:11.193008	53	202	\N
305	Общага бгур 	2	2026-04-14 22:53:37.30954	24	203	\N
306	Ленина д10	2	2026-04-14 22:54:18.451329	24	204	\N
307	Гомель Каратэдо д12	11	2026-04-14 22:55:21.059888	40	205	\N
308	Лошица д120 кв 25	5	2026-04-14 22:56:08.105025	101	206	\N
\.


--
-- Data for Name: orders_dishes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders_dishes (order_id, dish_id) FROM stdin;
203	52
203	153
203	552
302	103
302	552
302	152
302	202
303	52
303	53
303	102
303	554
303	553
303	555
303	252
304	53
304	554
304	553
304	556
304	602
304	603
305	103
305	302
306	554
306	552
307	554
307	555
307	556
307	603
307	604
307	652
307	653
307	654
307	655
307	757
307	758
308	103
308	153
308	252
308	302
308	552
\.


--
-- Data for Name: restaurant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.restaurant (id, address, city, name, rating) FROM stdin;
52	Тимерязева д9	Минск	SUPER BURGER KING	0
153	гикало д9	Минск	Папа донер	0
53	никифорова 23	Минск	КФС	0
\.


--
-- Name: category_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_seq', 301, true);


--
-- Name: customer_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_seq', 251, true);


--
-- Name: dish_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dish_seq', 801, true);


--
-- Name: menu_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.menu_seq', 301, true);


--
-- Name: orders_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_seq', 351, true);


--
-- Name: restaurant_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.restaurant_seq', 201, true);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: dish dish_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dish
    ADD CONSTRAINT dish_pkey PRIMARY KEY (id);


--
-- Name: menu menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: restaurant restaurant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restaurant
    ADD CONSTRAINT restaurant_pkey PRIMARY KEY (id);


--
-- Name: dish fk3h7qfevodvyk24ss68mwu8ap6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dish
    ADD CONSTRAINT fk3h7qfevodvyk24ss68mwu8ap6 FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: orders fk624gtjin3po807j3vix093tlf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk624gtjin3po807j3vix093tlf FOREIGN KEY (customer_id) REFERENCES public.customer(id);


--
-- Name: orders_dishes fk6on9jka58uq379lr0ai2o7c6w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders_dishes
    ADD CONSTRAINT fk6on9jka58uq379lr0ai2o7c6w FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: menu fkblwdtxevpl4mrds8a12q0ohu6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT fkblwdtxevpl4mrds8a12q0ohu6 FOREIGN KEY (restaurant_id) REFERENCES public.restaurant(id);


--
-- Name: dish fkljuksxg35var0r9a3y09l148h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dish
    ADD CONSTRAINT fkljuksxg35var0r9a3y09l148h FOREIGN KEY (menu_id) REFERENCES public.menu(id);


--
-- Name: orders_dishes fkqo3p6jtee1uk27en37jjbu2rt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders_dishes
    ADD CONSTRAINT fkqo3p6jtee1uk27en37jjbu2rt FOREIGN KEY (dish_id) REFERENCES public.dish(id);


--
-- PostgreSQL database dump complete
--

\unrestrict 3XocRRp7Gy6Gzbmx7Z7uKbzpbLbH6Ibxaklh1ao05PYMhObV6WEbDgej8WhgSCU

