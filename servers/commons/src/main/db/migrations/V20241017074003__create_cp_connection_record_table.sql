CREATE TABLE IF NOT EXISTS cp_connection_record(
  id                    SERIAL PRIMARY KEY,
  cp_id                 INT NOT NULL,
  franchise_id          VARCHAR(255) NULL,
  manifest_image_url    VARCHAR(1024) NULL,
  created_at            TIMESTAMP NOT NULL
  updated_at            TIMESTAMP NOT NULL
);