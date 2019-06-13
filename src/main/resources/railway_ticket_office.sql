CREATE DATABASE  IF NOT EXISTS `railway_ticket_office` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `railway_ticket_office`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: railway_ticket_office
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coach_types`
--

DROP TABLE IF EXISTS `coach_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `coach_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name_en` varchar(45) NOT NULL,
  `name_ru` varchar(45) NOT NULL,
  `places` int(11) NOT NULL,
  `markup` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1704 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coach_types`
--

LOCK TABLES `coach_types` WRITE;
/*!40000 ALTER TABLE `coach_types` DISABLE KEYS */;
INSERT INTO `coach_types` VALUES (1,'Seating first class','Сидячий первый класс',56,25),(2,'Seating second class','Сидячий второй класс',80,15),(3,'Compartment','Купе',36,10),(4,'Berth','Плацкарт',54,5),(5,'De Luxe','Люкс',20,35);
/*!40000 ALTER TABLE `coach_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger_roles`
--

DROP TABLE IF EXISTS `passenger_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `passenger_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passenger_id` int(11) NOT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`,`passenger_id`),
  UNIQUE KEY `idpassenger_roles_UNIQUE` (`id`),
  KEY `fk_passenger_roles_passengers1_idx` (`passenger_id`),
  CONSTRAINT `fk_passenger_roles_passengers1` FOREIGN KEY (`passenger_id`) REFERENCES `passengers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1708 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger_roles`
--

LOCK TABLES `passenger_roles` WRITE;
/*!40000 ALTER TABLE `passenger_roles` DISABLE KEYS */;
INSERT INTO `passenger_roles` VALUES (1,1,'USER'),(2,2,'USER'),(3,4,'USER'),(4,1,'ADMIN'),(31,31,'USER'),(32,2,'ADMIN'),(1705,1704,'USER'),(1706,1705,'USER'),(1707,1705,'ADMIN');
/*!40000 ALTER TABLE `passenger_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passengers`
--

DROP TABLE IF EXISTS `passengers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `passengers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=1706 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passengers`
--

