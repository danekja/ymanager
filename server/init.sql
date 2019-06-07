SET NAMES utf8mb4;

-- -----------------------------------------------------
-- Table end_user
-- -----------------------------------------------------
DROP TABLE IF EXISTS end_user;
CREATE TABLE end_user (
  id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  no_vacations FLOAT NOT NULL,
  no_sick_days INT,
  taken_sick_days INT NOT NULL,
  alert DATETIME,
  token TEXT NOT NULL,
  email VARCHAR(100) NOT NULL,
  photo TEXT,
  creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_role VARCHAR(8) NOT NULL,
  status VARCHAR(8) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT check_taken_sick_days CHECK (taken_sick_days >= 0)
);

-- -----------------------------------------------------
-- Table vacation_day
-- -----------------------------------------------------
DROP TABLE IF EXISTS vacation_day;
CREATE TABLE vacation_day (
  id BIGINT NOT NULL AUTO_INCREMENT,
  vacation_date DATE NOT NULL,
  time_from TIME,
  time_to TIME,
  creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(8) NOT NULL,
  vacation_type VARCHAR(8) NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_vacation_day_end_user (user_id ASC),
  CONSTRAINT fk_vacation_day_end_user FOREIGN KEY (user_id)
    REFERENCES end_user (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT check_time CHECK (time_from < time_to)
);

-- -----------------------------------------------------
-- Table Default_Settings
-- -----------------------------------------------------
DROP TABLE IF EXISTS default_settings;
CREATE TABLE default_settings (
  id BIGINT NOT NULL AUTO_INCREMENT,
  no_sick_days INT NOT NULL,
  alert DATETIME NOT NULL,
  creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT check_no_sick_days CHECK (no_sick_days >= 0)
);

DELIMITER $$
DROP PROCEDURE IF EXISTS GetUserId $$
CREATE PROCEDURE GetUserId(
  IN in_id BIGINT,
  OUT out_id BIGINT,
  OUT out_first_name VARCHAR(45),
  OUT out_last_name VARCHAR(45),
  OUT out_no_vacations FLOAT,
  OUT out_no_sick_days INT,
  OUT out_taken_sick_days INT,
  OUT out_alert DATETIME,
  OUT out_token TEXT,
  OUT out_email VARCHAR(100),
  OUT out_photo TEXT,
  OUT out_creation_date DATETIME,
  OUT out_role VARCHAR(8),
  OUT out_status VARCHAR(8))
BEGIN
  DECLARE sickDaysCount INT;
  DECLARE notification DATETIME;

  SELECT no_sick_days, alert INTO sickDaysCount, notification FROM default_settings ORDER BY id DESC LIMIT 1;
  SELECT id, first_name, last_name, no_vacations, IFNULL(no_sick_days, sickDaysCount), taken_sick_days, IFNULL(alert, notification), token, email, photo, creation_date, user_role, status
     INTO out_id, out_first_name, out_last_name, out_no_vacations, out_no_sick_days, out_taken_sick_days, out_alert, out_token, out_email, out_photo, out_creation_date, out_role, out_status
     FROM end_user
     WHERE id=in_id;
END $$

DROP PROCEDURE IF EXISTS GetUserToken $$
CREATE PROCEDURE GetUserToken(
  IN in_token TEXT,
  OUT out_id BIGINT,
  OUT out_first_name VARCHAR(45),
  OUT out_last_name VARCHAR(45),
  OUT out_no_vacations FLOAT,
  OUT out_no_sick_days INT,
  OUT out_taken_sick_days INT,
  OUT out_alert DATETIME,
  OUT out_token TEXT,
  OUT out_email VARCHAR(100),
  OUT out_photo TEXT,
  OUT out_creation_date DATETIME,
  OUT out_role VARCHAR(8),
  OUT out_status VARCHAR(8))
BEGIN
  DECLARE sickDaysCount INT;
  DECLARE notification DATETIME;

  SELECT no_sick_days, alert INTO sickDaysCount, notification FROM default_settings ORDER BY id DESC LIMIT 1;
  SELECT id, first_name, last_name, no_vacations, IFNULL(no_sick_days, sickDaysCount), taken_sick_days, IFNULL(alert, notification), token, email, photo, creation_date, user_role, status
     INTO out_id, out_first_name, out_last_name, out_no_vacations, out_no_sick_days, out_taken_sick_days, out_alert, out_token, out_email, out_photo, out_creation_date, out_role, out_status
     FROM end_user
     WHERE token=in_token;
END $$

DELIMITER ;

-- -----------------------------------------------------
-- Insert table end_user
-- -----------------------------------------------------
INSERT INTO end_user (first_name, last_name, no_vacations, no_sick_days, taken_sick_days, alert, token, email, photo, user_role, status) VALUES ('admin', 'admin', 0, NULL, 0, NULL, '', '', 'https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg', 'EMPLOYER', 'ACCEPTED');

-- -----------------------------------------------------
-- Insert table vacation_day
-- -----------------------------------------------------
-- INSERT INTO vacation_day (vacation_date, time_from, time_to, user_id, status_id, type_id) VALUES ();

-- -----------------------------------------------------
-- Insert table default_settings
-- -----------------------------------------------------
INSERT INTO default_settings (no_sick_days, alert) VALUES (5, '2019-12-01 12:00:00.000');