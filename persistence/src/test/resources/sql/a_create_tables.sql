DROP TABLE IF EXISTS user_inscribed;
DROP TABLE IF EXISTS user_owns;
DROP TABLE IF EXISTS changas;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    user_id INTEGER IDENTITY PRIMARY KEY,
    name    VARCHAR(100),
    surname VARCHAR(100),
    tel     VARCHAR(100),
    email   VARCHAR(100) UNIQUE NOT NULL,
    passwd  VARCHAR(100) NOT NULL,
    enabled BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS changas
(
    changa_id     INTEGER IDENTITY PRIMARY KEY,
    user_id       INTEGER NOT NULL ,
    street        VARCHAR(100),
    neighborhood  VARCHAR(100),
    number        INTEGER,
    creation_date TIMESTAMP,
    title         VARCHAR(100),
    description   VARCHAR(100) ,
    state         VARCHAR(100) DEFAULT 'emitted',
    category      VARCHAR(100),
    price         DOUBLE PRECISION,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS user_owns
(
    user_id   INTEGER NOT NULL,
    changa_id INTEGER IDENTITY NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (changa_id) REFERENCES changas (changa_id)
);

CREATE TABLE IF NOT EXISTS user_inscribed
(
    user_id   INTEGER NOT NULL REFERENCES users (user_id),
    changa_id INTEGER NOT NULL REFERENCES changas (changa_id),
    state     VARCHAR(100) DEFAULT 'requested',
    PRIMARY KEY (user_id, changa_id)
);

CREATE TABLE IF NOT EXISTS categories
(
    locale VARCHAR(5) NOT NULL,
    key VARCHAR(30) NOT NULL,
    message VARCHAR(30),
    PRIMARY KEY (locale, key)
);