-- DROP DATABASE IF EXISTS oxatrade;

-- CREATE SCHEMA IF NOT EXISTS oxatrade
--	CHARACTER SET utf8mb4
--  COLLATE utf8mb4_general_ci;

-- NEWSLETTER-BLOCK:

-- oxatrade.newsletter definition
CREATE TABLE IF NOT EXISTS oxatrade.newsletter (
	id BINARY(16) NOT NULL,
  email varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT '0' COMMENT 'Email is verificated',
  last_update timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='List of emails for newsletter-mailing';

-- Triggers to automatically generate the UUID by "users"
 CREATE TRIGGER IF NOT EXISTS before_insert_newsletter
 BEFORE INSERT ON oxatrade.newsletter
 FOR EACH ROW
 BEGIN
     IF NEW.id IS NULL THEN
         SET NEW.id = (UUID_TO_BIN(UUID()));
     END IF;
 END;

-- oxatrade.newstheme definition
CREATE TABLE IF NOT EXISTS oxatrade.newstheme (
  id int unsigned NOT NULL AUTO_INCREMENT,
  theme_name varchar(25) NOT NULL COMMENT 'title',
  memo text COMMENT 'memo/information',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='List of themes spec. for newsletter-mailing';

-- Dumping data for table "oxatrade.countries"
INSERT INTO oxatrade.newstheme (theme_name, memo) VALUES
('DEFAULT', 'Auto created');

-- oxatrade.newsletter_newstheme definition
-- Create the Join Table to establish a many-to-many relationship
CREATE TABLE IF NOT EXISTS oxatrade.newsletter_newstheme (
  email_id BINARY(16) NOT NULL,
  theme_id int unsigned NOT NULL,
  PRIMARY KEY (email_id, theme_id)
  -- FOREIGN KEY (email_id) REFERENCES oxatrade.newsletter(id) ON UPDATE CASCADE ON DELETE CASCADE,
  -- FOREIGN KEY (theme_id) REFERENCES oxatrade.newstheme(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Many-to-Many relationship between newsletter and newstheme';
