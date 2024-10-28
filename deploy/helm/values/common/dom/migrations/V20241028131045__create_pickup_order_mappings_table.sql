CREATE TABLE pickup_order_mappings (
    id SERIAL PRIMARY KEY,
    mapping_id VARCHAR(36) UNIQUE,
    pickup_details_id VARCHAR(36) REFERENCES pickup_details(pickup_details_id),
    order_id VARCHAR(36) REFERENCES orders(order_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);