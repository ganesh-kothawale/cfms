CREATE TABLE IF NOT EXISTS hlps(
    id                  SERIAL PRIMARY KEY,
    hlp_order_id        VARCHAR(64) NOT NULL UNIQUE,
    hlp_order_status    VARCHAR(64) NULL,
    otp                 VARCHAR(64) NULL,
    rider_name          VARCHAR(128) NULL,
    rider_number        VARCHAR(64) NULL,
    vehicle_type        VARCHAR(64) NULL,
    franchise_id        VARCHAR(64) NOT NULL,
    created_at          TIMESTAMP NOT NULL,
    updated_at          TIMESTAMP NOT NULL
);