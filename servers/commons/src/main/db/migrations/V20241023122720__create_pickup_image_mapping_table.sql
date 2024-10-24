CREATE TABLE pickup_image_mapping (
    id SERIAL PRIMARY KEY,
    task_id VARCHAR(10) NOT NULL REFERENCES tasks(task_id),
    pickup_details_id VARCHAR(10) NOT NULL REFERENCES pickup_details(pickup_details_id),
    order_image TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_task_id ON pickup_image_mapping (task_id);
