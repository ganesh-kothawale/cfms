-- V1__create_tasks_table.sql
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    task_id VARCHAR(10) UNIQUE NOT NULL,
    flow_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('Pending', 'In Progress', 'Completed', 'Failed')),
    package_received INT,
    scheduled_slot TIMESTAMP,
    team_id VARCHAR(10),
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES teams(team_id)
);
