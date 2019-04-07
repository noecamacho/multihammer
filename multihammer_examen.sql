/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.1.33-MariaDB : Database - multihammer
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`multihammer` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `multihammer`;

/*Table structure for table `acceso` */

DROP TABLE IF EXISTS `acceso`;

CREATE TABLE `acceso` (
  `id_perfil` tinyint(2) unsigned NOT NULL,
  `id_modulo` tinyint(2) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `acceso` */

insert  into `acceso`(`id_perfil`,`id_modulo`) values (2,1),(1,5),(1,4),(1,2),(1,7),(2,7),(2,8),(1,9),(1,8),(1,3),(1,1),(1,6);

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `domicilio` varchar(100) DEFAULT NULL,
  `rfc` varchar(13) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `telefono` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `rfc` (`rfc`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

insert  into `clientes`(`id_cliente`,`domicilio`,`rfc`,`nombre`,`apellido`,`telefono`) values (1,'Rio Presidio','KELO210298ABA','Público','General','6691238459'),(2,'Lomas de Mazatlan #236','NOCL021198AB5','Noe ','Camacho','6691990132'),(3,'Por el Renacimiento','LICL180297ABA','Lilian Karina','Covarrubias Lizárraga','6691837253'),(4,'Rio Presidio 307-Altos','KELO210298KL7','Kevin','Lizárraga','6691238459'),(5,'- UPSIN','ESVO219382ABA','Esteban Alberto','Valencia Obregón','6691827364'),(7,'SADSAJO123','ESVO219382AB5','Esteban','Valencia','6691828342'),(8,'Col. Ferrocarrilera','ALOS281190AV3','Alejandro','Lizárraga Osuna','6691827321'),(9,'Jabalies','ESRS2204997B3','Esperanza','Rámirez Sánchez','6691827321'),(10,'Mazátlan','ALPP301280726','Alejandro','Pasten','6691823123'),(11,'Villa Verde','LDFM071099716','Luis Daniel','Fernández Muñoz','6691823123');

/*Table structure for table `materiales` */

DROP TABLE IF EXISTS `materiales`;

CREATE TABLE `materiales` (
  `id_material` tinyint(4) unsigned NOT NULL AUTO_INCREMENT,
  `material` varchar(50) DEFAULT NULL,
  `descripcion` text,
  PRIMARY KEY (`id_material`),
  UNIQUE KEY `material` (`material`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

/*Data for the table `materiales` */

insert  into `materiales`(`id_material`,`material`,`descripcion`) values (1,'Piso Rojo','Un piso rojo '),(2,'Varilla 3/4','Varilla de tres cuartos'),(3,'Piso','Un piso más'),(16,'Cemento gris','Cemento duradero'),(17,'Piso azul','Piso azul'),(18,'Varilla 5/8','Varilla de cinco octavos'),(19,'Varilla 3/8','Varilla de tres octavos'),(20,'Alambre recocido','Alambre recocido'),(21,'Alambrón','Alambrón'),(22,'Mortero','Mortero del bueno');

/*Table structure for table `modulos` */

DROP TABLE IF EXISTS `modulos`;

CREATE TABLE `modulos` (
  `id_modulo` tinyint(2) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `icon` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_modulo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `modulos` */

insert  into `modulos`(`id_modulo`,`nombre`,`estado`,`icon`) values (1,'Productos',1,'VIEW_LIST'),(2,'Reportes',1,'LIBRARY_BOOKS'),(3,'Estadisticas',1,'CHART_LINE'),(4,'Usuarios',1,'ACCOUNT_MULTIPLE'),(5,'Modulos',1,'SETTINGS'),(6,'Proveedores',1,'TRUCK'),(7,'Ventas',1,'CART'),(8,'Clientes',1,'ACCOUNT_CIRCLE'),(9,'Pedidos',1,'CLIPBOARD_TEXT'),(10,'Perfiles',1,'HOME');

/*Table structure for table `pedidos` */

DROP TABLE IF EXISTS `pedidos`;

CREATE TABLE `pedidos` (
  `id_pedido` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) DEFAULT NULL,
  `total` float(8,2) DEFAULT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `estado` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `pedidos` */

insert  into `pedidos`(`id_pedido`,`id_cliente`,`total`,`fecha`,`estado`) values (2,3,1286.20,'2019-03-14 20:25:01',1),(3,2,659.90,'2019-03-14 20:28:56',1),(4,1,2316.20,'2019-03-14 20:36:35',1),(5,2,66715.75,'2019-03-14 20:57:15',1),(6,1,809.90,'2019-03-15 12:42:01',1),(7,4,50125.00,'2019-03-16 16:31:58',1),(8,2,22820.00,'2019-03-16 16:32:54',1),(9,2,6319.80,'2019-03-19 09:13:31',1),(10,8,10180.00,'2019-03-26 18:52:54',1),(11,9,2975.00,'2019-03-26 18:54:46',1),(12,10,50000.00,'2019-03-26 18:55:43',1),(13,11,8965.00,'2019-03-26 18:56:55',1);

/*Table structure for table `perfiles` */

DROP TABLE IF EXISTS `perfiles`;

CREATE TABLE `perfiles` (
  `id_perfil` tinyint(2) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `perfiles` */

insert  into `perfiles`(`id_perfil`,`nombre`,`descripcion`) values (1,'Administrador','el mero mero'),(2,'Empleado','dependiente'),(3,'Vendedor','vendedor'),(4,'Encargado','encargado'),(5,'Encargado de piso','pisos'),(6,'Almacenista','almacen'),(7,'Conserje','limpia'),(8,'Seguridad','guardia'),(9,'Enfermeria','primeros auxilios'),(10,'Otro','cosas varias');

/*Table structure for table `productos` */

DROP TABLE IF EXISTS `productos`;

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT,
  `id_material` tinyint(4) DEFAULT NULL,
  `unidad` varchar(50) DEFAULT NULL,
  `precio` float(8,2) DEFAULT NULL,
  `id_proveedor` tinyint(2) DEFAULT NULL,
  `cantidad` smallint(6) DEFAULT NULL,
  `existencia` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

/*Data for the table `productos` */

insert  into `productos`(`id_producto`,`id_material`,`unidad`,`precio`,`id_proveedor`,`cantidad`,`existencia`) values (1,1,'PZA',65.99,2,26,1),(2,1,'m²',75.25,2,45,1),(3,1,'pz',70.00,1,23,0),(4,2,'PZA',470.00,1,43,1),(5,3,'pz',20.00,3,300,0),(6,1,'m²',210.00,1,8,0),(21,15,'m',123.00,2,91,0),(22,16,'Saco 50KG',160.00,1,8,1),(23,17,'PZA',500.00,2,500,1),(24,16,'KG',50.00,4,50,0),(25,16,'m²',70.00,4,70,0),(26,2,'m²',100.00,1,100,1),(27,18,'PZA',330.00,2,10,1),(28,19,'PZA',115.00,3,5,1),(29,20,'KG',22.00,1,45,1),(30,21,'KG',20.00,2,92,1),(31,22,'Saco 50KG',125.00,6,72,1),(32,3,'PZA',20.00,4,117,1);

/*Table structure for table `proveedores` */

DROP TABLE IF EXISTS `proveedores`;

CREATE TABLE `proveedores` (
  `id_proveedor` tinyint(2) NOT NULL AUTO_INCREMENT,
  `domicilio` varchar(100) NOT NULL,
  `rfc` varchar(13) NOT NULL,
  `razon_social` varchar(50) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `telefono` varchar(12) NOT NULL,
  PRIMARY KEY (`id_proveedor`),
  UNIQUE KEY `razon_social` (`razon_social`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `proveedores` */

insert  into `proveedores`(`id_proveedor`,`domicilio`,`rfc`,`razon_social`,`estado`,`telefono`) values (1,'Rio Presidio','MELM8305281H0','Provisa',1,'6691238459'),(2,'Mazatlan','UEPS123632KSJ','UPSIN',1,'6692847232'),(3,'Mazatlan','KALO210298AL3','Kuroda',1,'5582918237'),(4,'Castillito Plateado Feliz 123','MEX211198ABA','PisoMEX',1,'6691238472'),(6,'UPSIN','UPSI239281AB4','UPSIN3',1,'6691823723'),(7,'UPSIN','MULT127321AB5','Multihammer',1,'6691232172'),(8,'Culiacan','VARM210290AB2','VarMX',1,'6691827123'),(9,'Jalisco','CEMX120687716','CEMEX',1,'6692837212'),(10,'Mexico','ROTO918271231','Rotoplas',1,'6691827232');

/*Table structure for table `registros` */

DROP TABLE IF EXISTS `registros`;

CREATE TABLE `registros` (
  `id_registro` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) unsigned NOT NULL,
  `id_producto` int(11) unsigned NOT NULL,
  `cantidad` smallint(6) unsigned NOT NULL,
  `precio` float(8,2) unsigned NOT NULL,
  PRIMARY KEY (`id_registro`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

/*Data for the table `registros` */

insert  into `registros`(`id_registro`,`id_pedido`,`id_producto`,`cantidad`,`precio`) values (1,2,1,5,65.99),(2,2,2,5,75.25),(3,2,3,2,70.00),(4,2,5,22,20.00),(5,3,1,10,65.99),(6,4,1,5,65.99),(7,4,2,5,75.25),(8,4,3,2,70.00),(9,4,6,7,210.00),(10,5,1,1000,65.99),(11,5,4,50,10.00),(12,5,2,3,75.25),(13,6,1,10,65.99),(14,6,4,15,10.00),(15,7,22,50,50.00),(16,7,5,500,20.00),(17,7,2,500,75.25),(18,8,4,1000,10.00),(19,8,5,641,20.00),(20,9,1,20,65.99),(21,9,27,500,10.00),(22,10,27,10,470.00),(23,10,28,5,115.00),(24,10,27,8,330.00),(25,10,31,5,125.00),(26,10,22,5,160.00),(27,10,30,20,20.00),(28,10,29,20,22.00),(29,11,31,10,125.00),(30,11,28,15,115.00),(31,12,23,100,500.00),(32,13,29,15,22.00),(33,13,27,15,330.00),(34,13,28,3,115.00),(35,13,27,2,470.00),(36,13,22,15,160.00);

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `id_usuario` tinyint(4) NOT NULL AUTO_INCREMENT,
  `id_perfil` tinyint(4) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `usuario` (`usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`id_usuario`,`id_perfil`,`usuario`,`password`,`estado`) values (1,1,'admin','admin',1),(2,2,'emple','emple',1),(3,2,'user','user',1),(4,1,'kevin','contraseña',1),(5,2,'noe','contraseña',1),(6,1,'lilian','contraseña',1),(7,2,'esperanza','contraseña',1),(8,1,'esteban','contraseña',1),(9,2,'pepe','pepe',1),(10,2,'juan','juan',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
