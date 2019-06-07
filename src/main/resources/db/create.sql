SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS Departments (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 hod VARCHAR,
 email VARCHAR
);

CREATE TABLE IF NOT EXISTS Staff (
 id int PRIMARY KEY auto_increment,
 name VARCHAR
 department VARCHAR
);

CREATE TABLE IF NOT EXISTS News (
 id int PRIMARY KEY auto_increment,
 writtenby VARCHAR,
 content VARCHAR,
 rating VARCHAR,
 departmentid INTEGER
);

CREATE TABLE IF NOT EXISTS departments_staff (
 id int PRIMARY KEY auto_increment,
 staffid INTEGER,
 departmentid INTEGER
);

CREATE TABLE IF NOT EXISTS departments_news (
 id int PRIMARY KEY auto_increment,
 newsid INTEGER,
 departmentid INTEGER
);