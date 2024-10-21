CREATE TABLE pickup_details (
    id SERIAL PRIMARY KEY,
    pickup_details_id VARCHAR(10) UNIQUE,
    task_id VARCHAR(10) NOT NULL,
    order_id VARCHAR(10) NOT NULL,
    hlp_id VARCHAR(10) NOT NULL,
    franchise_id VARCHAR(10) NOT NULL,
    status VARCHAR NOT NULL CHECK (status IN ('Pending', 'Picked Up', 'Delivered')),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
);

CREATE INDEX idx_pickup_details_task_id ON pickup_details (task_id);
CREATE INDEX idx_pickup_details_order_id ON pickup_details (order_id);
CREATE INDEX idx_pickup_details_hlp_id ON pickup_details (hlp_id);
CREATE INDEX idx_pickup_details_franchise_id ON pickup_details (franchise_id);
