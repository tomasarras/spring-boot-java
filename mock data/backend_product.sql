-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: backend
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id_product` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `id_category` bigint NOT NULL,
  PRIMARY KEY (`id_product`),
  KEY `FK5cxv31vuhc7v32omftlxa8k3c` (`id_category`),
  CONSTRAINT `FK5cxv31vuhc7v32omftlxa8k3c` FOREIGN KEY (`id_category`) REFERENCES `category` (`id_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Puree - Kiwi',55.79,4),(2,'Bread - Kimel Stick Poly',87.28,8),(3,'Hagen Daza - Dk Choocolate',19.25,2),(4,'Coffee - 10oz Cup 92961',2.77,10),(5,'Cookies - Englishbay Chochip',91.27,8),(6,'Grapes - Black',96.68,10),(7,'Salmon - Atlantic, No Skin',36.58,1),(8,'Coconut - Creamed, Pure',29.46,6),(9,'Salmon - Sockeye Raw',48.25,8),(10,'Appetizer - Southwestern',91.35,10),(11,'Chicken - Whole',36.91,4),(12,'Sponge Cake Mix - Vanilla',3.32,3),(13,'Pork - Ham, Virginia',61.93,7),(14,'Wine - Cotes Du Rhone Parallele',79.34,5),(15,'Mackerel Whole Fresh',80.9,9),(16,'Sugar - White Packet',93.05,6),(17,'Veal - Inside',3.17,10),(18,'Thyme - Fresh',30.29,6),(19,'Basil - Seedlings Cookstown',84,4),(20,'Bread Sour Rolls',86.84,9),(21,'Spoon - Soup, Plastic',29.29,9),(22,'Salmon - Smoked, Sliced',49.5,5),(23,'Container - Clear 32 Oz',49.44,4),(24,'Appetizer - Lobster Phyllo Roll',93.66,8),(25,'Syrup - Monin, Irish Cream',97.22,4),(26,'Chips - Potato Jalapeno',19.43,2),(27,'Pheasants - Whole',65.32,8),(28,'Lamb - Shoulder',83.97,3),(29,'Sobe - Lizard Fuel',83.06,10),(30,'Ginger - Crystalized',86.18,1),(31,'Fork - Plastic',49.03,5),(32,'Latex Rubber Gloves Size 9',41.83,7),(33,'Mix - Cocktail Strawberry Daiquiri',20.16,3),(34,'Turkey - Breast, Boneless Sk On',3.61,7),(35,'Foil - 4oz Custard Cup',19.33,5),(36,'Gatorade - Cool Blue Raspberry',16.72,3),(37,'Wine - Shiraz Wolf Blass Premium',52.41,5),(38,'Squid - U 5',32.93,7),(39,'Pepper - Jalapeno',39.2,8),(40,'Pork - Back, Long Cut, Boneless',88.41,6),(41,'Pork - Back, Long Cut, Boneless',36.46,7),(42,'Veal - Chops, Split, Frenched',50.24,6),(43,'Scotch - Queen Anne',73.14,3),(44,'Wine - White, Mosel Gold',21.59,5),(45,'Muffin - Mix - Mango Sour Cherry',42.31,5),(46,'Juice - Orange, Concentrate',36.91,4),(47,'Ecolab - Ster Bac',4.18,5),(48,'Wine - Fontanafredda Barolo',47.1,10),(49,'Ham - Black Forest',71.49,6),(50,'Tomatillo',99.81,6),(51,'Chambord Royal',80.82,10),(52,'Water - Spring Water, 355 Ml',33.86,8),(53,'Chocolate Liqueur - Godet White',54.63,2),(54,'Cheese - Cheddarsliced',2.14,1),(55,'Butter - Pod',4,2),(56,'Snapple - Mango Maddness',28.49,3),(57,'Coffee Caramel Biscotti',25.77,7),(58,'Cheese - St. Andre',48.71,6),(59,'Beer - Muskoka Cream Ale',37.6,6),(60,'Fish - Scallops, Cold Smoked',94.1,9),(61,'Appetizer - Crab And Brie',13.62,5),(62,'Lettuce - Romaine',88.81,10),(63,'Buffalo - Striploin',41.67,10),(64,'Pasta - Orzo, Dry',59.61,7),(65,'Carrots - Mini, Stem On',11.68,5),(66,'Roe - Lump Fish, Black',67.12,7),(67,'Bread - Burger',31.66,7),(68,'Gatorade - Cool Blue Raspberry',69.66,6),(69,'Brandy - Orange, Mc Guiness',97.51,8),(70,'Chef Hat 25cm',28.48,8),(71,'Limes',20.66,5),(72,'Eel - Smoked',53.84,6),(73,'Skirt - 24 Foot',51.46,3),(74,'Lettuce - Green Leaf',72.67,9),(75,'Oven Mitt - 13 Inch',25.48,7),(76,'Crab - Meat Combo',57.43,8),(77,'Soup - Knorr, French Onion',53.39,3),(78,'Soup - Knorr, Classic Can. Chili',75.55,2),(79,'Bread - Crumbs, Bulk',31.73,1),(80,'Sour Puss - Tangerine',64.62,2),(81,'Honey - Lavender',32.96,1),(82,'Pear - Packum',86.26,5),(83,'Bread - Italian Sesame Poly',81.76,8),(84,'Scallops - 20/30',74.26,6),(85,'Cassis',52.18,3),(86,'Skirt - 24 Foot',45.6,7),(87,'Flour - Whole Wheat',45.62,2),(88,'Wine - Chianti Classica Docg',71.81,5),(89,'Zucchini - Mini, Green',78.41,1),(90,'Muffin Hinge - 211n',5.23,7),(91,'Cheese - Grie Des Champ',56.4,6),(92,'Mince Meat - Filling',15.24,3),(93,'Tart - Raisin And Pecan',25.63,8),(94,'Lamb - Pieces, Diced',79.55,7),(95,'Pastry - Apple Muffins - Mini',68.6,1),(96,'The Pop Shoppe - Lime Rickey',59.78,7),(97,'Bread - Italian Corn Meal Poly',30.67,3),(98,'Trout - Smoked',62.33,6),(99,'Nestea - Ice Tea, Diet',9.55,9),(100,'Water - Evian 355 Ml',20.47,6);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-22 21:03:54
