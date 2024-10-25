ALTER TABLE recon
ADD CONSTRAINT fk_task_id
    FOREIGN KEY (task_id) REFERENCES tasks(task_id);

ALTER TABLE recon
ADD CONSTRAINT fk_team_id
    FOREIGN KEY (team_id) REFERENCES teams(team_id);
