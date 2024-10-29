ALTER TABLE pickup_details
DROP CONSTRAINT fk_order_id,
DROP COLUMN order_id,
DROP COLUMN status;

ALTER TABLE pickup_details
ADD COLUMN package_received INT;