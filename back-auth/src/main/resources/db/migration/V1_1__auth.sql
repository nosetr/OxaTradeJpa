-- DROP DATABASE IF EXISTS oxatrade;

-- CREATE SCHEMA IF NOT EXISTS oxatrade
-- 	CHARACTER SET utf8mb4
--  COLLATE utf8mb4_general_ci;

-- oxatrade.users definition
CREATE TABLE IF NOT EXISTS oxatrade.users (
  id BINARY(16) NOT NULL,
  email varchar(64) NOT NULL,
  password varchar(2048) NOT NULL,
  phone varchar(25) DEFAULT NULL,
  provider varchar(25) DEFAULT NULL COMMENT 'google or facebook, etc.',
  first_name varchar(64) NOT NULL,
  last_name varchar(64) DEFAULT NULL,
  title varchar(32) DEFAULT NULL,
  enabled BOOLEAN NOT NULL DEFAULT '0',
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY email (email)
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Table of users';

-- Triggers to automatically generate the UUID by "users"
 CREATE TRIGGER IF NOT EXISTS before_insert_users
 BEFORE INSERT ON oxatrade.users
 FOR EACH ROW
 BEGIN
     IF NEW.id IS NULL THEN
         SET NEW.id = (UUID_TO_BIN(UUID()));
     END IF;
 END;

-- oxatrade.roles definition
CREATE TABLE oxatrade.roles
(
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name    varchar(25) NOT NULL UNIQUE,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Many-to-many between users and roles';;

-- oxatrade.users_roles definition
CREATE TABLE oxatrade.users_roles
(
    user_id BINARY(16) REFERENCES users (id),
    role_id int REFERENCES roles (id),
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (user_id, role_id)
);

-- Insert ROLES
INSERT INTO roles (name)
VALUES ('ADMIN'),('USER');

-- Create new admin and user with pass: "12345$aA"
INSERT INTO oxatrade.users(
	email,
	password,
	first_name,
	last_name,
	enabled)
VALUES
("admin@adminov.com", "Ojv3Ym0Rg2atgLuVEhe3Ek9xVWp+zYQSvj51M7L66LA=", "admin", "adminov", true),
("user@userov.com", "Ojv3Ym0Rg2atgLuVEhe3Ek9xVWp+zYQSvj51M7L66LA=", "user", "userov", true);

-- Insert USERS_ROLES
INSERT INTO oxatrade.users_roles (user_id, role_id)
VALUES
((SELECT id FROM users WHERE first_name = 'admin'), (SELECT id FROM roles WHERE name = 'admin')),
((SELECT id FROM users WHERE first_name = 'user'), (SELECT id FROM roles WHERE name = 'user'));
