DROP TABLE IF EXISTS "user";

CREATE TABLE "user"(
    id TEXT NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    bank_account TEXT NOT NULL,
    email TEXT NOT NULL
);

DROP TABLE IF EXISTS product;

CREATE TABLE product(
    name TEXT NOT NULL,
    code TEXT NOT NULL PRIMARY KEY,
    description TEXT NOT NULL,
    price BIGINT NOT NULL
);

DROP TABLE IF EXISTS shopping_cart;

CREATE TABLE shopping_cart(
    "user" TEXT NOT NULL,
    product_code TEXT NOT NULL PRIMARY KEY,
    quantity INT NOT NULL
);
