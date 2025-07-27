-- liquibase formatted sql

--changeset azatsepina: 1

CREATE TABLE users
(
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email      VARCHAR(255) UNIQUE NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    password      VARCHAR(255),
    phone         VARCHAR(255),
    role          VARCHAR(255),
    image_id         INTEGER,
    FOREIGN KEY (image_id) REFERENCES images (id)
        ON DELETE CASCADE
);

--changeset kyakovlev: 2
CREATE TABLE ads
(
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    price       INT,
    user_id     INTEGER NOT NULL,
    image_id    INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (image_id) REFERENCES images (id)
        ON DELETE CASCADE
);

--changeset ikanashnik: 3
CREATE TABLE comments
(
    id         INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id    INTEGER NOT NULL,
    ad_id      INTEGER,
    created_at INTEGER,
    text       TEXT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (ad_id) REFERENCES ads (id)
        ON DELETE CASCADE
);


