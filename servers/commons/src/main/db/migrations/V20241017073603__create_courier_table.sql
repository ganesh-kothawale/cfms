CREATE TABLE IF NOT EXISTS courier(
  id                    SERIAL PRIMARY KEY,
  name                  VARCHAR(128) NOT NULL,
  created_at            TIMESTAMP NOT NULL
);