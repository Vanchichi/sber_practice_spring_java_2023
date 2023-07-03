create schema if not exists products_vanchugova_da;

create table products_vanchugova_da.product
(
    id    integer generated always as identity
        primary key,
    name  varchar(255) not null,
    price numeric      not null,
    count integer      not null
);

create table products_vanchugova_da.cart
(
    id        integer generated always as identity
        primary key,
    promocode varchar(255)
);

create table products_vanchugova_da.client
(
    id       integer generated always as identity primary key,
    name     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    cart_id  integer      not null
        constraint client_cart_id_fk
            references products_vanchugova_da.cart
);

create table products_vanchugova_da.product_client
(
    id         integer generated always as identity primary key,
    id_product integer not null
        constraint product_client_products_id_fk
            references products_vanchugova_da.product,
    id_cart    integer not null
        constraint product_client_cart_id_fk
            references products_vanchugova_da.cart,
    count      integer not null
);