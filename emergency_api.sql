-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: emergency_api
-- ------------------------------------------------------
-- Server version	8.0.22-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int DEFAULT NULL,
  `team_id` int DEFAULT NULL,
  `firstname` tinytext COLLATE utf8mb4_unicode_ci,
  `lastname` tinytext COLLATE utf8mb4_unicode_ci,
  `street` tinytext COLLATE utf8mb4_unicode_ci,
  `zipcode` tinytext COLLATE utf8mb4_unicode_ci,
  `phone_number` tinytext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `IDX_268B9C9DBE04EA9` (`job_id`),
  KEY `IDX_268B9C9D296CD8AE` (`team_id`),
  CONSTRAINT `FK_268B9C9D296CD8AE` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FK_268B9C9DBE04EA9` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent`
--

LOCK TABLES `agent` WRITE;
/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
INSERT INTO `agent` VALUES (3,2,4,'Paul','Berson','36 rue du fournil de Berson','01700','+33625984587'),(4,2,5,'Patrick','Castillo','36 rue du fournil de Berson','01700','+33625984587'),(5,3,4,'Daryl','Paul','36 rue du fournil de Berson','01700','+33625984587'),(6,3,5,'Paul','Daryl','36 rue du fournil de Berson','01700','+33625984587'),(7,3,6,'Daryl','Jacques','36 rue du fournil de Berson','01700','+33625984587'),(8,3,6,'Lacrise','Omik','36 rue du fournil de Berson','01700','+33625984587'),(9,3,6,'Michel','MichelMichel','36 rue du fournil de Berson','01700','+33625984587'),(10,3,6,'Jacqueline','Lefebvre','36 rue du fournil de Berson','01700','+33625984587'),(11,4,4,'Tutar','Daniel','36 rue du fournil de Berson','01700','+33625984587'),(12,4,4,'Dominique','Rouille','36 rue du fournil de Berson','01700','+33625984587'),(13,4,4,'Aude','Joubert','36 rue du fournil de Berson','01700','+33625984587'),(14,4,4,'Vincent','Vidal','36 rue du fournil de Berson','01700','+33625984587'),(15,4,5,'Bash','Bel','36 rue du fournil de Berson','01700','+33625984587'),(16,4,5,'Flocon','D\'automne','36 rue du fournil de Berson','01700','+33625984587'),(17,4,5,'Ludovic','Maindron','36 rue du fournil de Berson','01700','+33625984587'),(18,4,5,'Zyed','Sayari','36 rue du fournil de Berson','01700','+33625984587'),(19,4,5,'Baron','Chepasonprenom','36 rue du fournil de Berson','01700','+33625984587'),(20,4,6,'Anthony','Buisson','36 rue du fournil de Berson','01700','+33625984587'),(21,4,6,'Xavier','Meruem','36 rue du fournil de Berson','01700','+33625984587'),(22,4,6,'Declaration','D\'amitie','36 rue du fournil de Berson','01700','+33625984587'),(23,4,6,'Laetitia','Monidole','36 rue du fournil de Berson','01700','+33625984587'),(24,4,6,'Mary','Annick','36 rue du fournil de Berson','01700','+33625984587'),(25,4,NULL,'Monique','Dupont','36 rue du fournil de Berson','01700','+33625984587'),(26,4,NULL,'Eglantine','Moretti','36 rue du fournil de Berson','01700','+33625984587'),(27,4,NULL,'Mongue','Pfe','36 rue du fournil de Berson','01700','+33625984587');
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_vehicle`
--

DROP TABLE IF EXISTS `category_vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_vehicle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `capacity` int NOT NULL,
  `name` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `coefficient` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_vehicle`
--

LOCK TABLES `category_vehicle` WRITE;
/*!40000 ALTER TABLE `category_vehicle` DISABLE KEYS */;
INSERT INTO `category_vehicle` VALUES (2,8,'Grande Echelle',8),(3,4,'Petite Echelle + Lance',6),(4,6,'Camion',4);
/*!40000 ALTER TABLE `category_vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `country` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Lyon','FRA'),(2,'Lyon','FRA');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctrine_migration_versions`
--

