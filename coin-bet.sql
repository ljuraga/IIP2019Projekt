-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 17, 2019 at 10:38 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `coin-bet`
--
CREATE DATABASE IF NOT EXISTS `coin-bet` DEFAULT CHARACTER SET utf8 COLLATE utf8_croatian_ci;
USE `coin-bet`;

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `Id` bigint(20) NOT NULL,
  `User` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `GameDate` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `GameTime` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `Game` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `League` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `OddCategory` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `OddValue` float DEFAULT NULL,
  `OddMultiplayer` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE `games` (
  `Id` bigint(20) NOT NULL,
  `Date` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `Time` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `Game` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `League` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `OddHome` float DEFAULT NULL,
  `OddDraw` float DEFAULT NULL,
  `OddAway` float DEFAULT NULL,
  `ResultHome` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `ResultDraw` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `ResultAway` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`Id`, `Date`, `Time`, `Game`, `League`, `OddHome`, `OddDraw`, `OddAway`, `ResultHome`, `ResultDraw`, `ResultAway`) VALUES
(1, '2018/11/27', '18:55:00', 'AEK Athens vs Ajax', 'UEFA Champions League', 4.5, 3.75, 1.75, 'X', NULL, NULL),
(2, '2018/11/27', '18:55:00', 'CSKA Moscow vs Viktoria Plzen', 'UEFA Champions League', 1.87, 3.4, 4.33, NULL, 'X', NULL),
(3, '2018/11/27', '21:00:00', 'Bayern Munich vs Benfica', 'UEFA Champions League', 1.25, 5.75, 11, NULL, NULL, 'X'),
(4, '2018/11/27', '21:00:00', 'Hoffenheim vs Shakhtar Donetsk', 'UEFA Champions League', 1.87, 4, 3.8, 'X', NULL, NULL),
(5, '2018/11/27', '21:00:00', 'Juventus vs Valencia', 'UEFA Champions League', 1.4, 4.75, 7.5, NULL, 'X', NULL),
(6, '2018/11/27', '21:00:00', 'Lyon vs Manchester City', 'UEFA Champions League', 6, 5.25, 1.42, NULL, NULL, 'X'),
(7, '2018/11/27', '21:00:00', 'Manchester Utd vs BSC Young Boys Bern', 'UEFA Champions League', 1.25, 5.75, 11, 'X', NULL, NULL),
(8, '2018/11/27', '21:00:00', 'Roma vs Real Madrid', 'UEFA Champions League', 3.8, 3.8, 1.87, NULL, 'X', NULL),
(9, '2018/11/27', '18:55:00', 'AEK Athens vs Ajax', 'English Premier League', 4.5, 3.75, 1.75, NULL, NULL, 'X'),
(10, '2018/11/27', '18:55:00', 'CSKA Moscow vs Viktoria Plzen', 'English Premier League', 1.87, 3.4, 4.33, 'X', NULL, NULL),
(11, '2018/11/27', '21:00:00', 'Bayern Munich vs Benfica', 'English Premier League', 1.25, 5.75, 11, NULL, 'X', NULL),
(12, '2018/11/27', '21:00:00', 'Hoffenheim vs Shakhtar Donetsk', 'English Premier League', 1.87, 4, 3.8, NULL, NULL, 'X'),
(13, '2018/11/27', '21:00:00', 'Juventus vs Valencia', 'English Premier League', 1.4, 4.75, 7.5, 'X', NULL, NULL),
(14, '2018/11/27', '21:00:00', 'Lyon vs Manchester City', 'English Premier League', 6, 5.25, 1.42, NULL, 'X', NULL),
(15, '2018/11/27', '21:00:00', 'Manchester Utd vs BSC Young Boys Bern', 'English Premier League', 1.25, 5.75, 11, NULL, NULL, 'X'),
(16, '2018/11/27', '21:00:00', 'Roma vs Real Madrid', 'English Premier League', 3.8, 3.8, 1.87, 'X', NULL, NULL),
(19, '2018/11/28', '21:00:00', 'Borussia Dortmund vs Club Brugge', 'UEFA Champions League', 1.33, 5.25, 8.5, NULL, NULL, NULL),
(20, '2018/11/28', '21:00:00', 'Napoli vs FK Crvena Zvezda', 'UEFA Champions League', 1.11, 9, 23, NULL, NULL, NULL),
(21, '2018/11/28', '21:00:00', 'Porto vs Schalke', 'UEFA Champions League', 1.8, 3.4, 4.75, NULL, NULL, NULL),
(22, '2018/11/28', '21:00:00', 'PSG vs Liverpool', 'UEFA Champions League', 2.3, 3.8, 2.87, NULL, NULL, NULL),
(23, '2018/11/28', '21:00:00', 'PSV vs Barcelona', 'UEFA Champions League', 7, 5, 1.4, NULL, NULL, NULL),
(24, '2018/11/28', '21:00:00', 'Tottenham vs Inter Milan', 'UEFA Champions League', 1.87, 3.8, 4, NULL, NULL, NULL),
(25, '2018/11/28', '18:55:00', 'Anetico Madrid vs Monaco', 'English Premier League', 1.18, 7, 19, NULL, NULL, NULL),
(26, '2018/11/28', '18:55:00', 'Lolkomotiv Moscow vs Galatasaray', 'English Premier League', 2.8, 3.1, 2.8, NULL, NULL, NULL),
(27, '2018/11/28', '21:00:00', 'Borussia Dortmund vs Club Brugge', 'English Premier League', 1.33, 5.25, 8.5, NULL, NULL, NULL),
(28, '2018/11/28', '21:00:00', 'Napoli vs FK Crvena Zvezda', 'English Premier League', 1.11, 9, 23, NULL, NULL, NULL),
(29, '2018/11/28', '21:00:00', 'Porto vs Schalke', 'English Premier League', 1.8, 3.4, 4.75, NULL, NULL, NULL),
(30, '2018/11/28', '21:00:00', 'PSG vs Liverpool', 'English Premier League', 2.3, 3.8, 2.87, NULL, NULL, NULL),
(31, '2018/11/28', '21:00:00', 'PSV vs Barcelona', 'English Premier League', 7, 5, 1.4, NULL, NULL, NULL),
(32, '2018/11/28', '21:00:00', 'Tottenham vs Inter Milan', 'English Premier League', 1.87, 3.8, 4, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `Id` bigint(20) NOT NULL,
  `User` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `TransactionDate` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `TransactionTime` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `GameDate` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `GameTime` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `Game` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `League` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `OddCategory` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `OddValue` float DEFAULT NULL,
  `OddMultiplayer` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `Result` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `Paid` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Id` bigint(20) NOT NULL,
  `User` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `Password` varchar(150) COLLATE utf8_croatian_ci DEFAULT NULL,
  `Balance` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Id`, `User`, `Password`, `Balance`) VALUES
