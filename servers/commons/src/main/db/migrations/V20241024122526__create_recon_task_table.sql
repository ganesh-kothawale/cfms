-- V1__create_recon_table.sql
CREATE TABLE recon (
    id SERIAL PRIMARY KEY,
    recon_id VARCHAR(10) UNIQUE NOT NULL,
    order_id VARCHAR(10) NOT NULL,
    task_id VARCHAR(10) NOT NULL,
    team_id VARCHAR(10) NOT NULL,
    recon_status VARCHAR(50) NOT NULL CHECK (recon_status IN ('Pending', 'Completed', 'Return Requested')),
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
    updated_at TIMESTAMP DEFAULT now()
);

CREATE INDEX idx_recon_order_id ON recon (order_id);
CREATE INDEX idx_recon_task_id ON recon (task_id);
CREATE INDEX idx_recon_team_id ON recon (team_id);
CREATE INDEX idx_recon_recon_status ON recon (recon_status);
CREATE INDEX idx_recon_created_at ON recon (created_at);
CREATE INDEX idx_recon_updated_at ON recon (updated_at);


