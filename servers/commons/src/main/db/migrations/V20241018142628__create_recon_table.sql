-- V1__create_recon_table.sql
CREATE TABLE recon (
    id SERIAL PRIMARY KEY,
    recon_id VARCHAR(10) UNIQUE NOT NULL,
    order_id VARCHAR(10) NOT NULL, -- Reference to orders table
    task_id VARCHAR(10) NOT NULL, -- Reference to tasks table
    team_id VARCHAR(10) NOT NULL, -- Reference to teams table
    recon_status VARCHAR(50) NOT NULL CHECK (recon_status IN ('Pending', 'Completed', 'Return Requested')), -- Enum for recon status
    packaging_required BOOLEAN DEFAULT false,
    pre_packaging_image_url VARCHAR(255),
    shipment_is_envelope_or_document BOOLEAN DEFAULT false,
    shipment_weight DECIMAL(10, 2),
    weight_photo_url VARCHAR(255),
    shipment_dimensions_cm_or_inch VARCHAR(255),
    shipment_length DECIMAL(10, 2),
    shipment_width DECIMAL(10, 2),
    shipment_height DECIMAL(10, 2),
    dimensions_photo_urls VARCHAR(255),
    return_requested BOOLEAN DEFAULT false,
    return_image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders_test(order_id) ON DELETE CASCADE,
    CONSTRAINT fk_task_id FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE SET NULL,
    CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES teams(team_id) ON DELETE SET NULL
);
