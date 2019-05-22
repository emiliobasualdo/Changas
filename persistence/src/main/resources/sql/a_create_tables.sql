CREATE TABLE IF NOT EXISTS users
(
    user_id SERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    tel     VARCHAR(100) NOT NULL,
    email   VARCHAR(100) NOT NULL UNIQUE ,
    passwd  VARCHAR(100) NOT NULL,
    rating  DOUBLE PRECISION default -1.0,
    enabled BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS changas
(
    changa_id     SERIAL PRIMARY KEY,
    user_id       SERIAL NOT NULL,
    street        VARCHAR(100),
    neighborhood  VARCHAR(100) NOT NULL,
    number        INTEGER ,
    creation_date TIMESTAMP, /* falta not null, hay que reponer la información en el .sql*/
    title         VARCHAR(100) NOT NULL,
    description   VARCHAR(1000) NOT NULL ,
    state         VARCHAR(100) DEFAULT 'emitted' NOT NULL,
    price         DOUBLE PRECISION NOT NULL,
    category      VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS user_inscribed
(
    user_id   SERIAL NOT NULL REFERENCES users (user_id),
    changa_id SERIAL NOT NULL  REFERENCES changas (changa_id),
    state     VARCHAR(100) DEFAULT 'requested',
    rating  DOUBLE PRECISION,
    PRIMARY KEY (user_id, changa_id)
);

CREATE TABLE IF NOT EXISTS verification_token
(
   token_id SERIAL PRIMARY KEY,
   token VARCHAR(255),
   user_id SERIAL NOT NULL REFERENCES users (user_id),
   expiry_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    key VARCHAR(30) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS neighborhoods
(
    key VARCHAR(50) PRIMARY KEY
);