(1, 'cd977ba5c59f38986c37321156e025fc89b970c1047c6a00588556238a6dc2dd1d85d4e9313307e59a602971627107adcc0ad2e527002101e0941d3595519bc6', 'c125ef3309b38e7fa5057acb35cbd276afb4a6d763ff0d7e070a3c01a59ec0ff4871e6d454b3c73cfddf895f78a18cf2555942ae5aa592e5502207c65e171105', 0),
(2, 'dcd05977cd321d1baa4f126759fb5e5c4d854815d44fae0be5b9731c435d86022408ebfaa2918d277bfd061286ca8f3726d6cbf836325449f3696a866a48b69a', '2782123ada18f195477cf991ddce179d3237607b24a5e22a002343c96542628bf5cb479c3621a4d23cc8499957098033f084c898848392d2c4109c97ef255fc4', 0),
(3, 'e5835dec2949fce1a3bfc7e483d09d1a4430841a5f526c0f1cbb6b69f793911364feae2981fbf19d41750e5f3a0e8b2af5d1a6b6e16cb2c2866eef85b0c5e048', 'e5835dec2949fce1a3bfc7e483d09d1a4430841a5f526c0f1cbb6b69f793911364feae2981fbf19d41750e5f3a0e8b2af5d1a6b6e16cb2c2866eef85b0c5e048', 100000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `games`
--
ALTER TABLE `games`
  MODIFY `Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `Id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
