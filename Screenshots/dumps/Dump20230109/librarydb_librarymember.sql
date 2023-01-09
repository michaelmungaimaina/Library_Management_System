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
-- Table structure for table `librarymember`
--

DROP TABLE IF EXISTS `librarymember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `librarymember` (
  `memberid` int NOT NULL,
  `memberfirstname` varchar(45) NOT NULL,
  `memberlastname` varchar(45) NOT NULL,
  `membergender` enum('Male','Female') NOT NULL,
  `memberemail` varchar(45) NOT NULL,
  `memberphone` varchar(45) NOT NULL,
  `memberdepartment` varchar(100) NOT NULL,
  `registrationstatus` enum('Registered','Unregistered','True','False') DEFAULT 'Unregistered',
  PRIMARY KEY (`memberid`),
  UNIQUE KEY `memberegnumber_UNIQUE` (`memberid`),
  UNIQUE KEY `memberphone_UNIQUE` (`memberphone`),
  UNIQUE KEY `memberemail_UNIQUE` (`memberemail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarymember`
--

LOCK TABLES `librarymember` WRITE;
/*!40000 ALTER TABLE `librarymember` DISABLE KEYS */;
INSERT INTO `librarymember` VALUES (367833,'MAINA','MICHAEL','Male','mdndhd@gmail.com','0787653425','PURE AND APPLIED SCIENCES','Unregistered'),(37141539,'John','Maina','Male','mejathuo@gmail.com','0716977984','ICT','Unregistered'),(37141540,'Michael','Maina','Male','mainamichael@gmail.com','0748370999','Business','Registered');
/*!40000 ALTER TABLE `librarymember` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-09 13:56:06
