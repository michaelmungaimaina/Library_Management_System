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
-- Table structure for table `librarybook`
--

DROP TABLE IF EXISTS `librarybook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `librarybook` (
  `bookid` varchar(45) NOT NULL,
  `booktitle` varchar(45) NOT NULL,
  `authorfirstname` varchar(45) NOT NULL,
  `authorlastname` varchar(45) NOT NULL,
  `bookpublisher` varchar(45) NOT NULL,
  `yearofpublication` year NOT NULL,
  `numberofpages` int NOT NULL,
  `bookcategory` varchar(100) NOT NULL,
  `bookstatus` enum('Available','Borrowed') NOT NULL DEFAULT 'Available',
  PRIMARY KEY (`bookid`),
  UNIQUE KEY `bookid_UNIQUE` (`bookid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarybook`
--

LOCK TABLES `librarybook` WRITE;
/*!40000 ALTER TABLE `librarybook` DISABLE KEYS */;
INSERT INTO `librarybook` VALUES ('DBP/001','Java in Two Semesters','Christine','Michelle','ABC',0000,1234,'ICT','Borrowed'),('MKU/ICT001/2020','Java in Two Semesters','Christin','Michelle','ABC',2000,1234,'ICT','Available');
/*!40000 ALTER TABLE `librarybook` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-09 13:56:07
