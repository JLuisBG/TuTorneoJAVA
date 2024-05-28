-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-05-2024 a las 20:17:47
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tutorneo`
--

DELIMITER $$
--
-- Funciones
--
CREATE DEFINER=`root`@`localhost` FUNCTION `CheckAdminExists` (`email` VARCHAR(50), `pass` VARCHAR(256)) RETURNS TINYINT(1)  BEGIN
    DECLARE adminExists BOOLEAN;

    SELECT COUNT(*)
    INTO adminExists
    FROM admin
    WHERE admin.email = email AND admin.pass = pass;

    RETURN adminExists > 0;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `CheckUserExists` (`email` VARCHAR(50), `pass` VARCHAR(256)) RETURNS TINYINT(1)  BEGIN
    DECLARE userExists BOOLEAN;

    SELECT COUNT(*)
    INTO userExists
    FROM players
    WHERE players.email = email AND players.pass = pass;

    RETURN userExists > 0;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `pass` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `admin`
--

INSERT INTO `admin` (`id`, `email`, `pass`) VALUES
(2, 'admin@admin.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `players`
--

CREATE TABLE `players` (
  `id` int(11) NOT NULL,
  `pass` varchar(256) NOT NULL,
  `email` varchar(50) NOT NULL,
  `isentryfeepaid` tinyint(1) NOT NULL,
  `birthdate` date NOT NULL,
  `telephoneno` int(9) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `playerteam`
--

CREATE TABLE `playerteam` (
  `id` int(11) NOT NULL,
  `playerid` int(11) NOT NULL,
  `teamid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prize`
--

CREATE TABLE `prize` (
  `id` int(11) NOT NULL,
  `prizenumber` int(11) DEFAULT NULL,
  `prizename` varchar(50) DEFAULT NULL,
  `prizeamount` float DEFAULT NULL,
  `prizepercentage` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `team`
--

CREATE TABLE `team` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `logo` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `teamtournament`
--

CREATE TABLE `teamtournament` (
  `id` int(11) NOT NULL,
  `tournamentid` int(11) DEFAULT NULL,
  `teamid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tournament`
--

CREATE TABLE `tournament` (
  `id` int(11) NOT NULL,
  `tournamentname` varchar(50) NOT NULL,
  `entryfee` float NOT NULL,
  `prizeid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `playerteam`
--
ALTER TABLE `playerteam`
  ADD PRIMARY KEY (`id`),
  ADD KEY `playerid` (`playerid`),
  ADD KEY `teamid` (`teamid`);

--
-- Indices de la tabla `prize`
--
ALTER TABLE `prize`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `teamtournament`
--
ALTER TABLE `teamtournament`
  ADD PRIMARY KEY (`id`),
  ADD KEY `teamid` (`teamid`),
  ADD KEY `tournamentid` (`tournamentid`);

--
-- Indices de la tabla `tournament`
--
ALTER TABLE `tournament`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `players`
--
ALTER TABLE `players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `playerteam`
--
ALTER TABLE `playerteam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `prize`
--
ALTER TABLE `prize`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `team`
--
ALTER TABLE `team`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `teamtournament`
--
ALTER TABLE `teamtournament`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tournament`
--
ALTER TABLE `tournament`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `playerteam`
--
ALTER TABLE `playerteam`
  ADD CONSTRAINT `playerteam_ibfk_1` FOREIGN KEY (`playerid`) REFERENCES `players` (`id`),
  ADD CONSTRAINT `playerteam_ibfk_2` FOREIGN KEY (`teamid`) REFERENCES `team` (`id`);

--
-- Filtros para la tabla `teamtournament`
--
ALTER TABLE `teamtournament`
  ADD CONSTRAINT `teamtournament_ibfk_1` FOREIGN KEY (`teamid`) REFERENCES `team` (`id`),
  ADD CONSTRAINT `teamtournament_ibfk_2` FOREIGN KEY (`tournamentid`) REFERENCES `tournament` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;