-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: simulator_api
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Lyon','FRA');
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
INSERT INTO `doctrine_migration_versions` VALUES ('DoctrineMigrations\\Version20201208161013','2021-01-14 01:59:37',3709),('DoctrineMigrations\\Version20201209100119','2021-01-14 01:59:41',1766),('DoctrineMigrations\\Version20201209125635','2021-01-14 01:59:43',1857),('DoctrineMigrations\\Version20210106130217','2021-01-14 01:59:45',7458),('DoctrineMigrations\\Version20210106140511','2021-01-14 01:59:53',2727),('DoctrineMigrations\\Version20210106141458','2021-01-14 01:59:56',2821),('DoctrineMigrations\\Version20210106142910','2021-01-14 01:59:59',2239),('DoctrineMigrations\\Version20210106143553','2021-01-14 02:00:01',1789);
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
  `intensity` double NOT NULL,
  `resolved_at` datetime DEFAULT NULL,
  `type` longtext COLLATE utf8mb4_unicode_ci,
  `code_incident` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `sensor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_3D03A11A64D218E` (`location_id`),
  KEY `IDX_3D03A11AA247991F` (`sensor_id`),
  CONSTRAINT `FK_3D03A11A64D218E` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_3D03A11AA247991F` FOREIGN KEY (`sensor_id`) REFERENCES `sensor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
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
  `incident_id` int DEFAULT NULL,
  `coefficient` double NOT NULL,
  `number_vehicles` int NOT NULL,
  `number_agents` int NOT NULL,
  `resolved_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_D11814AB59E53FB9` (`incident_id`),
  CONSTRAINT `FK_D11814AB59E53FB9` FOREIGN KEY (`incident_id`) REFERENCES `incident` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intervention`
--

LOCK TABLES `intervention` WRITE;
/*!40000 ALTER TABLE `intervention` DISABLE KEYS */;
/*!40000 ALTER TABLE `intervention` ENABLE KEYS */;
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
-- Table structure for table `sensor`
--

DROP TABLE IF EXISTS `sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `location_id` int DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reference` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_BC8617B064D218E` (`location_id`),
  CONSTRAINT `FK_BC8617B064D218E` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (1,8,'SensoTemperature Gen4','sensoTempGen4','temperature'),(2,9,'SensoTemperature Gen4','sensoTempGen4','temperature'),(3,10,'SensoTemperature Gen4','sensoTempGen4','temperature'),(4,11,'SensoTemperature Gen4','sensoTempGen4','temperature'),(5,12,'SensoTemperature Gen4','sensoTempGen4','temperature'),(6,13,'SensoTemperature Gen4','sensoTempGen4','temperature'),(7,14,'SensoTemperature Gen4','sensoTempGen4','temperature'),(8,15,'SensoTemperature Gen4','sensoTempGen4','temperature'),(9,16,'SensoTemperature Gen4','sensoTempGen4','temperature'),(10,17,'SensoTemperature Gen4','sensoTempGen4','temperature'),(11,18,'SensoTemperature Gen4','sensoTempGen4','temperature'),(12,19,'SensoTemperature Gen4','sensoTempGen4','temperature'),(13,20,'SensoTemperature Gen4','sensoTempGen4','temperature'),(14,21,'SensoTemperature Gen4','sensoTempGen4','temperature'),(15,22,'SensoTemperature Gen4','sensoTempGen4','temperature'),(16,23,'SensoTemperature Gen4','sensoTempGen4','temperature'),(17,24,'SensoTemperature Gen4','sensoTempGen4','temperature'),(18,25,'SensoTemperature Gen4','sensoTempGen4','temperature'),(19,26,'SensoTemperature Gen4','sensoTempGen4','temperature'),(20,27,'SensoTemperature Gen4','sensoTempGen4','temperature'),(21,28,'SensoTemperature Gen4','sensoTempGen4','temperature');
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-14  2:06:55
