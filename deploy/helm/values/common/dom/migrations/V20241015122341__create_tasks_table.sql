-- V1__create_tasks_table.sql
CREATE TABLE tasks (
    task_id SERIAL PRIMARY KEY,
    flow_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    package_received INT,
    scheduled_slot TIMESTAMP,
    team_id INT,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES teams(team_id)
);
