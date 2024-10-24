ALTER TABLE orders
ADD COLUMN order_id VARCHAR(10);

CREATE INDEX idx_orders_order_id ON orders(order_id);
