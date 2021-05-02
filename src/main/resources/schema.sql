DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS product;

CREATE TABLE customer
(
    id          INT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(250) NOT NULL,
    uniqueID    VARCHAR(250) NOT NULL,
    total_points DOUBLE       NOT NULL,
    PRIMARY KEY (uniqueID)
);

CREATE TABLE product
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(250) NOT NULL,
    code INTEGER      NOT NULL,
    cost DOUBLE       NOT NULL,
    PRIMARY KEY (code)
);