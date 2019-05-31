-- -----------------------------------------------------
-- Table role
-- -----------------------------------------------------
DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id TINYINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(8) NOT NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table approval_status
-- -----------------------------------------------------
DROP TABLE IF EXISTS approval_status;
CREATE TABLE approval_status (
  id TINYINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(8) NOT NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table vacation_type
-- -----------------------------------------------------
DROP TABLE IF EXISTS vacation_type;
CREATE TABLE vacation_type (
  id TINYINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(8) NOT NULL,
  PRIMARY KEY (id)
);

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
  role_id TINYINT NOT NULL,
  status_id TINYINT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_end_user_role (role_id ASC),
  INDEX fk_end_user_approval_status (status_id ASC),
  CONSTRAINT fk_end_user_role FOREIGN KEY (role_id)
    REFERENCES role (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_end_user_approval_status FOREIGN KEY (status_id)
    REFERENCES approval_status (id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
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
  user_id BIGINT NOT NULL,
  status_id TINYINT NOT NULL,
  type_id TINYINT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_vacation_day_approval_status (status_id ASC),
  INDEX fk_vacation_day_end_user (user_id ASC),
  INDEX fk_vacation_day_vacation_type (type_id ASC),
  CONSTRAINT fk_vacation_day_approval_status FOREIGN KEY (status_id)
    REFERENCES approval_status (id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT fk_vacation_day_end_user FOREIGN KEY (user_id)
    REFERENCES end_user (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_vacation_day_vacation_type FOREIGN KEY (type_id)
    REFERENCES vacation_type (id)
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
  OUT out_email VARCHAR(100),
  OUT out_photo TEXT,
  OUT out_creation_date DATETIME,
  OUT out_role VARCHAR(8),
  OUT out_status VARCHAR(8))
BEGIN
  DECLARE sickDaysCount INT;
  DECLARE notification DATETIME;

  SELECT no_sick_days, alert INTO sickDaysCount, notification FROM default_settings ORDER BY id DESC LIMIT 1;
  SELECT u.id, u.first_name, u.last_name, u.no_vacations, IFNULL(u.no_sick_days, sickDaysCount), u.taken_sick_days, IFNULL(u.alert, notification), u.email, u.photo, u.creation_date, r.name, s.name
     INTO out_id, out_first_name, out_last_name, out_no_vacations, out_no_sick_days, out_taken_sick_days, out_alert, out_email, out_photo, out_creation_date, out_role, out_status
     FROM end_user u
     INNER JOIN role r ON u.role_id=r.id
     INNER JOIN approval_status s ON u.status_id=s.id
     WHERE u.id=in_id;
END $$

DROP PROCEDURE IF EXISTS GetUserToken $$
CREATE PROCEDURE GetUserToken(
  IN p_token TEXT,
  OUT out_id BIGINT,
  OUT out_first_name VARCHAR(45),
  OUT out_last_name VARCHAR(45),
  OUT out_no_vacations FLOAT,
  OUT out_no_sick_days INT,
  OUT out_taken_sick_days INT,
  OUT out_alert DATETIME,
  OUT out_email VARCHAR(100),
  OUT out_photo TEXT,
  OUT out_creation_date DATETIME,
  OUT out_role VARCHAR(8),
  OUT out_status VARCHAR(8))
BEGIN
  DECLARE sickDaysCount INT;
  DECLARE notification DATETIME;

  SELECT no_sick_days, alert INTO sickDaysCount, notification FROM default_settings ORDER BY id DESC LIMIT 1;
  SELECT u.id, u.first_name, u.last_name, u.no_vacations, IFNULL(u.no_sick_days, sickDaysCount), u.taken_sick_days, IFNULL(u.alert, notification), u.email, u.photo, u.creation_date, r.name, s.name
     INTO out_id, out_first_name, out_last_name, out_no_vacations, out_no_sick_days, out_taken_sick_days, out_alert, out_email, out_photo, out_creation_date, out_role, out_status
     FROM end_user u
     INNER JOIN role r ON u.role_id=r.id
     INNER JOIN approval_status s ON u.status_id=s.id
     WHERE u.token=p_token;
END $$

DELIMITER ;

-- -----------------------------------------------------
-- Insert table role
-- -----------------------------------------------------
INSERT INTO role (name) VALUES ('employee');
INSERT INTO role (name) VALUES ('employer');

-- -----------------------------------------------------
-- Insert table approval_status
-- -----------------------------------------------------
INSERT INTO approval_status (name) VALUES ('accepted');
INSERT INTO approval_status (name) VALUES ('pending');
INSERT INTO approval_status (name) VALUES ('rejected');

-- -----------------------------------------------------
-- Insert table vacation_type
-- -----------------------------------------------------
INSERT INTO vacation_type (name) VALUES ('sickday');
INSERT INTO vacation_type (name) VALUES ('vacation');

-- -----------------------------------------------------
-- Insert table end_user
-- -----------------------------------------------------
INSERT INTO end_user (first_name, last_name, no_vacations, no_sick_days, taken_sick_days, alert, token, email, photo, role_id, status_id) VALUES ('admin', 'admin', 0, NULL, 0, NULL, '', '', '', 2, 1);
INSERT INTO end_user (first_name, last_name, no_vacations, no_sick_days, taken_sick_days, alert, token, email, photo, role_id, status_id) VALUES ('test', 'test', 0, NULL, 0, NULL, '', '', '', 1, 2);

-- -----------------------------------------------------
-- Insert table vacation_day
-- -----------------------------------------------------
-- INSERT INTO vacation_day (vacation_date, time_from, time_to, user_id, status_id, type_id) VALUES ();

-- -----------------------------------------------------
-- Insert table default_settings
-- -----------------------------------------------------
INSERT INTO default_settings (no_sick_days, alert) VALUES (5, '2019-12-01 12:00:00.000');