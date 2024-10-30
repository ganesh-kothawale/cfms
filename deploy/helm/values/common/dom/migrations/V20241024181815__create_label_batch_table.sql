CREATE TABLE shipping_label_batches (
    id SERIAL PRIMARY KEY,
    batch_id VARCHAR(10) NOT NULL,
    franchise_id VARCHAR(255) NOT NULL,
    order_ids VARCHAR(255)[] NOT NULL,
    created_at TIMESTAMP NOT NULL,
    bucket_location VARCHAR(255),
    object_id VARCHAR(255)
);

CREATE INDEX idx_franchise_id ON shipping_label_batches (franchise_id);
CREATE INDEX idx_batch_id ON shipping_label_batches (batch_id);