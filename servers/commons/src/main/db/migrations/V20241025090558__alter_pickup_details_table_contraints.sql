ALTER TABLE pickup_details
DROP CONSTRAINT IF EXISTS pickup_details_status_check;

ALTER TABLE pickup_details
ADD CONSTRAINT pickup_details_status_check
CHECK (status IN ('Pending', 'Picked Up', 'Delivered', 'PickedUp'));

ALTER TABLE pickup_details
ADD CONSTRAINT fk_task_id
    FOREIGN KEY (task_id) REFERENCES tasks(task_id);

ALTER TABLE pickup_details
ADD CONSTRAINT fk_order_id
    FOREIGN KEY (order_id) REFERENCES orders(order_id);

ALTER TABLE pickup_details
ADD CONSTRAINT fk_hlp_id
    FOREIGN KEY (hlp_id) REFERENCES hlps(hlp_order_id);

ALTER TABLE pickup_details
ADD COLUMN order_images VARCHAR(512);
