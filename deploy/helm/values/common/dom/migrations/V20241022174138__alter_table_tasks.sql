ALTER TABLE tasks
    DROP CONSTRAINT fk_team_id;

ALTER TABLE tasks
    RENAME COLUMN task_id TO id;

ALTER TABLE tasks
    ADD COLUMN task_id VARCHAR(10) UNIQUE NOT NULL;

ALTER TABLE tasks
    ALTER COLUMN team_id SET DATA TYPE VARCHAR(10);

ALTER TABLE tasks
    ADD CONSTRAINT status_check CHECK (status IN ('Pending', 'In Progress', 'Completed', 'Failed'));

ALTER TABLE tasks
    ADD CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES teams(team_id);