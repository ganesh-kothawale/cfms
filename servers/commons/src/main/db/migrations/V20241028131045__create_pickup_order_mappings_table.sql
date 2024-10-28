CREATE TABLE pickup_order_mappings (
    id SERIAL PRIMARY KEY,
    mapping_id VARCHAR UNIQUE,
    order_id VARCHAR REFERENCES orders(order_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);