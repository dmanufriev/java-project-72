CREATE TABLE IF NOT EXISTS urls (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS url_checks (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    url_id BIGINT REFERENCES urls(id) NOT NULL,
    status_code INTEGER NOT NULL,
    title VARCHAR(255),
    h1 VARCHAR(255),
    description CHARACTER VARYING,
    created_at TIMESTAMP NOT NULL
);
