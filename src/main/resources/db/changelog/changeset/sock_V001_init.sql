create table if not exists socks (
    id bigint primary key generated always as identity unique,
    color_hex varchar(7) not null,
    cotton_percentage INT NOT NULL CHECK (cotton_percentage >= 0 AND cotton_percentage <= 100),
    quantity INT NOT NULL CHECK (quantity >= 0)
);