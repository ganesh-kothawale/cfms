CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    order_number VARCHAR(128) NOT NULL UNIQUE,
    awb_number VARCHAR(128),
    courier_partner VARCHAR(64) NOT NULL,
    mode_of_transport VARCHAR(64) NOT NULL,
    sender_name VARCHAR(255) NOT NULL,
    sender_mobile VARCHAR(255) NOT NULL,
    sender_address TEXT NOT NULL,
    sender_pincode INT NOT NULL,
    sender_city VARCHAR(128) NOT NULL,
    sender_state VARCHAR(128) NOT NULL,
    sender_latitude DECIMAL(10, 6) NOT NULL,
    sender_longitude DECIMAL(10, 6) NOT NULL,
    receiver_name VARCHAR(255) NOT NULL,
    receiver_mobile VARCHAR(128) NOT NULL,
    receiver_address TEXT NOT NULL,
    receiver_home_number VARCHAR(255) NOT NULL,
    receiver_city VARCHAR(255) NOT NULL,
    receiver_pincode INT NOT NULL,
    receiver_state VARCHAR(128) NOT NULL,
    material_type VARCHAR(255) NOT NULL,
    material_weight INT NOT NULL,
    dimensions_length DECIMAL(10, 2),
    dimensions_breadth DECIMAL(10, 2),
    dimensions_height DECIMAL(10, 2),
    volumetric_weight INT,
    shipping_label_link VARCHAR(512),
    pickup_datetime VARCHAR(128) NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    hlp_order_id VARCHAR(64),
    hlp_order_status VARCHAR(255) NOT NULL,
    vehicle_type VARCHAR(255),
    franchise_id VARCHAR(128) NOT NULL,
    account_id INT,
    account_code VARCHAR(255),
    is_franchise_updated BOOLEAN DEFAULT FALSE,
    team_id VARCHAR(255),
    status_update_sync VARCHAR(255) DEFAULT 'Pending',
    sender_geo_id VARCHAR(255),
    receiver_geo_id VARCHAR(255),
    status_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_franchise_id FOREIGN KEY (franchise_id) REFERENCES franchises(franchise_id),
    CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES teams(team_id),
    CONSTRAINT fk_hlp_order_id FOREIGN KEY (hlp_order_id) REFERENCES hlps(hlp_order_id)
);

CREATE INDEX idx_orders_id ON orders(id);
CREATE INDEX idx_orders_order_number ON orders(order_number);
CREATE INDEX idx_orders_franchise_id ON orders(franchise_id);