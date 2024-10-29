ALTER TABLE recon
ADD CONSTRAINT fk_order_id
    FOREIGN KEY (order_id) REFERENCES orders(order_id);