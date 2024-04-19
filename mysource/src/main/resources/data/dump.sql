-- MySQL dump 10.13  Distrib 8.2.0, for Linux (aarch64)
--
-- Host: mysourcedb    Database: mysourcedb
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `cmn_confirmations`
--

DROP TABLE IF EXISTS `cmn_confirmations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cmn_confirmations` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `created_by` bigint NOT NULL,
  `reference_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` bigint NOT NULL,
  `key` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gyj14wr9smwld1tbmg92hudwa` (`user_id`),
  CONSTRAINT `FKjpi14bwo7gw03oc6is4je71x5` FOREIGN KEY (`user_id`) REFERENCES `cmn_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmn_confirmations`
--

LOCK TABLES `cmn_confirmations` WRITE;
/*!40000 ALTER TABLE `cmn_confirmations` DISABLE KEYS */;
/*!40000 ALTER TABLE `cmn_confirmations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmn_credentials`
--

DROP TABLE IF EXISTS `cmn_credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cmn_credentials` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `created_by` bigint NOT NULL,
  `reference_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` bigint NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dbf7chf4k870f4s5lfc6h1h9o` (`user_id`),
  CONSTRAINT `FK6c4ssj7xvb79heoqgt8jxhh3k` FOREIGN KEY (`user_id`) REFERENCES `cmn_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmn_credentials`
--

LOCK TABLES `cmn_credentials` WRITE;
/*!40000 ALTER TABLE `cmn_credentials` DISABLE KEYS */;
/*!40000 ALTER TABLE `cmn_credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmn_roles`
--

DROP TABLE IF EXISTS `cmn_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cmn_roles` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `created_by` bigint NOT NULL,
  `reference_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` bigint NOT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmn_roles`
--

LOCK TABLES `cmn_roles` WRITE;
/*!40000 ALTER TABLE `cmn_roles` DISABLE KEYS */;
INSERT INTO `cmn_roles` VALUES (1,'2024-04-19 06:13:40.612252',0,'f70d7be2-4db2-bf45-b01d-2e81b49968ff','2024-04-19 06:13:40.737165',0,'USER','USER'),(2,'2024-04-19 06:13:40.816551',0,'7dd0abc6-01bd-d74e-ef60-c54682e5baf4','2024-04-19 06:13:40.847923',0,'ADMIN','ADMIN'),(3,'2024-04-19 20:14:47.044901',0,'df956f5f-092f-a52f-f87d-a55e9e2b0e1d','2024-04-19 20:14:47.136817',0,'MANAGER','Manager'),(4,'2024-04-19 20:14:47.198796',0,'ce56419c-572f-4dc7-b3a8-c9bdfa9461b7','2024-04-19 20:14:47.213731',0,'SUPER_ADMIN','Host');
/*!40000 ALTER TABLE `cmn_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmn_user_roles`
--

DROP TABLE IF EXISTS `cmn_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cmn_user_roles` (
  `role_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK343hfcykm1n4htcidvaksgt7i` (`role_id`),
  CONSTRAINT `FK279swvgif0g4krocxjulu7pn3` FOREIGN KEY (`user_id`) REFERENCES `cmn_users` (`id`),
  CONSTRAINT `FK343hfcykm1n4htcidvaksgt7i` FOREIGN KEY (`role_id`) REFERENCES `cmn_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmn_user_roles`
--

LOCK TABLES `cmn_user_roles` WRITE;
/*!40000 ALTER TABLE `cmn_user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `cmn_user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmn_users`
--

DROP TABLE IF EXISTS `cmn_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cmn_users` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `created_by` bigint NOT NULL,
  `reference_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` bigint NOT NULL,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `login_attempts` int DEFAULT NULL,
  `mfa` bit(1) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `qr_code_image_uri` varchar(255) DEFAULT NULL,
  `qr_code_secret` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_phqwivsg7v03uko6x3pg1yjqs` (`email`),
  UNIQUE KEY `UK_9vlg6hjn9tjro4y2ak30pnoos` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmn_users`
--

LOCK TABLES `cmn_users` WRITE;
/*!40000 ALTER TABLE `cmn_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `cmn_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `primary_key_seq`
--

DROP TABLE IF EXISTS `primary_key_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `primary_key_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `primary_key_seq`
--

LOCK TABLES `primary_key_seq` WRITE;
/*!40000 ALTER TABLE `primary_key_seq` DISABLE KEYS */;
INSERT INTO `primary_key_seq` VALUES (15);
/*!40000 ALTER TABLE `primary_key_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-19 20:27:06
