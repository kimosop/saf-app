SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 department_name VARCHAR,
 hod VARCHAR
);

CREATE TABLE IF NOT EXISTS staff (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
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