-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: librarydb
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `issuebook`
--

DROP TABLE IF EXISTS `issuebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issuebook` (
  `issueid` int NOT NULL AUTO_INCREMENT,
  `bookid` varchar(45) NOT NULL,
  `memberid` int NOT NULL,
  `issuedate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `renewcount` int NOT NULL DEFAULT '0',
  `duedate` timestamp NOT NULL,
  `returnedate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`issueid`),
  UNIQUE KEY `issueid_UNIQUE` (`issueid`),
  UNIQUE KEY `bookid_UNIQUE` (`bookid`),
  KEY `memberfk_idx` (`memberid`),
  CONSTRAINT `bookfk` FOREIGN KEY (`bookid`) REFERENCES `librarybook` (`bookid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `memberfk` FOREIGN KEY (`memberid`) REFERENCES `librarymember` (`memberid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issuebook`
--

LOCK TABLES `issuebook` WRITE;
/*!40000 ALTER TABLE `issuebook` DISABLE KEYS */;
INSERT INTO `issuebook` VALUES (20,'DBP/001',37141539,'2022-11-05 11:14:02',0,'2022-11-12 08:14:02','2022-11-05 14:12:53'),(22,'MKU/ICT001/2020',37141540,'2022-11-05 13:14:17',0,'2022-11-19 10:14:17','2022-11-09 09:03:02');
/*!40000 ALTER TABLE `issuebook` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-09 13:56:08
