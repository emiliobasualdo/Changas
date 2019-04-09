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
    passwd  VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS changas
(
    changa_id     INTEGER PRIMARY KEY,
    user_id       INTEGER NOT NULL ,
    street        VARCHAR(100),
    neighborhood  VARCHAR(100),
    number        INTEGER,
    creation_date TIMESTAMP,
    title         VARCHAR(100),
    description   VARCHAR(100),
    state         VARCHAR(100),
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
    user_id   INTEGER NOT NULL,
    changa_id INTEGER NOT NULL ,
    state     VARCHAR(100) DEFAULT 'requested',
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (changa_id) REFERENCES changas (changa_id)
);

/*
CREATE TABLE IF NOT EXISTS users
(
    user_id INTEGER IDENTITY PRIMARY KEY,
    name    VARCHAR(100),
    surname VARCHAR(100),
    tel     VARCHAR(100),
    email   VARCHAR(100),
    passwd  VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS changas
(
    changa_id     INTEGER IDENTITY PRIMARY KEY,
    user_id       INTEGER,
    street        VARCHAR(100),
    neighborhood  VARCHAR(100),
    number        INTEGER,
    creation_date TIMESTAMP,
    title         VARCHAR(100),
    description   VARCHAR(100),
    state         VARCHAR(100),
    price         DOUBLE PRECISION,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS user_owns
(
    user_id   INTEGER IDENTITY,
    changa_id INTEGER UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (changa_id) REFERENCES changas (changa_id)
);

CREATE TABLE IF NOT EXISTS user_inscribed
(
    user_id   INTEGER IDENTITY,
    changa_id INTEGER ,
    state     VARCHAR(100) DEFAULT 'requested',
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (changa_id) REFERENCES changas (changa_id)
);*/
