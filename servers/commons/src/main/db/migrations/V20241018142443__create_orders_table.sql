-- V2__create_orders_table.sql
CREATE TABLE orders_test (
    id SERIAL PRIMARY KEY,
    order_id VARCHAR(10) UNIQUE NOT NULL,
    order_number VARCHAR(255) NOT NULL,
    awb_number VARCHAR(255),
    courier_partner VARCHAR(255) NOT NULL,
    mode_of_transport VARCHAR(255) NOT NULL,
    sender_name VARCHAR(255) NOT NULL,
    sender_mobile VARCHAR(255) NOT NULL,
    sender_address TEXT NOT NULL,
    sender_pincode INT NOT NULL,
    sender_city VARCHAR(255) NOT NULL,
    sender_state VARCHAR(255) NOT NULL,
    sender_latitude DOUBLE PRECISION NOT NULL,
    sender_longitude DOUBLE PRECISION NOT NULL,
    receiver_name VARCHAR(255) NOT NULL,
    receiver_mobile VARCHAR(255) NOT NULL,
    receiver_address TEXT NOT NULL,
    receiver_home_number VARCHAR(255),
    receiver_city VARCHAR(255) NOT NULL,
    receiver_pincode INT NOT NULL,
    receiver_state VARCHAR(255) NOT NULL,
    material_type VARCHAR(255) NOT NULL,
    material_weight INT NOT NULL,
    dimensions_length DOUBLE PRECISION,
    dimensions_breadth DOUBLE PRECISION,
    dimensions_height DOUBLE PRECISION,
    volumetric_weight INT,
    shipping_label_link VARCHAR(255),
    pickup_datetime VARCHAR(255) NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    hlp_order_id INT,
    hlp_order_status VARCHAR(255),
    vehicle_type VARCHAR(255),
    franchise_id VARCHAR(255) NOT NULL,
    account_id INT,
    account_code VARCHAR(255),
    is_franchise_updated BOOLEAN DEFAULT false,
    team_id VARCHAR(255),
    status_update_sync VARCHAR(255) DEFAULT 'Pending',
    sender_geo_id VARCHAR(255),
    receiver_geo_id VARCHAR(255),
    status_id INT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