DROP TABLE IF EXISTS `doctrine_migration_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctrine_migration_versions` (
  `version` varchar(191) COLLATE utf8_unicode_ci NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int DEFAULT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctrine_migration_versions`
--

LOCK TABLES `doctrine_migration_versions` WRITE;
/*!40000 ALTER TABLE `doctrine_migration_versions` DISABLE KEYS */;
INSERT INTO `doctrine_migration_versions` VALUES ('DoctrineMigrations\\Version20210106162437','2021-01-06 17:24:45',10443),('DoctrineMigrations\\Version20210106164552','2021-01-06 17:46:04',2294),('DoctrineMigrations\\Version20210107131234','2021-01-07 14:12:40',9139);
/*!40000 ALTER TABLE `doctrine_migration_versions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incident`
--

DROP TABLE IF EXISTS `incident`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incident` (
  `id` int NOT NULL AUTO_INCREMENT,
  `location_id` int DEFAULT NULL,
  `intervention_id` int DEFAULT NULL,
  `resolved_at` datetime DEFAULT NULL,
  `type` longtext COLLATE utf8mb4_unicode_ci,
  `intensity` double NOT NULL,
  `code_incident` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_3D03A11A8EAE3863` (`intervention_id`),
  KEY `IDX_3D03A11A64D218E` (`location_id`),
  CONSTRAINT `FK_3D03A11A64D218E` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_3D03A11A8EAE3863` FOREIGN KEY (`intervention_id`) REFERENCES `intervention` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incident`
--

LOCK TABLES `incident` WRITE;
/*!40000 ALTER TABLE `incident` DISABLE KEYS */;
/*!40000 ALTER TABLE `incident` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intervention`
--

DROP TABLE IF EXISTS `intervention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intervention` (
  `id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `coefficient` double NOT NULL,
  `number_vehicles` int NOT NULL,
  `number_agents` int NOT NULL,
  `resolved_at` datetime DEFAULT NULL,
  `incident_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_D11814AB59E53FB9` (`incident_id`),
  KEY `IDX_D11814AB296CD8AE` (`team_id`),
  CONSTRAINT `FK_D11814AB296CD8AE` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FK_D11814AB59E53FB9` FOREIGN KEY (`incident_id`) REFERENCES `incident` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intervention`
--

LOCK TABLES `intervention` WRITE;
/*!40000 ALTER TABLE `intervention` DISABLE KEYS */;
/*!40000 ALTER TABLE `intervention` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` tinytext COLLATE utf8mb4_unicode_ci NOT NULL,
  `coefficient` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (2,'Lieutenant',2),(3,'Adjudant',1),(4,'Sapeur',0.5);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_5E9E89CB8BAC62AF` (`city_id`),
  CONSTRAINT `FK_5E9E89CB8BAC62AF` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (8,1,45.7841,4.8689),(9,1,45.7941,4.8588),(10,1,45.7963,4.8492),(11,1,45.7943,4.8482),(12,1,45.7953,4.8437),(13,1,45.7943,4.8427),(14,1,45.8035,4.8357),(15,1,45.8025,4.8347),(16,1,45.8063,4.8325),(17,1,45.8053,4.8315),(18,1,45.7486,4.8218),(19,1,45.7476,4.8208),(20,1,45.7431,4.8096),(21,1,45.7421,4.8086),(22,1,45.7484,4.8554),(23,1,45.7274,4.8544),(24,1,45.7262,4.8755),(25,1,45.7252,4.8745),(26,1,45.7326,4.8981),(27,1,45.7316,4.8971),(28,1,45.7381,4.8922);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` tinytext COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (4,'Delta S4'),(5,'Hazan S4'),(6,'Sagdiyev S4');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_vehicle_id` int DEFAULT NULL,
  `team_id` int DEFAULT NULL,
  `registration_number` tinytext COLLATE utf8mb4_unicode_ci NOT NULL,
  `fuel` longtext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `IDX_1B80E486724D47AF` (`category_vehicle_id`),
  KEY `IDX_1B80E486296CD8AE` (`team_id`),
  CONSTRAINT `FK_1B80E486296CD8AE` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FK_1B80E486724D47AF` FOREIGN KEY (`category_vehicle_id`) REFERENCES `category_vehicle` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES (2,2,4,'AJ-630-KR','diesel'),(3,3,5,'CL-643-EK','mazout'),(4,3,5,'EG-772-BK','mazout'),(5,4,6,'WW-145-XY','mazout'),(6,4,6,'AB-572-EU','mazout');
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-14  1:53:42
