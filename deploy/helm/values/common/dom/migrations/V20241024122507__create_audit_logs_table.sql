CREATE TABLE audit_logs (
    id SERIAL PRIMARY KEY,
    audit_log_id VARCHAR(10) NOT NULL,
    entity_id VARCHAR(255) NOT NULL,
    entity_type VARCHAR(50) NOT NULL CHECK (entity_type IN ('Order', 'Recon', 'Task', 'Franchise', 'Holiday', 'PickupTask')),
    status VARCHAR(50) NOT NULL CHECK (status IN (
        'Created', 'Updated', 'Deleted',
        'Pending', 'Shipped', 'Delivered',
        'Cancelled', 'Return Requested', 'Completed', 'In Progress', 'Failed', 'Picked Up'
    )),
    message VARCHAR(255),
    change_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_logs_entity_id ON audit_logs (entity_id);
CREATE INDEX idx_audit_logs_entity_type ON audit_logs (entity_type);
CREATE INDEX idx_audit_logs_status ON audit_logs (status);
CREATE INDEX idx_audit_logs_change_timestamp ON audit_logs (change_timestamp);
CREATE INDEX idx_audit_logs_entity_id_entity_type ON audit_logs (entity_id, entity_type);

