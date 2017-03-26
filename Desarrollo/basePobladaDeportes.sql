/*
SQLyog Community Edition- MySQL GUI v8.05 
MySQL - 5.7.16-log : Database - dbcampeonatos
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`dbcampeonatos` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dbcampeonatos`;

/*Table structure for table `campeonao_auspiciante` */

DROP TABLE IF EXISTS `campeonao_auspiciante`;

CREATE TABLE `campeonao_auspiciante` (
  `CAMP_ID` int(11) NOT NULL,
  `PADE_ID` int(11) DEFAULT NULL,
  `CAAU_MONTO_AUSPICIO` float(8,2) DEFAULT NULL,
  `CAAU_OBSERVACIONES` varchar(255) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`CAMP_ID`),
  KEY `FK_RELATIONSHIP_40` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_19` FOREIGN KEY (`CAMP_ID`) REFERENCES `campeonato` (`CAMP_ID`),
  CONSTRAINT `FK_RELATIONSHIP_40` FOREIGN KEY (`PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `campeonao_auspiciante` */

/*Table structure for table `campeonato` */

DROP TABLE IF EXISTS `campeonato`;

CREATE TABLE `campeonato` (
  `CAMP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PADE_ID` int(11) DEFAULT NULL,
  `SPO_PADE_ID` int(11) DEFAULT NULL,
  `SPO_PADE_ID2` int(11) DEFAULT NULL,
  `CAMP_DESCRIPCION` varchar(64) DEFAULT NULL,
  `CAMP_FECHA_INICIO` datetime DEFAULT NULL,
  `CAMP_FECHA_FIN` datetime DEFAULT NULL,
  `CAMP_NUMERO_EQUIPOS` bigint(20) DEFAULT NULL,
  `CAMP_OBSERVACIONES` varchar(255) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`CAMP_ID`),
  KEY `FK_RELATIONSHIP_31` (`SPO_PADE_ID2`),
  KEY `FK_RELATIONSHIP_38` (`SPO_PADE_ID`),
  KEY `FK_RELATIONSHIP_39` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_31` FOREIGN KEY (`SPO_PADE_ID2`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_38` FOREIGN KEY (`SPO_PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_39` FOREIGN KEY (`PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `campeonato` */

/*Table structure for table `campeonato_equipo` */

DROP TABLE IF EXISTS `campeonato_equipo`;

CREATE TABLE `campeonato_equipo` (
  `CAMP_ID` int(11) NOT NULL,
  `EQUI_ID` int(11) NOT NULL,
  `CAEQ_ESTADO` varchar(32) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`CAMP_ID`),
  KEY `FK_REL_EQUIPO_CAMPEONATO_EQUIPO` (`EQUI_ID`),
  CONSTRAINT `FK_REL_CAMPEONATO_CAMPEONATO_EQUIPO` FOREIGN KEY (`CAMP_ID`) REFERENCES `campeonato` (`CAMP_ID`),
  CONSTRAINT `FK_REL_EQUIPO_CAMPEONATO_EQUIPO` FOREIGN KEY (`EQUI_ID`) REFERENCES `equipo` (`EQUI_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `campeonato_equipo` */

/*Table structure for table `caracteristica` */

DROP TABLE IF EXISTS `caracteristica`;

CREATE TABLE `caracteristica` (
  `CARA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PADE_ID` int(11) DEFAULT NULL,
  `CAMP_ID` int(11) DEFAULT NULL,
  `SPO_PADE_ID` int(11) DEFAULT NULL,
  `CARA_TEXTO` varchar(64) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`CARA_ID`),
  KEY `FK_RELATIONSHIP_13` (`CAMP_ID`),
  KEY `FK_RELATIONSHIP_14` (`SPO_PADE_ID`),
  KEY `FK_RELATIONSHIP_15` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_13` FOREIGN KEY (`CAMP_ID`) REFERENCES `campeonato` (`CAMP_ID`),
  CONSTRAINT `FK_RELATIONSHIP_14` FOREIGN KEY (`SPO_PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_15` FOREIGN KEY (`PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `caracteristica` */

/*Table structure for table `equipo` */

DROP TABLE IF EXISTS `equipo`;

CREATE TABLE `equipo` (
  `EQUI_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USUA_ID` int(11) DEFAULT NULL,
  `EQUI_NOMBRE` varchar(32) DEFAULT NULL,
  `EQUI_DESCRIPCION` varchar(64) DEFAULT NULL,
  `EQUI_FECHA_FUNDACION` datetime DEFAULT NULL,
  `EQUI_PRESIDENTE` varchar(64) DEFAULT NULL,
  `EQUI_MAIL_CONTACTO` varchar(64) DEFAULT NULL,
  `EQUI_DIRECCION` varchar(64) DEFAULT NULL,
  `EQUI_TELEFONO` varchar(32) DEFAULT NULL,
  `EQUI_OBSERVACIONES` varchar(255) DEFAULT NULL,
  `EQUI_ESCUDO` longblob,
  `EQUI_BANDERA` longblob,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`EQUI_ID`),
  KEY `FK_RELATIONSHIP_33` (`USUA_ID`),
  CONSTRAINT `FK_RELATIONSHIP_33` FOREIGN KEY (`USUA_ID`) REFERENCES `usuario` (`USUA_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `equipo` */

insert  into `equipo`(`EQUI_ID`,`USUA_ID`,`EQUI_NOMBRE`,`EQUI_DESCRIPCION`,`EQUI_FECHA_FUNDACION`,`EQUI_PRESIDENTE`,`EQUI_MAIL_CONTACTO`,`EQUI_DIRECCION`,`EQUI_TELEFONO`,`EQUI_OBSERVACIONES`,`EQUI_ESCUDO`,`EQUI_BANDERA`,`AUD_USU_CREA`,`AUD_USU_MODI`,`AUD_FECHA_CREA`,`AUD_FECHA_MODI`) values (1,1,'ballenita','sasas',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `fixture` */

DROP TABLE IF EXISTS `fixture`;

CREATE TABLE `fixture` (
  `FIXT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CAMP_ID` int(11) DEFAULT NULL,
  `PADE_ID` int(11) DEFAULT NULL,
  `SPO_PADE_ID` int(11) DEFAULT NULL,
  `SPO_PADE_ID2` int(11) DEFAULT NULL,
  `SPO_PADE_ID3` int(11) DEFAULT NULL,
  `SPO_PADE_ID4` int(11) DEFAULT NULL,
  `SPO_PADE_ID5` int(11) DEFAULT NULL,
  `SPO_CAMP_ID` int(11) DEFAULT NULL,
  `SPO_CAMP_ID2` int(11) DEFAULT NULL,
  `SPO_CAMP_ID3` int(11) DEFAULT NULL,
  `FIXT_FECHA` datetime DEFAULT NULL,
  `FIXT_HORA` time DEFAULT NULL,
  `FIXT_NUMERO_FECHA` bigint(20) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  `FIXT_NUM_GOLES_GANADOR` bigint(20) DEFAULT NULL,
  `FIXT_NUM_GOLES_PERDEDOR` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`FIXT_ID`),
  KEY `FK_RELATIONSHIP_10` (`CAMP_ID`),
  KEY `FK_RELATIONSHIP_25` (`SPO_PADE_ID5`),
  KEY `FK_RELATIONSHIP_26` (`SPO_CAMP_ID`),
  KEY `FK_RELATIONSHIP_27` (`SPO_CAMP_ID2`),
  KEY `FK_RELATIONSHIP_29` (`SPO_PADE_ID`),
  KEY `FK_RELATIONSHIP_30` (`PADE_ID`),
  KEY `FK_RELATIONSHIP_32` (`SPO_CAMP_ID3`),
  KEY `FK_RELATIONSHIP_35` (`SPO_PADE_ID2`),
  KEY `FK_RELATIONSHIP_36` (`SPO_PADE_ID4`),
  KEY `FK_RELATIONSHIP_37` (`SPO_PADE_ID3`),
  CONSTRAINT `FK_RELATIONSHIP_10` FOREIGN KEY (`CAMP_ID`) REFERENCES `campeonato` (`CAMP_ID`),
  CONSTRAINT `FK_RELATIONSHIP_25` FOREIGN KEY (`SPO_PADE_ID5`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_26` FOREIGN KEY (`SPO_CAMP_ID`) REFERENCES `campeonato_equipo` (`CAMP_ID`),
  CONSTRAINT `FK_RELATIONSHIP_27` FOREIGN KEY (`SPO_CAMP_ID2`) REFERENCES `campeonato_equipo` (`CAMP_ID`),
  CONSTRAINT `FK_RELATIONSHIP_29` FOREIGN KEY (`SPO_PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_30` FOREIGN KEY (`PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_32` FOREIGN KEY (`SPO_CAMP_ID3`) REFERENCES `campeonato_equipo` (`CAMP_ID`),
  CONSTRAINT `FK_RELATIONSHIP_35` FOREIGN KEY (`SPO_PADE_ID2`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_36` FOREIGN KEY (`SPO_PADE_ID4`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_37` FOREIGN KEY (`SPO_PADE_ID3`) REFERENCES `parametro_detalle` (`PADE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `fixture` */

/*Table structure for table `jugador` */

DROP TABLE IF EXISTS `jugador`;

CREATE TABLE `jugador` (
  `JUGA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EQUI_ID` int(11) DEFAULT NULL,
  `JUGA_NOMBRES` varchar(32) DEFAULT NULL,
  `JUGA_APELLIDOS` varchar(32) DEFAULT NULL,
  `JUGA_NUMERO_CEDULA` varchar(32) DEFAULT NULL,
  `JUGA_FOTO` longblob,
  `JUGA_IMAGEN_CEDULA` longblob,
  `JUGA_IMAGEN_CEDULA_REVERSO` longblob,
  `JUGA_MAIL` varchar(32) DEFAULT NULL,
  `JUGA_TELEFONO` varchar(32) DEFAULT NULL,
  `JUGA_LUGAR_NACIMIENTO` varchar(32) DEFAULT NULL,
  `JUGA_FECHA_NACIMIENTO` datetime DEFAULT NULL,
  `JUGA_NACIONALIDAD` varchar(32) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`JUGA_ID`),
  KEY `FK_RELATIONSHIP_16` (`EQUI_ID`),
  CONSTRAINT `FK_RELATIONSHIP_16` FOREIGN KEY (`EQUI_ID`) REFERENCES `equipo` (`EQUI_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `jugador` */

insert  into `jugador`(`JUGA_ID`,`EQUI_ID`,`JUGA_NOMBRES`,`JUGA_APELLIDOS`,`JUGA_NUMERO_CEDULA`,`JUGA_FOTO`,`JUGA_IMAGEN_CEDULA`,`JUGA_IMAGEN_CEDULA_REVERSO`,`JUGA_MAIL`,`JUGA_TELEFONO`,`JUGA_LUGAR_NACIMIENTO`,`JUGA_FECHA_NACIMIENTO`,`JUGA_NACIONALIDAD`,`AUD_USU_MODI`,`AUD_FECHA_CREA`,`AUD_FECHA_MODI`,`AUD_USU_CREA`) values (2,1,'sasa','sasa','',NULL,NULL,NULL,'','','',NULL,'','',NULL,NULL,NULL),(3,1,'wq','wq','',NULL,NULL,NULL,'','','',NULL,'','',NULL,NULL,NULL),(4,1,'david','david','12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,NULL,'david','tayupanta','1234556',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','2019-02-03 00:00:00','2019-02-03 00:00:00','2'),(6,NULL,'xavis','sasasa','1233344444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2','2019-02-03 00:00:00','2019-02-03 00:00:00','2');

/*Table structure for table `parametro` */

DROP TABLE IF EXISTS `parametro`;

CREATE TABLE `parametro` (
  `PARA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PARA_DES_CORTA` varchar(32) DEFAULT NULL,
  `PARA_DES_LARGA` varchar(128) DEFAULT NULL,
  `PARA_NEMONICO` varchar(64) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREAR` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`PARA_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `parametro` */

insert  into `parametro`(`PARA_ID`,`PARA_DES_CORTA`,`PARA_DES_LARGA`,`PARA_NEMONICO`,`AUD_USU_CREA`,`AUD_USU_MODI`,`AUD_FECHA_CREAR`,`AUD_FECHA_MODI`) values (1,'sa','sa','sa','sa','sa','2017-03-27 00:00:00',NULL);

/*Table structure for table `parametro_detalle` */

DROP TABLE IF EXISTS `parametro_detalle`;

CREATE TABLE `parametro_detalle` (
  `PADE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PARA_ID` int(11) DEFAULT NULL,
  `PADE_NEMONICO` varchar(32) DEFAULT NULL,
  `PADE_DES_CORTA` varchar(32) DEFAULT NULL,
  `PADE_DES_LARGA` varchar(32) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`PADE_ID`),
  KEY `FK_REL_PARAMETRO_PARAMETRO_DETALLE` (`PARA_ID`),
  CONSTRAINT `FK_REL_PARAMETRO_PARAMETRO_DETALLE` FOREIGN KEY (`PARA_ID`) REFERENCES `parametro` (`PARA_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `parametro_detalle` */

insert  into `parametro_detalle`(`PADE_ID`,`PARA_ID`,`PADE_NEMONICO`,`PADE_DES_CORTA`,`PADE_DES_LARGA`,`AUD_USU_CREA`,`AUD_USU_MODI`,`AUD_FECHA_CREA`,`AUD_FECHA_MODI`) values (1,1,'sa','sa','sa',NULL,NULL,NULL,NULL);

/*Table structure for table `partido_jugador` */

DROP TABLE IF EXISTS `partido_jugador`;

CREATE TABLE `partido_jugador` (
  `PART_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PADE_ID` int(11) DEFAULT NULL,
  `JUGA_ID` int(11) DEFAULT NULL,
  `FIXT_ID` int(11) DEFAULT NULL,
  `PART_VALOR` bigint(20) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  PRIMARY KEY (`PART_ID`),
  KEY `FK_RELATIONSHIP_28` (`JUGA_ID`),
  KEY `FK_RELATIONSHIP_34` (`PADE_ID`),
  KEY `FK_RELATIONSHIP_9` (`FIXT_ID`),
  CONSTRAINT `FK_RELATIONSHIP_28` FOREIGN KEY (`JUGA_ID`) REFERENCES `jugador` (`JUGA_ID`),
  CONSTRAINT `FK_RELATIONSHIP_34` FOREIGN KEY (`PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_RELATIONSHIP_9` FOREIGN KEY (`FIXT_ID`) REFERENCES `fixture` (`FIXT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `partido_jugador` */

/*Table structure for table `perfil_recurso` */

DROP TABLE IF EXISTS `perfil_recurso`;

CREATE TABLE `perfil_recurso` (
  `PADE_ID` int(11) NOT NULL,
  `SPO_PADE_ID` int(11) NOT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`PADE_ID`,`SPO_PADE_ID`),
  KEY `FK_REL_PERFIL_PERFIL_RECURSO` (`SPO_PADE_ID`),
  CONSTRAINT `FK_REL_PERFIL_PERFIL_RECURSO` FOREIGN KEY (`SPO_PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`),
  CONSTRAINT `FK_REL_RECURSO_PERFIL_RECURSO` FOREIGN KEY (`PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `perfil_recurso` */

/*Table structure for table `requisitos` */

DROP TABLE IF EXISTS `requisitos`;

CREATE TABLE `requisitos` (
  `REQU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CAMP_ID` int(11) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`REQU_ID`),
  KEY `FK_RELATIONSHIP_12` (`CAMP_ID`),
  CONSTRAINT `FK_RELATIONSHIP_12` FOREIGN KEY (`CAMP_ID`) REFERENCES `campeonato_equipo` (`CAMP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `requisitos` */

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `USUA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PADE_ID` int(11) DEFAULT NULL,
  `USUA_LOGIN` varchar(32) DEFAULT NULL,
  `USUA_CONTRASENA` varchar(32) DEFAULT NULL,
  `AUD_USU_CREA` varchar(128) DEFAULT NULL,
  `AUD_USU_MODI` varchar(128) DEFAULT NULL,
  `AUD_FECHA_CREA` datetime DEFAULT NULL,
  `AUD_FECHA_MODI` datetime DEFAULT NULL,
  PRIMARY KEY (`USUA_ID`),
  KEY `FK_REL_PERFIL_USUARIO` (`PADE_ID`),
  CONSTRAINT `FK_REL_PERFIL_USUARIO` FOREIGN KEY (`PADE_ID`) REFERENCES `parametro_detalle` (`PADE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `usuario` */

insert  into `usuario`(`USUA_ID`,`PADE_ID`,`USUA_LOGIN`,`USUA_CONTRASENA`,`AUD_USU_CREA`,`AUD_USU_MODI`,`AUD_FECHA_CREA`,`AUD_FECHA_MODI`) values (1,1,'david','david',NULL,NULL,NULL,NULL),(2,1,'admin','admin',NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
