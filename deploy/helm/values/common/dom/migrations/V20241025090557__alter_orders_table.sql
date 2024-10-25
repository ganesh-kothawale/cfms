ALTER TABLE orders
ADD CONSTRAINT unique_order_id UNIQUE (order_id);