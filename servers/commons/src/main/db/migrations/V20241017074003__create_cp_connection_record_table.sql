CREATE TABLE IF NOT EXISTS cp_connection_record(
  id                    SERIAL PRIMARY KEY,
  cp_id                 VARCHAR(256) NOT NULL,
  franchise_id          INT NOT NULL,
  order_id              INT NULL,
  manifest_image_url    VARCHAR(1024) NULL,
  arrival_timestamp     TIMESTAMP NULL,
  created_at            TIMESTAMP NOT NULL
);