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
  `order` bigint NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_data`
--

LOCK TABLES `search_data` WRITE;
/*!40000 ALTER TABLE `search_data` DISABLE KEYS */;
INSERT INTO `search_data` VALUES (5,'merrell coupons','https://www.offers.com/merrell/',1,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(6,'merrell coupons','https://www.couponarea.com/view/merrell.com',2,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(7,'merrell coupons','https://www.merrell.com/US/en/promotions-coupons/',3,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(8,'merrell coupons','http://www.couponalbum.com/coupons/merrell.htm',4,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(9,'merrell coupons','https://www.fyvor.com/coupons/merrell.com/',5,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(10,'merrell coupons','https://www.freehotcoupons.org/hotcoupons/merrell-coupons-discounts',6,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(11,'merrell coupons','https://givingassistant.org/coupon-codes/merrell.com',7,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(12,'merrell coupons','https://www.goodsearch.com/coupons/merrell',8,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(13,'merrell coupons','https://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',9,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(14,'merrell coupons','https://dealhack.com/coupons/merrell',10,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(15,'merrell coupons','https://www.techbargains.com/stores/merrell',11,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(16,'merrell coupons','https://www.dealcatcher.com/merrell-coupons',12,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(17,'merrell coupons','https://www.getcouponcodes.com/coupon-code/Merrell',13,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(18,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',14,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(19,'merrell abc xyz','https://www.google.com.vn/?hl=vi',1,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(20,'abc coupons xyz','https://www.google.com.vn/?hl=vi',2,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(21,'abc xyz coupons','https://www.google.com.vn/?hl=vi',3,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(22,'abc merrell xyz coupons','https://www.google.com.vn/?hl=vi',4,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(23,'abc merrell coupons xyz','https://www.google.com.vn/?hl=vi',5,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(24,'abc merrell coupons xyz','https://www.google.com.vn/?hl=vi2',6,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(25,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',15,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(26,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',16,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(27,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',17,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(28,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',18,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(29,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',19,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(30,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',20,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(31,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',21,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(32,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',22,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(33,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',23,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(34,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',24,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(35,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',25,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(36,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',26,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(37,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',27,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(38,'merrell abc xyz','https://stackoverflow.com/',23,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(39,'merrell abc xyz','https://stackoverflow.com/',24,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(40,'merrell abc xyz','https://stackoverflow.com/',25,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(41,'merrell abc xyz','https://stackoverflow.com/',26,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(42,'merrell abc xyz','https://stackoverflow.com/',27,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(43,'merrell abc xyz','https://stackoverflow.com/',7,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(44,'merrell abc xyz','https://stackoverflow.com/',8,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(45,'merrell abc xyz','https://stackoverflow.com/',9,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(46,'merrell abc xyz','https://stackoverflow.com/',10,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(47,'merrell abc xyz','https://stackoverflow.com/',11,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(48,'merrell abc xyz','https://stackoverflow.com/',12,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(49,'merrell abc xyz','https://stackoverflow.com/',13,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(50,'merrell abc xyz','https://stackoverflow.com/',14,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(51,'merrell abc xyz','https://stackoverflow.com/',15,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(52,'merrell abc xyz','https://stackoverflow.com/',16,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(53,'merrell abc xyz','https://stackoverflow.com/',17,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(54,'merrell abc xyz','https://stackoverflow.com/',18,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(55,'merrell abc xyz','https://stackoverflow.com/',19,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(56,'merrell abc xyz','https://stackoverflow.com/',20,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(57,'merrell abc xyz','https://stackoverflow.com/',21,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(58,'merrell abc xyz','https://stackoverflow.com/',22,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(59,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',28,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(60,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',29,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(61,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',30,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(62,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',31,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(63,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',32,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(64,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',33,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(65,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',34,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(66,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',35,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(67,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',36,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(68,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',37,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(69,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',38,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(70,'merrell coupons','http://www.dealigg.com/story-Merrell-Coupons-Merrell-Coupon-Code',39,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(72,'merrell abc xyz','https://stackoverflow.com/',28,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(73,'merrell abc xyz','https://stackoverflow.com/',29,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(74,'merrell abc xyz','https://stackoverflow.com/',30,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(75,'merrell abc xyz','https://stackoverflow.com/',31,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(76,'merrell abc xyz','https://stackoverflow.com/',32,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(77,'merrell abc xyz','https://stackoverflow.com/',33,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(78,'merrell abc xyz','https://stackoverflow.com/',34,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(79,'merrell abc xyz','https://stackoverflow.com/',35,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(80,'merrell abc xyz','https://stackoverflow.com/',36,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(81,'merrell abc xyz','https://stackoverflow.com/',37,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(82,'merrell abc xyz','https://stackoverflow.com/',38,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(84,'merrell abc xyz','https://stackoverflow.com/',39,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(85,'merrell abc xyz','https://stackoverflow.com/',40,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(86,'merrell abc xyz','https://stackoverflow.com/',41,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(87,'merrell abc xyz','https://stackoverflow.com/',42,'2020-03-02 00:00:00','2020-03-02 12:00:00'),(88,'merrell abc xyz','https://stackoverflow.com/',43,'2020-03-02 00:00:00','2020-03-02 12:00:00');
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

-- Dump completed on 2020-03-04  1:21:56
