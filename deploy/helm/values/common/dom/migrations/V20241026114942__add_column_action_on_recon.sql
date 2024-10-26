ALTER TABLE recon
ADD COLUMN action VARCHAR(64);
CREATE INDEX idx_return_requested ON recon (return_requested);