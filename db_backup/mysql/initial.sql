-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: engineers_project
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attachementscourses`
--

DROP TABLE IF EXISTS `attachementscourses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachementscourses` (
  `courseID` varchar(36) NOT NULL,
  `attachementID` varchar(36) NOT NULL,
  PRIMARY KEY (`courseID`,`attachementID`),
  KEY `FK__ATTACHEMENTS_COURSES__FILES` (`attachementID`),
  CONSTRAINT `FK__ATTACHEMENTS_COURSES__CRS` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`),
  CONSTRAINT `FK__ATTACHEMENTS_COURSES__FILES` FOREIGN KEY (`attachementID`) REFERENCES `files` (`fileID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachementscourses`
--

LOCK TABLES `attachementscourses` WRITE;
/*!40000 ALTER TABLE `attachementscourses` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachementscourses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachementshomeworks`
--

DROP TABLE IF EXISTS `attachementshomeworks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachementshomeworks` (
  `attachementID` varchar(36) NOT NULL,
  `homeworkID` varchar(36) NOT NULL,
  PRIMARY KEY (`attachementID`,`homeworkID`),
  KEY `FK_ATT_HWRK__HWRK` (`homeworkID`),
  CONSTRAINT `FK_ATT_HWRK__FILE` FOREIGN KEY (`attachementID`) REFERENCES `files` (`fileID`),
  CONSTRAINT `FK_ATT_HWRK__HWRK` FOREIGN KEY (`homeworkID`) REFERENCES `homeworks` (`taskID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachementshomeworks`
--

LOCK TABLES `attachementshomeworks` WRITE;
/*!40000 ALTER TABLE `attachementshomeworks` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachementshomeworks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachementsmessages`
--

DROP TABLE IF EXISTS `attachementsmessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachementsmessages` (
  `fileID` varchar(36) NOT NULL,
  `messageID` varchar(36) NOT NULL,
  PRIMARY KEY (`fileID`,`messageID`),
  KEY `FK__ATTACHEMENTS_MESSAGES__MESSAGES` (`messageID`),
  CONSTRAINT `FK__ATTACHEMENTS_MESSAGES__FILES` FOREIGN KEY (`fileID`) REFERENCES `files` (`fileID`),
  CONSTRAINT `FK__ATTACHEMENTS_MESSAGES__MESSAGES` FOREIGN KEY (`messageID`) REFERENCES `messages` (`messageID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachementsmessages`
--

LOCK TABLES `attachementsmessages` WRITE;
/*!40000 ALTER TABLE `attachementsmessages` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachementsmessages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursedays`
--

DROP TABLE IF EXISTS `coursedays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coursedays` (
  `courseDayID` varchar(36) NOT NULL,
  `courseID` varchar(36) NOT NULL,
  `day` tinyint(1) NOT NULL,
  `hourFrom` varchar(5) NOT NULL,
  `hourTo` varchar(5) NOT NULL,
  PRIMARY KEY (`courseDayID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursedays`
--

LOCK TABLES `coursedays` WRITE;
/*!40000 ALTER TABLE `coursedays` DISABLE KEYS */;
/*!40000 ALTER TABLE `coursedays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courselevels`
--

DROP TABLE IF EXISTS `courselevels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courselevels` (
  `name` varchar(2) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courselevels`
--

LOCK TABLES `courselevels` WRITE;
/*!40000 ALTER TABLE `courselevels` DISABLE KEYS */;
/*!40000 ALTER TABLE `courselevels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursememberships`
--

DROP TABLE IF EXISTS `coursememberships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coursememberships` (
  `courseMembershipID` varchar(36) NOT NULL,
  `courseID` varchar(36) NOT NULL,
  `userID` varchar(36) NOT NULL,
  `courseIDMovedFrom` varchar(36) DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  `resignation` tinyint(1) NOT NULL,
  PRIMARY KEY (`courseMembershipID`),
  KEY `FK__CRS_MSHP__CRS` (`courseID`),
  KEY `FK__CRS_MSHP__USR` (`userID`),
  KEY `FK__CRS_MSHP__MVD` (`courseIDMovedFrom`),
  CONSTRAINT `FK__CRS_MSHP__CRS` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`),
  CONSTRAINT `FK__CRS_MSHP__MVD` FOREIGN KEY (`courseIDMovedFrom`) REFERENCES `courses` (`courseID`),
  CONSTRAINT `FK__CRS_MSHP__USR` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursememberships`
--

LOCK TABLES `coursememberships` WRITE;
/*!40000 ALTER TABLE `coursememberships` DISABLE KEYS */;
/*!40000 ALTER TABLE `coursememberships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `courseID` varchar(36) NOT NULL,
  `languageID` varchar(5) NOT NULL,
  `courseLevelName` varchar(2) NOT NULL,
  `courseTypeID` varchar(36) NOT NULL,
  `maxStudents` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `activityFrom` datetime DEFAULT NULL,
  `activityTo` datetime DEFAULT NULL,
  PRIMARY KEY (`courseID`),
  KEY `FK_CRS_LNG` (`languageID`),
  KEY `FK__CRS__CRS_LEV` (`courseLevelName`),
  KEY `FK__CRS__CRS_TYPE` (`courseTypeID`),
  CONSTRAINT `FK_CRS_LNG` FOREIGN KEY (`languageID`) REFERENCES `languages` (`languageID`),
  CONSTRAINT `FK__CRS__CRS_LEV` FOREIGN KEY (`courseLevelName`) REFERENCES `courselevels` (`name`),
  CONSTRAINT `FK__CRS__CRS_TYPE` FOREIGN KEY (`courseTypeID`) REFERENCES `coursetypes` (`courseTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursetypenames`
--

DROP TABLE IF EXISTS `coursetypenames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coursetypenames` (
  `courseTypeNameID` varchar(36) NOT NULL,
  `courseTypeID` varchar(36) NOT NULL,
  `namingLanguageID` varchar(5) NOT NULL,
  `courseTypeName` varchar(30) NOT NULL,
  PRIMARY KEY (`courseTypeNameID`),
  KEY `FK__CRS_TP_NM__CSR_TP` (`courseTypeID`),
  KEY `FK__CRS_TP__NM_LNG` (`namingLanguageID`),
  CONSTRAINT `FK__CRS_TP_NM__CSR_TP` FOREIGN KEY (`courseTypeID`) REFERENCES `coursetypes` (`courseTypeID`),
  CONSTRAINT `FK__CRS_TP__NM_LNG` FOREIGN KEY (`namingLanguageID`) REFERENCES `languages` (`languageID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursetypenames`
--

LOCK TABLES `coursetypenames` WRITE;
/*!40000 ALTER TABLE `coursetypenames` DISABLE KEYS */;
/*!40000 ALTER TABLE `coursetypenames` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursetypes`
--

DROP TABLE IF EXISTS `coursetypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coursetypes` (
  `courseTypeID` varchar(36) NOT NULL,
  PRIMARY KEY (`courseTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursetypes`
--

LOCK TABLES `coursetypes` WRITE;
/*!40000 ALTER TABLE `coursetypes` DISABLE KEYS */;
/*!40000 ALTER TABLE `coursetypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `files` (
  `fileID` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `date` datetime NOT NULL,
  `path` varchar(100) NOT NULL,
  `senderID` varchar(36) NOT NULL,
  PRIMARY KEY (`fileID`),
  KEY `FK_FILES_USERS` (`senderID`),
  CONSTRAINT `FK_FILES_USERS` FOREIGN KEY (`senderID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grades` (
  `gradeID` varchar(36) NOT NULL,
  `gradedByID` varchar(36) NOT NULL,
  `courseID` varchar(36) NOT NULL,
  `gradeTitle` varchar(50) NOT NULL,
  `gradeDescription` varchar(50) DEFAULT NULL,
  `taskID` varchar(36) DEFAULT NULL,
  `taskType` tinyint(1) DEFAULT NULL,
  `scale` varchar(8) NOT NULL,
  `maxPoints` double DEFAULT NULL,
  `weight` double NOT NULL DEFAULT '1',
  PRIMARY KEY (`gradeID`),
  KEY `FK_GRD_USER` (`gradedByID`),
  KEY `FK_GRD_CRS` (`courseID`),
  CONSTRAINT `FK_GRD_CRS` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`),
  CONSTRAINT `FK_GRD_USER` FOREIGN KEY (`gradedByID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homeworks`
--

DROP TABLE IF EXISTS `homeworks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homeworks` (
  `taskID` varchar(36) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `courseID` varchar(36) NOT NULL,
  PRIMARY KEY (`taskID`),
  KEY `FK_HWR_CRS` (`courseID`),
  CONSTRAINT `FK_HWR_CRS` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homeworks`
--

LOCK TABLES `homeworks` WRITE;
/*!40000 ALTER TABLE `homeworks` DISABLE KEYS */;
/*!40000 ALTER TABLE `homeworks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homeworksolutions`
--

DROP TABLE IF EXISTS `homeworksolutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homeworksolutions` (
  `homeworkSolutionID` varchar(36) NOT NULL,
  `courseMembershipID` varchar(36) NOT NULL,
  `taskID` varchar(36) NOT NULL,
  `fileID` varchar(36) NOT NULL,
  `studentGradeID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`homeworkSolutionID`),
  KEY `FK__HWR_SOL__CRS` (`courseMembershipID`),
  KEY `FK__HWR_SOL__HWR` (`taskID`),
  KEY `FK__HWR_SOL__FILE` (`fileID`),
  CONSTRAINT `FK__HWR_SOL__CRS` FOREIGN KEY (`courseMembershipID`) REFERENCES `coursememberships` (`courseMembershipID`),
  CONSTRAINT `FK__HWR_SOL__FILE` FOREIGN KEY (`fileID`) REFERENCES `files` (`fileID`),
  CONSTRAINT `FK__HWR_SOL__HWR` FOREIGN KEY (`taskID`) REFERENCES `homeworks` (`taskID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homeworksolutions`
--

LOCK TABLES `homeworksolutions` WRITE;
/*!40000 ALTER TABLE `homeworksolutions` DISABLE KEYS */;
/*!40000 ALTER TABLE `homeworksolutions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languagenames`
--

DROP TABLE IF EXISTS `languagenames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languagenames` (
  `namedLanguageID` varchar(5) NOT NULL,
  `namingLanguageID` varchar(5) NOT NULL,
  `languageName` varchar(20) NOT NULL,
  PRIMARY KEY (`namedLanguageID`,`namingLanguageID`),
  KEY `FK__NM_LNG__LNG` (`namingLanguageID`),
  CONSTRAINT `FK__ND_LNG__LNG` FOREIGN KEY (`namedLanguageID`) REFERENCES `languages` (`languageID`),
  CONSTRAINT `FK__NM_LNG__LNG` FOREIGN KEY (`namingLanguageID`) REFERENCES `languages` (`languageID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languagenames`
--

LOCK TABLES `languagenames` WRITE;
/*!40000 ALTER TABLE `languagenames` DISABLE KEYS */;
/*!40000 ALTER TABLE `languagenames` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languages` (
  `languageID` varchar(5) NOT NULL,
  PRIMARY KEY (`languageID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `messageID` varchar(36) NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` varchar(300) NOT NULL,
  `isAnnouncement` tinyint(1) NOT NULL,
  `courseID` varchar(36) DEFAULT NULL,
  `senderID` varchar(36) NOT NULL,
  PRIMARY KEY (`messageID`),
  KEY `FK_MSG_CRS` (`courseID`),
  CONSTRAINT `FK_MSG_CRS` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagesusers`
--

DROP TABLE IF EXISTS `messagesusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messagesusers` (
  `messageID` varchar(36) NOT NULL,
  `userID` varchar(36) NOT NULL,
  PRIMARY KEY (`messageID`,`userID`),
  KEY `FK__MESSAGES_USERS__USERS` (`userID`),
  CONSTRAINT `FK__MESSAGES_USERS__MESSAGES` FOREIGN KEY (`messageID`) REFERENCES `messages` (`messageID`),
  CONSTRAINT `FK__MESSAGES_USERS__USERS` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messagesusers`
--

LOCK TABLES `messagesusers` WRITE;
/*!40000 ALTER TABLE `messagesusers` DISABLE KEYS */;
/*!40000 ALTER TABLE `messagesusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phones`
--

DROP TABLE IF EXISTS `phones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phones` (
  `phoneID` varchar(36) NOT NULL,
  `phoneType` varchar(8) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `userID` varchar(36) NOT NULL,
  PRIMARY KEY (`phoneID`),
  KEY `FK_PHONE_USER` (`userID`),
  CONSTRAINT `FK_PHONE_USER` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phones`
--

LOCK TABLES `phones` WRITE;
/*!40000 ALTER TABLE `phones` DISABLE KEYS */;
/*!40000 ALTER TABLE `phones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `placementanswers`
--

DROP TABLE IF EXISTS `placementanswers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `placementanswers` (
  `placementAnswerID` varchar(36) NOT NULL,
  `answerKey` varchar(1) NOT NULL,
  `answerName` varchar(30) NOT NULL,
  `placementSentenceID` varchar(36) NOT NULL,
  PRIMARY KEY (`placementAnswerID`),
  KEY `FK__PL_ANS__PL_SNT` (`placementSentenceID`),
  CONSTRAINT `FK__PL_ANS__PL_SNT` FOREIGN KEY (`placementSentenceID`) REFERENCES `placementsentences` (`placementSentenceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `placementanswers`
--

LOCK TABLES `placementanswers` WRITE;
/*!40000 ALTER TABLE `placementanswers` DISABLE KEYS */;
/*!40000 ALTER TABLE `placementanswers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `placementsentences`
--

DROP TABLE IF EXISTS `placementsentences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `placementsentences` (
  `placementSentenceID` varchar(36) NOT NULL,
  `prefix` varchar(30) DEFAULT NULL,
  `suffix` varchar(30) DEFAULT NULL,
  `correctAnswer` varchar(1) NOT NULL,
  `placementTaskID` varchar(36) NOT NULL,
  PRIMARY KEY (`placementSentenceID`),
  KEY `FK__PL_SNT__PL_TASK` (`placementTaskID`),
  CONSTRAINT `FK__PL_SNT__PL_TASK` FOREIGN KEY (`placementTaskID`) REFERENCES `placementtasks` (`placementTaskID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `placementsentences`
--

LOCK TABLES `placementsentences` WRITE;
/*!40000 ALTER TABLE `placementsentences` DISABLE KEYS */;
/*!40000 ALTER TABLE `placementsentences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `placementtasks`
--

DROP TABLE IF EXISTS `placementtasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `placementtasks` (
  `placementTaskID` varchar(36) NOT NULL,
  `command` varchar(300) NOT NULL,
  `placementTestID` varchar(36) NOT NULL,
  PRIMARY KEY (`placementTaskID`),
  KEY `FK__PL_TASK__PL_TEST` (`placementTestID`),
  CONSTRAINT `FK__PL_TASK__PL_TEST` FOREIGN KEY (`placementTestID`) REFERENCES `placementtests` (`placementTestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `placementtasks`
--

LOCK TABLES `placementtasks` WRITE;
/*!40000 ALTER TABLE `placementtasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `placementtasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `placementtestresults`
--

DROP TABLE IF EXISTS `placementtestresults`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `placementtestresults` (
  `placementTestResultID` varchar(36) NOT NULL,
  `result` double NOT NULL,
  `testID` varchar(36) NOT NULL,
  `userID` varchar(36) NOT NULL,
  PRIMARY KEY (`placementTestResultID`),
  KEY `FK__PL_TEST_RES__USER` (`userID`),
  KEY `FK__PL_TEST_RES__TEST` (`testID`),
  CONSTRAINT `FK__PL_TEST_RES__TEST` FOREIGN KEY (`testID`) REFERENCES `placementtests` (`placementTestID`),
  CONSTRAINT `FK__PL_TEST_RES__USER` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `placementtestresults`
--

LOCK TABLES `placementtestresults` WRITE;
/*!40000 ALTER TABLE `placementtestresults` DISABLE KEYS */;
/*!40000 ALTER TABLE `placementtestresults` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `placementtests`
--

DROP TABLE IF EXISTS `placementtests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `placementtests` (
  `placementTestID` varchar(36) NOT NULL,
  `languageID` varchar(5) NOT NULL,
  PRIMARY KEY (`placementTestID`),
  KEY `FK__PL_TEST__LNG` (`languageID`),
  CONSTRAINT `FK__PL_TEST__LNG` FOREIGN KEY (`languageID`) REFERENCES `languages` (`languageID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `placementtests`
--

LOCK TABLES `placementtests` WRITE;
/*!40000 ALTER TABLE `placementtests` DISABLE KEYS */;
/*!40000 ALTER TABLE `placementtests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentgrades`
--

DROP TABLE IF EXISTS `studentgrades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentgrades` (
  `studentGradeID` varchar(36) NOT NULL,
  `studentID` varchar(36) NOT NULL,
  `gradeValue` double NOT NULL,
  `gradeID` varchar(36) NOT NULL,
  PRIMARY KEY (`studentGradeID`),
  KEY `FK__ST_GRD__ST` (`studentID`),
  KEY `FK__ST_GRD__GRD` (`gradeID`),
  CONSTRAINT `FK__ST_GRD__GRD` FOREIGN KEY (`gradeID`) REFERENCES `grades` (`gradeID`),
  CONSTRAINT `FK__ST_GRD__ST` FOREIGN KEY (`studentID`) REFERENCES `coursememberships` (`courseMembershipID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentgrades`
--

LOCK TABLES `studentgrades` WRITE;
/*!40000 ALTER TABLE `studentgrades` DISABLE KEYS */;
/*!40000 ALTER TABLE `studentgrades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacherscourses`
--

DROP TABLE IF EXISTS `teacherscourses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacherscourses` (
  `teacherID` varchar(36) NOT NULL,
  `courseID` varchar(36) NOT NULL,
  PRIMARY KEY (`teacherID`,`courseID`),
  KEY `FK__TEACHERS_COURSES__CRS` (`courseID`),
  CONSTRAINT `FK__TEACHERS_COURSES__CRS` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`),
  CONSTRAINT `FK__TEACHERS_COURSES__USERS` FOREIGN KEY (`teacherID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacherscourses`
--

LOCK TABLES `teacherscourses` WRITE;
/*!40000 ALTER TABLE `teacherscourses` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacherscourses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacherslanguages`
--

DROP TABLE IF EXISTS `teacherslanguages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacherslanguages` (
  `teacherID` varchar(36) NOT NULL,
  `languageID` varchar(5) NOT NULL,
  PRIMARY KEY (`teacherID`,`languageID`),
  KEY `FK__TEACHERS_LANGUAGES__LANGUAGES` (`languageID`),
  CONSTRAINT `FK__TEACHERS_LANGUAGES__LANGUAGES` FOREIGN KEY (`languageID`) REFERENCES `languages` (`languageID`),
  CONSTRAINT `FK__TEACHERS_LANGUAGES__USERS` FOREIGN KEY (`teacherID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacherslanguages`
--

LOCK TABLES `teacherslanguages` WRITE;
/*!40000 ALTER TABLE `teacherslanguages` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacherslanguages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tests`
--

DROP TABLE IF EXISTS `tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tests` (
  `taskID` varchar(36) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `courseID` varchar(36) NOT NULL,
  PRIMARY KEY (`taskID`),
  KEY `FK_TST_CRS` (`courseID`),
  CONSTRAINT `FK_TST_CRS` FOREIGN KEY (`courseID`) REFERENCES `courses` (`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tests`
--

LOCK TABLES `tests` WRITE;
/*!40000 ALTER TABLE `tests` DISABLE KEYS */;
/*!40000 ALTER TABLE `tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testsolutions`
--

DROP TABLE IF EXISTS `testsolutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testsolutions` (
  `testSolutionID` varchar(36) NOT NULL,
  `courseMembershipID` varchar(36) NOT NULL,
  `taskID` varchar(36) NOT NULL,
  `written` tinyint(1) NOT NULL,
  `studentGradeID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`testSolutionID`),
  KEY `FK__TST_SOL__STD_GRD` (`studentGradeID`),
  KEY `FK__TST_SOL__TST` (`taskID`),
  KEY `FK__TST_SOL__CRS` (`courseMembershipID`),
  CONSTRAINT `FK__TST_SOL__CRS` FOREIGN KEY (`courseMembershipID`) REFERENCES `coursememberships` (`courseMembershipID`),
  CONSTRAINT `FK__TST_SOL__STD_GRD` FOREIGN KEY (`studentGradeID`) REFERENCES `studentgrades` (`studentGradeID`),
  CONSTRAINT `FK__TST_SOL__TST` FOREIGN KEY (`taskID`) REFERENCES `tests` (`taskID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testsolutions`
--

LOCK TABLES `testsolutions` WRITE;
/*!40000 ALTER TABLE `testsolutions` DISABLE KEYS */;
/*!40000 ALTER TABLE `testsolutions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userroles` (
  `roleID` varchar(36) NOT NULL,
  `role` varchar(30) NOT NULL,
  PRIMARY KEY (`roleID`),
  UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES ('95e5acf0-4e56-11e6-a0dd-54ab3a01c2d0','ADMIN'),('56196e40-4e56-11e6-a0dd-54ab3a01c2d0','USER');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userID` varchar(36) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `addressStreet` varchar(50) NOT NULL,
  `addressHouseNumber` varchar(5) NOT NULL,
  `addressFlatNumber` varchar(5) DEFAULT NULL,
  `addressPostCode` varchar(6) DEFAULT NULL,
  `addressCity` varchar(30) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersuserroles`
--

DROP TABLE IF EXISTS `usersuserroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usersuserroles` (
  `userID` varchar(36) NOT NULL,
  `userRoleID` varchar(36) NOT NULL,
  PRIMARY KEY (`userID`,`userRoleID`),
  KEY `FK_UUR_USER_ROLE` (`userRoleID`),
  CONSTRAINT `FK_UUR_USER` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  CONSTRAINT `FK_UUR_USER_ROLE` FOREIGN KEY (`userRoleID`) REFERENCES `userroles` (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersuserroles`
--

LOCK TABLES `usersuserroles` WRITE;
/*!40000 ALTER TABLE `usersuserroles` DISABLE KEYS */;
/*!40000 ALTER TABLE `usersuserroles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-27 23:49:38