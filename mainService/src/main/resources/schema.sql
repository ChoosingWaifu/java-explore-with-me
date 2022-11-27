CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
                                     email VARCHAR(100) NOT NULL,
                                     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL,
                                          CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS events (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     annotation VARCHAR(2000) NOT NULL,
                                     category_id BIGINT NOT NULL,
                                     confirmed_requests BIGINT,
                                     created_on TIMESTAMP NOT NULL,
                                     description VARCHAR(7000) NOT NULL,
                                     event_date TIMESTAMP NOT NULL,
                                     initiator_id BIGINT NOT NULL,
                                     lat FLOAT NOT NULL,
                                     lon FLOAT NOT NULL,
                                     paid BOOLEAN,
                                     participant_limit BIGINT,
                                     published_on TIMESTAMP,
                                     request_moderation BOOLEAN,
                                     state VARCHAR NOT NULL,
                                     title VARCHAR(120) NOT NULL,
                                     views BIGINT,
                                     FOREIGN KEY (category_id) REFERENCES categories (id),
                                     FOREIGN KEY (initiator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS compilations (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     pinned BOOLEAN NOT NULL,
                                     title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS requests (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     created TIMESTAMP NOT NULL,
                                     event BIGINT NOT NULL,
                                     requester BIGINT NOT NULL,
                                     status VARCHAR NOT NULL,
                                     FOREIGN KEY (event) REFERENCES events(id),
                                     FOREIGN KEY (requester) REFERENCES users (id),
                                     CONSTRAINT UQ_REQUEST UNIQUE (event, requester)
);

CREATE TABLE IF NOT EXISTS compilations_events (
                                     compilation_id BIGINT NOT NULL,
                                     event_id BIGINT NOT NULL,
                                     PRIMARY KEY (compilation_id, event_id),
                                     FOREIGN KEY (compilation_id) references compilations(id),
                                     FOREIGN KEY (event_id) references events(id)

);