LOCK TABLES `passengers` WRITE;
/*!40000 ALTER TABLE `passengers` DISABLE KEYS */;
INSERT INTO `passengers` VALUES (1,'Кибко','Александра','tiver69','6c2a5c9ead1d7d6ba86c8764d5cad395'),(2,'Savina','Evgenia','shipper1232','6c2a5c9ead1d7d6ba86c8764d5cad395'),(4,'Protasov','Vladislav','protasov1','0102812fbd5f73aa18aa0bae2cd8f79f'),(31,'Костяев','Анатолий','artmole','b9974191c2e2806abb0ed0fe229ca0f6'),(1704,'Васильченко','Светлана','wasvet','60d589174ca2b89351a41d90a8c9c2cf'),(1705,'Онищенко','Елизавета','lemonycap','0102812fbd5f73aa18aa0bae2cd8f79f');
/*!40000 ALTER TABLE `passengers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `stations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name_en` varchar(45) NOT NULL,
  `name_ru` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1710 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,'Zaporizhzhya 1','Запорожье 1'),(2,'Kyiv-Pasazhyrsky','Киев-Пассажирский'),(3,'Im. T. Shevchenka','Им. Т. Шевченка'),(4,'Znamyanka Pasazhyrska','Знаменка Пассажирская'),(5,'Oleksandriya','Александрия'),(6,'Pyatykhatky','Пятихатки'),(7,'Kamianske-Pasazhyrske','Каменское-Пассажирское'),(8,'Dnipro-Holovny','Днепр-Главный'),(9,'Darnytsya','Дарница'),(10,'Myrhorod','Миргород'),(11,'Poltava Kyivska','Полтава Киевская'),(12,'Kharkiv-Pas','Харьков-Пас'),(13,'Dniprobud 2','Днепробуд 2'),(14,'Nikopol','Никополь'),(15,'Trypillya Dniprovske','Триполье Днепровское'),(16,'Melitopol','Мелитополь'),(17,'Novooleksiyivka','Новоалексеевка'),(18,'Henichesk','Геническ'),(19,'Synelnykove-1','Синельниково-1'),(20,'Krasnopavlivka','Краснопавловка');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passenger_id` int(11) NOT NULL,
  `departure_station_id` int(11) NOT NULL,
  `destination_station_id` int(11) NOT NULL,
  `departure_date` date NOT NULL,
  `coach_id` int(11) NOT NULL,
  `place` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`,`passenger_id`,`departure_station_id`,`destination_station_id`,`coach_id`),
  KEY `fk_tickets_stations1_idx` (`departure_station_id`),
  KEY `fk_tickets_stations2_idx` (`destination_station_id`),
  KEY `fk_tickets_passangers1_idx` (`passenger_id`),
  KEY `fk_tickets_trains_has_coaches1_idx` (`coach_id`),
  CONSTRAINT `fk_tickets_passangers1` FOREIGN KEY (`passenger_id`) REFERENCES `passengers` (`id`),
  CONSTRAINT `fk_tickets_stations1` FOREIGN KEY (`departure_station_id`) REFERENCES `stations` (`id`),
  CONSTRAINT `fk_tickets_stations2` FOREIGN KEY (`destination_station_id`) REFERENCES `stations` (`id`),
  CONSTRAINT `fk_tickets_trains_has_coaches1` FOREIGN KEY (`coach_id`) REFERENCES `train_coaches` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1711 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (4,1,2,1,'2019-05-31',20,17,472),(5,2,2,1,'2019-05-31',20,18,472),(6,1,2,3,'2019-05-31',20,19,67),(18,4,10,12,'2019-06-20',35,33,140),(24,1,1,3,'2019-05-15',45,4,164),(25,1,2,12,'2019-06-06',35,10,280),(26,1,2,1,'2019-06-12',20,16,472),(27,1,1,2,'2019-06-12',45,18,220),(28,1,1,2,'2019-06-12',45,20,220),(30,1,2,1,'2019-06-12',20,19,472),(31,1,2,8,'2019-06-12',20,9,405),(32,1,2,12,'2019-06-13',28,15,260),(33,1,13,2,'2019-06-19',48,25,115),(1704,1,2,1,'2019-06-11',38,13,210),(1705,1,2,1,'2019-06-11',38,9,210),(1706,1704,1,17,'2019-07-07',65,77,57),(1707,1704,12,1,'2019-07-23',66,69,115),(1708,1705,2,1,'2019-06-30',42,27,220),(1709,1705,1,2,'2019-08-31',50,24,220),(1710,1705,2,12,'2019-06-12',32,67,230);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train_coaches`
--

DROP TABLE IF EXISTS `train_coaches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `train_coaches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `train_id` int(11) NOT NULL,
  `coach_type_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`,`train_id`,`coach_type_id`),
  KEY `fk_trains_has_coaches_coaches1_idx` (`coach_type_id`),
  KEY `fk_trains_has_coaches_trains_idx` (`train_id`),
  CONSTRAINT `fk_trains_has_coaches_coaches1` FOREIGN KEY (`coach_type_id`) REFERENCES `coach_types` (`id`),
  CONSTRAINT `fk_trains_has_coaches_trains` FOREIGN KEY (`train_id`) REFERENCES `trains` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1714 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train_coaches`
--

LOCK TABLES `train_coaches` WRITE;
/*!40000 ALTER TABLE `train_coaches` DISABLE KEYS */;
INSERT INTO `train_coaches` VALUES (19,732,1,2),(20,732,1,5),(21,732,1,8),(22,732,2,1),(23,732,2,3),(24,732,2,4),(25,732,2,6),(26,732,2,7),(27,732,2,9),(28,724,2,1),(29,724,2,3),(30,724,2,4),(31,724,2,6),(32,724,2,7),(33,724,2,9),(34,724,1,2),(35,724,1,5),(36,724,1,8),(37,72,4,5),(38,72,4,7),(39,72,4,8),(40,72,3,1),(41,72,3,2),(42,72,3,3),(43,72,3,4),(44,72,3,6),(45,73,4,5),(46,73,4,7),(47,73,4,8),(48,73,3,1),(49,73,3,2),(50,73,3,3),(51,73,3,4),(52,73,3,6),(53,12,3,1),(54,12,3,2),(55,12,4,3),(56,12,4,4),(57,12,4,5),(58,12,4,6),(59,12,3,7),(60,12,5,8),(61,12,3,9),(62,12,3,10),(63,729,1,1),(64,729,1,2),(65,729,2,3),(66,729,2,4),(67,729,2,5);
/*!40000 ALTER TABLE `train_coaches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train_stations`
--

DROP TABLE IF EXISTS `train_stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `train_stations` (
  `station_id` int(11) NOT NULL AUTO_INCREMENT,
  `train_id` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `prise` int(11) NOT NULL,
  `arrival` time DEFAULT NULL,
  `departure` time DEFAULT NULL,
  PRIMARY KEY (`station_id`,`train_id`),
  KEY `fk_stations_has_trains_trains1_idx` (`train_id`),
  KEY `fk_stations_has_trains_stations1_idx` (`station_id`),
  CONSTRAINT `fk_stations_has_trains_stations1` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`),
  CONSTRAINT `fk_stations_has_trains_trains1` FOREIGN KEY (`train_id`) REFERENCES `trains` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train_stations`
--

LOCK TABLES `train_stations` WRITE;
/*!40000 ALTER TABLE `train_stations` DISABLE KEYS */;
INSERT INTO `train_stations` VALUES (1,12,7,25,'01:58:00','02:06:00'),(1,72,4,50,'07:49:00',NULL),(1,73,0,0,NULL,'18:20:00'),(1,729,3,50,'10:22:00','10:25:00'),(1,732,7,50,'14:35:00',NULL),(2,12,0,0,NULL,'15:07:00'),(2,72,0,0,NULL,'20:12:00'),(2,73,4,50,'05:52:00',NULL),(2,724,0,0,NULL,'13:25:00'),(2,732,0,0,NULL,'07:07:00'),(3,12,2,25,'18:29:00','18:34:00'),(3,72,1,50,'23:02:00','23:07:00'),(3,73,3,50,'02:55:00','03:00:00'),(3,732,1,50,'09:23:00','09:24:00'),(4,12,3,25,'19:45:00','19:48:00'),(4,732,2,50,'10:24:00','10:25:00'),(5,732,3,50,'10:52:00','10:53:00'),(6,12,4,25,'21:38:00','21:40:00'),(6,732,4,50,'11:39:00','11:40:00'),(7,12,5,25,'23:04:00','23:06:00'),(7,732,5,50,'12:25:00','12:26:00'),(8,12,6,25,'23:42:00','00:08:00'),(8,732,6,50,'12:52:00','13:02:00'),(9,724,1,50,'13:43:00','13:45:00'),(10,724,2,50,'15:43:00','15:45:00'),(11,724,3,50,'16:38:00','16:40:00'),(12,724,4,50,'18:16:00',NULL),(12,729,0,0,NULL,'06:26:00'),(13,72,3,50,'07:13:00','07:15:00'),(13,73,2,50,'20:17:00','20:37:00'),(14,72,2,50,'05:18:00','05:32:00'),(14,73,1,50,'18:55:00','18:56:00'),(15,12,1,25,'15:47:00','15:55:00'),(16,12,8,25,'03:41:00','03:46:00'),(16,729,4,25,'12:02:00','12:04:00'),(17,12,9,25,'04:49:00',NULL),(17,729,5,25,'13:07:00','13:13:00'),(18,729,6,25,'13:27:00',NULL),(19,729,2,25,'09:29:00','09:31:00'),(20,729,1,25,'07:48:00','07:49:00');
/*!40000 ALTER TABLE `train_stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trains`
--

DROP TABLE IF EXISTS `trains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `trains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `frequency` int(11) NOT NULL,
  `markup` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1704 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trains`
--

LOCK TABLES `trains` WRITE;
/*!40000 ALTER TABLE `trains` DISABLE KEYS */;
INSERT INTO `trains` VALUES (12,7,5),(72,1,5),(73,1,5),(724,1,15),(729,1,10),(732,2,10);
/*!40000 ALTER TABLE `trains` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-12 14:14:37
