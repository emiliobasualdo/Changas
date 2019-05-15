CREATE TABLE IF NOT EXISTS users
(
    user_id SERIAL PRIMARY KEY,
    name    VARCHAR(100),
    surname VARCHAR(100),
    tel     VARCHAR(100),
    email   VARCHAR(100) NOT NULL UNIQUE ,
    passwd  VARCHAR(100) NOT NULL ,
    enabled BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS changas
(
    changa_id     SERIAL PRIMARY KEY,
    user_id       SERIAL NOT NULL,
    street        VARCHAR(100),
    neighborhood  VARCHAR(100),
    number        INTEGER,
    creation_date TIMESTAMP,
    title         VARCHAR(100),
    description   VARCHAR(100),
    state         VARCHAR(100) DEFAULT 'emitted',
    price         DOUBLE PRECISION,
    category      VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS user_inscribed
(
    user_id   SERIAL NOT NULL REFERENCES users (user_id),
    changa_id SERIAL NOT NULL  REFERENCES changas (changa_id),
    state     VARCHAR(100) DEFAULT 'requested',
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
     locale VARCHAR(5) NOT NULL,
     key VARCHAR(30) NOT NULL,
     message VARCHAR(30),
     PRIMARY KEY (locale, key)
);