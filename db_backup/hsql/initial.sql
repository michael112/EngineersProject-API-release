CREATE TABLE attachementscourses (
  courseID varchar(36) NOT NULL,
  attachementID varchar(36) NOT NULL,
  PRIMARY KEY (courseID,attachementID)
)

CREATE TABLE attachementshomeworks (
  attachementID varchar(36) NOT NULL,
  homeworkID varchar(36) NOT NULL,
  PRIMARY KEY (attachementID,homeworkID)
)

CREATE TABLE attachementsmessages (
  fileID varchar(36) NOT NULL,
  messageID varchar(36) NOT NULL,
  PRIMARY KEY (fileID,messageID)
)

CREATE TABLE coursedays (
  courseDayID varchar(36) NOT NULL,
  courseID varchar(36) NOT NULL,
  day boolean NOT NULL,
  hourFrom varchar(5) NOT NULL,
  hourTo varchar(5) NOT NULL,
  PRIMARY KEY (courseDayID)
)

CREATE TABLE courselevels (
  name varchar(2) NOT NULL,
  PRIMARY KEY (name)
)

CREATE TABLE coursememberships (
  courseMembershipID varchar(36) NOT NULL,
  courseID varchar(36) NOT NULL,
  userID varchar(36) NOT NULL,
  courseIDMovedFrom varchar(36) DEFAULT NULL,
  active boolean NOT NULL,
  resignation boolean NOT NULL,
  PRIMARY KEY (courseMembershipID)
)

CREATE TABLE courses (
  courseID varchar(36) NOT NULL,
  languageID varchar(5) NOT NULL,
  courseLevelName varchar(2) NOT NULL,
  courseTypeID varchar(36) NOT NULL,
  maxStudents integer DEFAULT NULL,
  price double DEFAULT NULL,
  activityFrom datetime DEFAULT NULL,
  activityTo datetime DEFAULT NULL
)

CREATE TABLE coursetypenames (
  courseTypeNameID varchar(36) NOT NULL,
  courseTypeID varchar(36) NOT NULL,
  namingLanguageID varchar(5) NOT NULL,
  courseTypeName varchar(30) NOT NULL,
  PRIMARY KEY (courseTypeNameID)
);

CREATE TABLE coursetypes (
  courseTypeID varchar(36) NOT NULL,
  PRIMARY KEY (courseTypeID)
)

CREATE TABLE files (
  fileID varchar(36) NOT NULL,
  name varchar(50) NOT NULL,
  date datetime NOT NULL,
  path varchar(100) NOT NULL,
  senderID varchar(36) NOT NULL,
  PRIMARY KEY (fileID)
)

CREATE TABLE grades (
  gradeID varchar(36) NOT NULL,
  gradedByID varchar(36) NOT NULL,
  courseID varchar(36) NOT NULL,
  gradeTitle varchar(50) NOT NULL,
  gradeDescription varchar(50) DEFAULT NULL,
  taskID varchar(36) DEFAULT NULL,
  taskType boolean DEFAULT NULL,
  scale varchar(8) NOT NULL,
  maxPoints double DEFAULT NULL,
  weight double DEFAULT '1' NOT NULL,
  PRIMARY KEY (gradeID)
)

CREATE TABLE homeworks (
  taskID varchar(36) NOT NULL,
  title varchar(50) DEFAULT NULL,
  date datetime DEFAULT NULL,
  description varchar(100) DEFAULT NULL,
  courseID varchar(36) NOT NULL,
  PRIMARY KEY (taskID)
)

CREATE TABLE homeworksolutions (
  homeworkSolutionID varchar(36) NOT NULL,
  courseMembershipID varchar(36) NOT NULL,
  taskID varchar(36) NOT NULL,
  fileID varchar(36) NOT NULL,
  studentGradeID varchar(36) DEFAULT NULL,
  PRIMARY KEY (homeworkSolutionID)
)

CREATE TABLE languagenames (
  namedLanguageID varchar(5) NOT NULL,
  namingLanguageID varchar(5) NOT NULL,
  languageName varchar(20) NOT NULL,
  PRIMARY KEY (namedLanguageID,namingLanguageID)
)

CREATE TABLE languages (
  languageID varchar(5) NOT NULL,
  PRIMARY KEY (languageID)
)

CREATE TABLE messages (
  messageID varchar(36) NOT NULL,
  title varchar(50) NOT NULL,
  content varchar(300) NOT NULL,
  isAnnouncement boolean NOT NULL,
  courseID varchar(36) DEFAULT NULL,
  senderID varchar(36) NOT NULL,
  PRIMARY KEY (messageID)
)

