-- DROP DATABASE IF EXISTS oxatrade;

-- CREATE SCHEMA IF NOT EXISTS oxatrade;

-- NEWSLETTER-BLOCK:

-- oxatrade.newsletter definition
CREATE TABLE IF NOT EXISTS oxatrade.newsletter (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email varchar(64) NOT NULL UNIQUE,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    last_update timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add comments
COMMENT ON COLUMN oxatrade.newsletter.enabled IS 'Email is verified';

-- oxatrade.newstheme definition
CREATE TABLE IF NOT EXISTS oxatrade.newstheme (
    id SERIAL PRIMARY KEY,
    theme_name varchar(25) NOT NULL,
    memo text
);

-- Add comments
COMMENT ON COLUMN oxatrade.newstheme.theme_name IS 'title';
COMMENT ON COLUMN oxatrade.newstheme.memo IS 'memo/information';

-- Dumping data for table "oxatrade.countries"
INSERT INTO oxatrade.newstheme (theme_name, memo) VALUES
('DEFAULT', 'Auto created');

-- oxatrade.newsletter_newstheme definition
-- Create the Join Table to establish a many-to-many relationship
CREATE TABLE IF NOT EXISTS oxatrade.newsletter_newstheme (
    email_id UUID REFERENCES oxatrade.newsletter(id),
    theme_id INT REFERENCES oxatrade.newstheme(id),
    PRIMARY KEY (email_id, theme_id)
);
