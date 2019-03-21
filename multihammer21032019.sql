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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

insert  into `clientes`(`id_cliente`,`domicilio`,`rfc`,`nombre`,`apellido`,`telefono`) values (1,'Rio Presidio','KELO210298ABA','Público','General','6691238459'),(2,'Lomas de Mazatlan #236','NOCL021198AB5','Noe ','Camacho','6691990132'),(3,'Por el Renacimiento','LICL180297ABA','Lilian Karina','Covarrubias Lizárraga','6691837253'),(4,'Rio Presidio 307-Altos','KELO210298KL7','Kevin','Lizárraga','6691238459'),(5,'- UPSIN','ESVO219382ABA','Esteban Alberto','Valencia Obregón','6691827364'),(7,'SADSAJO123','ESVO219382AB5','Esteban','Valencia','6691828342');

/*Table structure for table `materiales` */

DROP TABLE IF EXISTS `materiales`;

CREATE TABLE `materiales` (
  `id_material` tinyint(4) unsigned NOT NULL AUTO_INCREMENT,
  `material` varchar(50) DEFAULT NULL,
  `descripcion` text,
  PRIMARY KEY (`id_material`),
  UNIQUE KEY `material` (`material`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `materiales` */

insert  into `materiales`(`id_material`,`material`,`descripcion`) values (1,'Piso Rojo','Un piso rojo pasión'),(2,'Varilla','Varilla normal'),(3,'Piso','Un piso más'),(4,'algo','algo'),(13,'prueba','asdsadsa'),(14,'adasda','asdasdsaas'),(15,'iopipo','asdasdass'),(16,'Cemento','Cemento duradero'),(17,'Piso fucsia','wiiiiiiiiiiiiiii');

/*Table structure for table `modulos` */

DROP TABLE IF EXISTS `modulos`;

CREATE TABLE `modulos` (
  `id_modulo` tinyint(2) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `icon` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_modulo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `modulos` */

insert  into `modulos`(`id_modulo`,`nombre`,`estado`,`icon`) values (1,'Productos',1,'VIEW_LIST'),(2,'Reportes',1,'LIBRARY_BOOKS'),(3,'Estadisticas',1,'CHART_LINE'),(4,'Usuarios',1,'ACCOUNT_MULTIPLE'),(5,'Modulos',1,'SETTINGS'),(6,'Proveedores',1,'TRUCK'),(7,'Ventas',1,'CART'),(8,'Clientes',1,'ACCOUNT_CIRCLE'),(9,'Pedidos',1,'CLIPBOARD_TEXT');

/*Table structure for table `pedidos` */

DROP TABLE IF EXISTS `pedidos`;

CREATE TABLE `pedidos` (
  `id_pedido` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) DEFAULT NULL,
  `total` float(8,2) DEFAULT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `estado` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `pedidos` */

insert  into `pedidos`(`id_pedido`,`id_cliente`,`total`,`fecha`,`estado`) values (2,3,1286.20,'2019-03-14 20:25:01',1),(3,2,659.90,'2019-03-14 20:28:56',1),(4,1,2316.20,'2019-03-14 20:36:35',1),(5,2,66715.75,'2019-03-14 20:57:15',1),(6,1,809.90,'2019-03-15 12:42:01',1),(7,4,50125.00,'2019-03-16 16:31:58',1),(8,2,22820.00,'2019-03-16 16:32:54',1),(9,2,6319.80,'2019-03-19 09:13:31',1);

/*Table structure for table `perfiles` */

DROP TABLE IF EXISTS `perfiles`;

CREATE TABLE `perfiles` (
  `id_perfil` tinyint(2) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `perfiles` */

insert  into `perfiles`(`id_perfil`,`nombre`,`descripcion`) values (1,'Administrador','el mero mero'),(2,'Empleado','dependiente');

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Data for the table `productos` */

insert  into `productos`(`id_producto`,`id_material`,`unidad`,`precio`,`id_proveedor`,`cantidad`,`existencia`) values (1,1,'pz',65.99,2,26,1),(2,1,'m²',75.25,2,45,1),(3,1,'pz',70.00,1,23,1),(4,2,'m',10.00,2,170,1),(5,3,'pz',20.00,3,300,0),(6,1,'m²',210.00,1,8,0),(21,15,'m',123.00,2,91,0),(22,16,'Saco 20KG',60.00,1,28,1),(23,17,'pz',500.00,2,600,1);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `proveedores` */

insert  into `proveedores`(`id_proveedor`,`domicilio`,`rfc`,`razon_social`,`estado`,`telefono`) values (1,'Rio Presidio','MELM8305281H0','Provisa',0,'6691238459'),(2,'Mazatlan','UEPS123632KSJ','UPSIN',1,'6692847232'),(3,'Mazatlan','KALO210298AL3','Kuroda',1,'5582918237'),(4,'Castillito Plateado Feliz 123','MEX211198ABA','PisoMEX',0,'6691238472'),(6,'UPSIN','UPSI239281AB4','UPSIN3',1,'6691823723');

/*Table structure for table `registros` */

DROP TABLE IF EXISTS `registros`;

CREATE TABLE `registros` (
  `id_registro` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) unsigned NOT NULL,
  `id_producto` int(11) unsigned NOT NULL,
  `cantidad` smallint(6) unsigned NOT NULL,
  `precio` float(8,2) unsigned NOT NULL,
  PRIMARY KEY (`id_registro`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Data for the table `registros` */

insert  into `registros`(`id_registro`,`id_pedido`,`id_producto`,`cantidad`,`precio`) values (1,2,1,5,65.99),(2,2,2,5,75.25),(3,2,3,2,70.00),(4,2,5,22,20.00),(5,3,1,10,65.99),(6,4,1,5,65.99),(7,4,2,5,75.25),(8,4,3,2,70.00),(9,4,6,7,210.00),(10,5,1,1000,65.99),(11,5,4,50,10.00),(12,5,2,3,75.25),(13,6,1,10,65.99),(14,6,4,15,10.00),(15,7,22,50,50.00),(16,7,5,500,20.00),(17,7,2,500,75.25),(18,8,4,1000,10.00),(19,8,5,641,20.00),(20,9,1,20,65.99),(21,9,4,500,10.00);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`id_usuario`,`id_perfil`,`usuario`,`password`,`estado`) values (1,1,'admin','admin',1),(2,2,'emple','emple',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
