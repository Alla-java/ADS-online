-- liquibase formatted sql

--changeset azatsepina: 1

CREATE TABLE users
(
    id            BIGINT PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    password      VARCHAR(255),
    phone         VARCHAR(255),
    register_date DATE,
    role          VARCHAR(255),
    image_id         BIGINT,
    FOREIGN KEY (image_id) REFERENCES images (id)
        ON DELETE CASCADE
);