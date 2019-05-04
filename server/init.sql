-- -----------------------------------------------------
-- Table Rights
-- -----------------------------------------------------
DROP TABLE IF EXISTS Rights;
CREATE TABLE Rights (
  id INT NOT NULL AUTO_INCREMENT,
  right_name VARCHAR(45) NOT NULL,
  description TEXT,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table Roles
-- -----------------------------------------------------
DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles (
  id INT NOT NULL AUTO_INCREMENT,
  role_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table Has_rights
-- -----------------------------------------------------
DROP TABLE IF EXISTS Has_rights;
CREATE TABLE Has_rights (
  id INT NOT NULL AUTO_INCREMENT,
  role_id INT NOT NULL,
  right_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_Has_rights_Roles1 (role_id ASC),
  INDEX fk_Has_rights_Rights1 (right_id ASC),
  CONSTRAINT fk_Has_rights_Roles1 FOREIGN KEY (role_id)
    REFERENCES Roles (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_Has_rights_Rights1 FOREIGN KEY (right_id)
    REFERENCES Rights (id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table Approval_status
-- -----------------------------------------------------
DROP TABLE IF EXISTS Approval_status;
CREATE TABLE Approval_status (
  id INT NOT NULL AUTO_INCREMENT,
  status_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table Users
-- -----------------------------------------------------
DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  no_vacations INT NOT NULL,
  no_sick_days INT NOT NULL,
  alert DATE,
  token TEXT UNIQUE NOT NULL,
  email VARCHAR(45) UNIQUE NOT NULL,
  photo TEXT,
  role_id INT NOT NULL,
  status_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_Users_Roles1 (role_id ASC),
  INDEX fk_Users_Approval_status1 (status_id ASC),
  CONSTRAINT fk_Users_Roles1 FOREIGN KEY (role_id)
    REFERENCES Roles (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_Users_Approval_status FOREIGN KEY (status_id)
    REFERENCES Approval_status (id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT check_no_vacation CHECK (no_vacations >= 0),
  CONSTRAINT check_no_sick_days CHECK (no_sick_days >= 0),
  CONSTRAINT check_email CHECK (email = '%_@__%.__%')
);

-- -----------------------------------------------------
-- Table Sick_Days
-- -----------------------------------------------------
DROP TABLE IF EXISTS Sick_days;
CREATE TABLE Sick_days (
  id INT NOT NULL AUTO_INCREMENT,
  sick_date DATE NOT NULL,
  user_id INT NOT NULL,
  status_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_Sick_days_Approval_status1 (status_id ASC),
  INDEX fk_Sick_days_Users1 (user_id ASC),
  CONSTRAINT  fk_Sick_days_Approval_status1 FOREIGN KEY (status_id)
    REFERENCES Approval_status (id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT fk_Sick_days_Users1 FOREIGN KEY (user_id)
    REFERENCES Users (id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table Vacation_days
-- -----------------------------------------------------
DROP TABLE IF EXISTS Vacation_days;
CREATE TABLE Vacation_days (
  id INT NOT NULL AUTO_INCREMENT,
  vacation_date DATE NOT NULL,
  time_from TIME,
  time_to TIME,
  user_id INT NOT NULL,
  status_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_Vacation_days_Approval_status1 (status_id ASC),
  INDEX fk_Vacation_days_Users1 (user_id ASC),
  CONSTRAINT fk_Vacation_days_Approval_status1 FOREIGN KEY (status_id)
    REFERENCES Approval_status (id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT fk_Vacation_days_Users1 FOREIGN KEY (user_id)
    REFERENCES Users (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT check_time CHECK (time_from < time_to)
);

-- -----------------------------------------------------
-- Table Default_Settings
-- -----------------------------------------------------
DROP TABLE IF EXISTS Default_settings;
CREATE TABLE Default_settings (
  id INT NOT NULL AUTO_INCREMENT,
  no_vacations INT NOT NULL,
  no_sick_days INT NOT NULL,
  alert DATE NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT check_no_vacation CHECK (no_vacations >= 0),
  CONSTRAINT check_no_sick_days CHECK (no_sick_days >= 0)
);

-- -----------------------------------------------------
-- Insert table Rights
-- -----------------------------------------------------
-- INSERT INTO Rights (right_name, description) VALUES ();

-- -----------------------------------------------------
-- Insert table Roles
-- -----------------------------------------------------
INSERT INTO Roles (role_name) VALUES ('employee');
INSERT INTO Roles (role_name) VALUES ('employer');

-- -----------------------------------------------------
-- Insert table Has_rights
-- -----------------------------------------------------
-- INSERT INTO Has_rights (role_id, right_id) VALUES ();

-- -----------------------------------------------------
-- Insert table Approval_status
-- -----------------------------------------------------
INSERT INTO Approval_status (status_name) VALUES ('accepted');
INSERT INTO Approval_status (status_name) VALUES ('pending');
INSERT INTO Approval_status (status_name) VALUES ('rejected');

-- -----------------------------------------------------
-- Insert table Users
-- -----------------------------------------------------
-- INSERT INTO Users (first_name, last_name, no_vacations, no_sick_days, token, email, role_id, status_id) VALUES ();

-- -----------------------------------------------------
-- Insert table Sick_days
-- -----------------------------------------------------
-- INSERT INTO Sick_days (sick_date, user_id, status_id) VALUES ();

-- -----------------------------------------------------
-- Insert table Vacation_days
-- -----------------------------------------------------
-- INSERT INTO Vacation_days (vacation_date, time_from, time_to, user_id, status_id) VALUES ();

-- -----------------------------------------------------
-- Insert table Default_Settings
-- -----------------------------------------------------
INSERT INTO Default_settings (no_vacations, no_sick_days, alert) VALUES (0, 5, '2019-12-01');