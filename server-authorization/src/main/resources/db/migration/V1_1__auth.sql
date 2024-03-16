-- DROP DATABASE IF EXISTS oxatrade;

CREATE SCHEMA IF NOT EXISTS oxatrade;

-- Falls Funktion "uuid_generate_v4()" in PostgreSQL-Datenbank nicht verfügbar ist:
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- oxatrade.users definition
CREATE TABLE IF NOT EXISTS oxatrade.users (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  email varchar(64) NOT NULL,
  password varchar(2048) NOT NULL,
  phone varchar(25),
  provider varchar(25),
  first_name varchar(64) NOT NULL,
  last_name varchar(64),
  title varchar(32),
  enabled BOOLEAN NOT NULL DEFAULT FALSE,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add comment to 'provider' column
COMMENT ON COLUMN oxatrade.users.provider IS 'google or facebook, etc.';

-- oxatrade.roles definition
CREATE TABLE IF NOT EXISTS oxatrade.roles (
  id SERIAL PRIMARY KEY,
  name varchar(25) NOT NULL UNIQUE,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- oxatrade.users_roles definition
CREATE TABLE IF NOT EXISTS oxatrade.users_roles (
  user_id UUID REFERENCES oxatrade.users(id),
  role_id INT REFERENCES oxatrade.roles(id),
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, role_id)
);

-- Insert ROLES
INSERT INTO oxatrade.roles (name)
VALUES ('ADMIN'),('USER');

-- Create new admin and user with pass: "12345$aA"
INSERT INTO oxatrade.users(
	email,
	password,
	first_name,
	last_name,
	enabled)
VALUES
('admin@adminov.com', 'Ojv3Ym0Rg2atgLuVEhe3Ek9xVWp+zYQSvj51M7L66LA=', 'admin', 'adminov', true),
('user@userov.com', 'Ojv3Ym0Rg2atgLuVEhe3Ek9xVWp+zYQSvj51M7L66LA=', 'user', 'userov', true);

-- Insert USERS_ROLES
INSERT INTO oxatrade.users_roles (user_id, role_id)
VALUES
((SELECT id FROM oxatrade.users WHERE first_name = 'admin'), (SELECT id FROM oxatrade.roles WHERE name = 'ADMIN')),
((SELECT id FROM oxatrade.users WHERE first_name = 'user'), (SELECT id FROM oxatrade.roles WHERE name = 'USER'));
