-- V2__create_teams_table.sql
CREATE TABLE teams (
    id SERIAL PRIMARY KEY,
    team_id VARCHAR(10) UNIQUE NOT NULL,
    team_name VARCHAR(100) NOT NULL,
    short_name VARCHAR(50),
    parent_team_id VARCHAR(10),
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);