CREATE TABLE messagesusers (
  messageID varchar(36) NOT NULL,
  userID varchar(36) NOT NULL,
  PRIMARY KEY (messageID,userID)
)

CREATE TABLE phones (
  phoneID varchar(36) NOT NULL,
  phoneType varchar(8) NOT NULL,
  phoneNumber varchar(20) NOT NULL,
  userID varchar(36) NOT NULL,
  PRIMARY KEY (phoneID)
)

CREATE TABLE placementanswers (
  placementAnswerID varchar(36) NOT NULL,
  answerKey varchar(1) NOT NULL,
  answerName varchar(30) NOT NULL,
  placementSentenceID varchar(36) NOT NULL,
  PRIMARY KEY (placementAnswerID)
)

CREATE TABLE placementsentences (
  placementSentenceID varchar(36) NOT NULL,
  prefix varchar(30) DEFAULT NULL,
  suffix varchar(30) DEFAULT NULL,
  correctAnswer varchar(1) NOT NULL,
  placementTaskID varchar(36) NOT NULL,
  PRIMARY KEY (placementSentenceID)
)

CREATE TABLE placementtasks (
  placementTaskID varchar(36) NOT NULL,
  command varchar(300) NOT NULL,
  placementTestID varchar(36) NOT NULL,
  PRIMARY KEY (placementTaskID)
)

CREATE TABLE placementtestresults (
  placementTestResultID varchar(36) NOT NULL,
  result double NOT NULL,
  testID varchar(36) NOT NULL,
  userID varchar(36) NOT NULL,
  PRIMARY KEY (placementTestResultID)
)

CREATE TABLE placementtests (
  placementTestID varchar(36) NOT NULL,
  languageID varchar(5) NOT NULL,
  PRIMARY KEY (placementTestID)
)

CREATE TABLE studentgrades (
  studentGradeID varchar(36) NOT NULL,
  studentID varchar(36) NOT NULL,
  gradeValue double NOT NULL,
  gradeID varchar(36) NOT NULL,
  PRIMARY KEY (studentGradeID)
)

CREATE TABLE teacherscourses (
  teacherID varchar(36) NOT NULL,
  courseID varchar(36) NOT NULL,
  PRIMARY KEY (teacherID,courseID)
)

CREATE TABLE teacherslanguages (
  teacherID varchar(36) NOT NULL,
  languageID varchar(5) NOT NULL,
  PRIMARY KEY (teacherID,languageID)
)

CREATE TABLE tests (
  taskID varchar(36) NOT NULL,
  title varchar(50) DEFAULT NULL,
  date datetime DEFAULT NULL,
  description varchar(100) DEFAULT NULL,
  courseID varchar(36) NOT NULL,
  PRIMARY KEY (taskID)
)

CREATE TABLE testsolutions (
  testSolutionID varchar(36) NOT NULL,
  courseMembershipID varchar(36) NOT NULL,
  taskID varchar(36) NOT NULL,
  written boolean NOT NULL,
  studentGradeID varchar(36) DEFAULT NULL,
  PRIMARY KEY (testSolutionID)
)

CREATE TABLE userroles (
  roleID varchar(36) NOT NULL,
  role varchar(30) NOT NULL,
  PRIMARY KEY (roleID)
--  PRIMARY KEY (roleID),
--  UNIQUE KEY role (role)
)

CREATE TABLE users (
  userID varchar(36) NOT NULL,
  username varchar(30) NOT NULL,
  password varchar(100) NOT NULL,
  firstname varchar(30) NOT NULL,
  lastname varchar(30) NOT NULL,
  email varchar(50) NOT NULL,
  active boolean NOT NULL,
  addressStreet varchar(50) NOT NULL,
  addressHouseNumber varchar(5) NOT NULL,
  addressFlatNumber varchar(5) DEFAULT NULL,
  addressPostCode varchar(6) DEFAULT NULL,
  addressCity varchar(30) NOT NULL,
  PRIMARY KEY (userID)
--  PRIMARY KEY (userID),
--  UNIQUE KEY username (username)
)

CREATE TABLE usersuserroles (
  userID varchar(36) NOT NULL,
  userRoleID varchar(36) NOT NULL,
  PRIMARY KEY (userID,userRoleID)
)

-- INSERT INTO userroles VALUES ('95e5acf0-4e56-11e6-a0dd-54ab3a01c2d0','ADMIN'),('56196e40-4e56-11e6-a0dd-54ab3a01c2d0','USER');
INSERT INTO userroles VALUES ('95e5acf0-4e56-11e6-a0dd-54ab3a01c2d0','ADMIN');
INSERT INTO userroles VALUES ('56196e40-4e56-11e6-a0dd-54ab3a01c2d0','USER');