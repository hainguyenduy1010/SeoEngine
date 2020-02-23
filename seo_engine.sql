CREATE DATABASE  IF NOT EXISTS `seo_engine` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `seo_engine`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: seo_engine
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `search_data`
--

DROP TABLE IF EXISTS `search_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `search_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `keyword` text NOT NULL,
  `url` text NOT NULL,
  `sortkey` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_data`
--

LOCK TABLES `search_data` WRITE;
/*!40000 ALTER TABLE `search_data` DISABLE KEYS */;
INSERT INTO `search_data` VALUES (5,'merrell coupons','https://www.offers.com/merrell/',1),(6,'merrell coupons','https://www.couponarea.com/view/merrell.com',NULL),(7,'merrell coupons','https://www.merrell.com/US/en/promotions-coupons/',2),(8,'merrell coupons','http://www.couponalbum.com/coupons/merrell.htm',3),(9,'merrell coupons','https://www.fyvor.com/coupons/merrell.com/',NULL),(10,'merrell coupons','https://www.freehotcoupons.org/hotcoupons/merrell-coupons-discounts',NULL),(11,'merrell coupons','https://givingassistant.org/coupon-codes/merrell.com',NULL),(12,'merrell coupons','https://www.goodsearch.com/coupons/merrell',NULL),(13,'merrell coupons','https://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',NULL),(14,'merrell coupons','https://dealhack.com/coupons/merrell',NULL),(15,'merrell coupons','https://www.techbargains.com/stores/merrell',NULL),(16,'merrell coupons','https://www.dealcatcher.com/merrell-coupons',NULL),(17,'merrell coupons','https://www.getcouponcodes.com/coupon-code/Merrell',NULL),(18,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',NULL),(19,'merrell abc xyz','https://www.google.com.vn/?hl=vi',NULL),(20,'abc coupons xyz','https://www.google.com.vn/?hl=vi',NULL),(21,'abc xyz coupons','https://www.google.com.vn/?hl=vi',NULL),(22,'abc merrell xyz coupons','https://www.google.com.vn/?hl=vi',NULL),(23,'abc merrell coupons xyz','https://www.google.com.vn/?hl=vi',NULL),(24,'abc merrell coupons xyz','https://www.google.com.vn/?hl=vi2',NULL);
/*!40000 ALTER TABLE `search_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-24  0:52:54
