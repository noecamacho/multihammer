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
  `id_perfil` tinyint(2) NOT NULL,
  `id_modulo` tinyint(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `acceso` */

insert  into `acceso`(`id_perfil`,`id_modulo`) values (1,1),(1,2),(1,3),(1,4),(2,1);

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `domicilio` varchar(100) DEFAULT NULL,
  `rfc` varchar(13) DEFAULT NULL,
  `razon_social` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

/*Table structure for table `materiales` */

DROP TABLE IF EXISTS `materiales`;

CREATE TABLE `materiales` (
  `id_material` tinyint(4) NOT NULL AUTO_INCREMENT,
  `material` varchar(50) DEFAULT NULL,
  `existencia` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_material`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `materiales` */

/*Table structure for table `modulos` */

DROP TABLE IF EXISTS `modulos`;

CREATE TABLE `modulos` (
  `id_modulo` tinyint(2) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_modulo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `modulos` */

insert  into `modulos`(`id_modulo`,`nombre`,`estado`) values (1,'Productos',1),(2,'Reportes',1),(3,'Estadisticas',1),(4,'Usuarios',1);

/*Table structure for table `pedidos` */

DROP TABLE IF EXISTS `pedidos`;

CREATE TABLE `pedidos` (
  `id_pedido` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `estado` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `pedidos` */

/*Table structure for table `perfiles` */

DROP TABLE IF EXISTS `perfiles`;

CREATE TABLE `perfiles` (
  `id_perfil` tinyint(2) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `descripcion` varchar(50) DEFAULT NULL,
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
  `precio` double DEFAULT NULL,
  `id_proveedor` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `productos` */

/*Table structure for table `proveedores` */

DROP TABLE IF EXISTS `proveedores`;

CREATE TABLE `proveedores` (
  `id_proveedor` tinyint(2) NOT NULL AUTO_INCREMENT,
  `domicilio` varchar(100) NOT NULL,
  `rfc` varchar(13) NOT NULL,
  `razon_social` varchar(50) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `proveedores` */

/*Table structure for table `registros` */

DROP TABLE IF EXISTS `registros`;

CREATE TABLE `registros` (
  `id_registro` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) unsigned NOT NULL,
  `id_producto` int(11) unsigned NOT NULL,
  `cantidad` int(6) unsigned NOT NULL,
  `precio` double unsigned NOT NULL,
  PRIMARY KEY (`id_registro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `registros` */

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `id_usuario` tinyint(4) NOT NULL AUTO_INCREMENT,
  `id_perfil` tinyint(4) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`id_usuario`,`id_perfil`,`usuario`,`password`,`estado`) values (1,1,'admin','admin',1),(2,2,'emple','emple',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
