-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 02. Jul 2021 um 10:02
-- Server-Version: 10.4.19-MariaDB
-- PHP-Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `animalshelter`
--
CREATE DATABASE IF NOT EXISTS `animalshelter` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `animalshelter`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `cat`
--

CREATE TABLE `cat` (
  `cat_id` int(11) NOT NULL,
  `cat_name` varchar(50) NOT NULL,
  `cat_date_of_birth` varchar(20) DEFAULT NULL,
  `cat_sex` varchar(20) NOT NULL,
  `cat_is_neutered` tinyint(1) DEFAULT NULL,
  `cat_breed` varchar(50) DEFAULT NULL,
  `cat_colour` varchar(50) DEFAULT NULL,
  `cat_admission_date` varchar(20) NOT NULL,
  `cat_adoption_date` varchar(20) DEFAULT NULL,
  `cat_description` text DEFAULT NULL,
  `cat_is_outdoor_cat` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `cat`
--

INSERT INTO `cat` (`cat_id`, `cat_name`, `cat_date_of_birth`, `cat_sex`, `cat_is_neutered`, `cat_breed`, `cat_colour`, `cat_admission_date`, `cat_adoption_date`, `cat_description`, `cat_is_outdoor_cat`) VALUES
(1, 'Kitty', '04.05.2019', 'weiblich', 1, 'EKH', 'rot getigert', '29.06.2020', '', 'Kitty ist eine sehr liebe und zutrauliche Katze. Versteht sich gut mit anderen Katzen.', 0),
(2, 'Carlo', '', 'männlich', 0, 'EKH', 'grau getigert', '29.06.2021', '', 'Carlo wurde an einer Futterstelle aufgegriffen. Er ist noch sehr scheu und braucht viel Freiraum.', 1),
(4, 'Bruno', '08.05.2020', 'männlich', 1, 'EKH', 'schwarz', '30.06.2021', '', 'Bruno ist ein ganz ruhiger Stubentiger und sucht ein entspanntes und liebevolles Zuhause.', 0),
(5, 'Minnie', '04.02.2016', 'weiblich', 1, 'EKH', 'schwarz/weiß', '01.02.2021', '', 'Minnie möchte gerne mit Nicky zusammen bleiben. Die beiden werden nur zusammen abgegeben.', 1),
(7, 'Nicky', '13.09.2017', 'weiblich', 1, 'EKH', 'grau getigert', '06.09.2020', '', 'Nicky möchte gerne mit Minnie zusammen bleiben. Die beiden werden nur zusammen abgegeben.', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `dog`
--

CREATE TABLE `dog` (
  `dog_id` int(11) NOT NULL,
  `dog_name` varchar(50) NOT NULL,
  `dog_date_of_birth` varchar(20) DEFAULT NULL,
  `dog_sex` varchar(20) NOT NULL,
  `dog_is_neutered` tinyint(1) DEFAULT NULL,
  `dog_breed` varchar(50) DEFAULT NULL,
  `dog_colour` varchar(50) DEFAULT NULL,
  `dog_admission_date` varchar(20) NOT NULL,
  `dog_adoption_date` varchar(20) DEFAULT NULL,
  `dog_description` text DEFAULT NULL,
  `dog_is_regulated_dog_breed` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `dog`
--

INSERT INTO `dog` (`dog_id`, `dog_name`, `dog_date_of_birth`, `dog_sex`, `dog_is_neutered`, `dog_breed`, `dog_colour`, `dog_admission_date`, `dog_adoption_date`, `dog_description`, `dog_is_regulated_dog_breed`) VALUES
(2, 'Bolle', '08.04.2019', 'männlich', 1, 'American Staffordshire Terrier', 'grau', '10.02.2021', '', 'Bolle ist ein sehr anspruchsvoller Hund und kann nur an erfahrene Halter mit rassespezifischen Vorkenntnissen und Sachkundebescheinigung nach LHundG NRW abgegeben werden.', 1),
(3, 'Gina', '11.05.2020', 'weiblich', 1, 'Bernersennen/Schäferhund Mischling', 'schwarz/brau', '03.06.2021', '', 'Gina ist noch jung und muss noch viel lernen, aber sie hat sich bei uns schon toll entwickelt und ist wirklich eine ganz liebe.', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rabbit`
--

CREATE TABLE `rabbit` (
  `rabbit_id` int(11) NOT NULL,
  `rabbit_name` varchar(50) NOT NULL,
  `rabbit_date_of_birth` varchar(20) DEFAULT NULL,
  `rabbit_sex` varchar(20) NOT NULL,
  `rabbit_is_neutered` tinyint(1) DEFAULT NULL,
  `rabbit_breed` varchar(50) DEFAULT NULL,
  `rabbit_colour` varchar(50) DEFAULT NULL,
  `rabbit_admission_date` varchar(20) NOT NULL,
  `rabbit_adoption_date` varchar(20) DEFAULT NULL,
  `rabbit_description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `rabbit`
--

INSERT INTO `rabbit` (`rabbit_id`, `rabbit_name`, `rabbit_date_of_birth`, `rabbit_sex`, `rabbit_is_neutered`, `rabbit_breed`, `rabbit_colour`, `rabbit_admission_date`, `rabbit_adoption_date`, `rabbit_description`) VALUES
(1, 'Hanni', '', 'weiblich', 0, 'Löwenkopfkaninchen', 'weiß', '06.11.2020', '', 'Hanni und Nanni sind gemeinsam zu uns gekommen und möchten auch gerne wieder zusammen in ein neues Zuhause ziehen.'),
(2, 'Nanni', '', 'weiblich', 0, 'Löwenkopfkaninchen', 'braun', '06.11.2020', '', 'Hanni und Nanni sind gemeinsam zu uns gekommen und möchten auch gerne wieder zusammen in ein neues Zuhause ziehen.');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `cat`
--
ALTER TABLE `cat`
  ADD PRIMARY KEY (`cat_id`);

--
-- Indizes für die Tabelle `dog`
--
ALTER TABLE `dog`
  ADD PRIMARY KEY (`dog_id`);

--
-- Indizes für die Tabelle `rabbit`
--
ALTER TABLE `rabbit`
  ADD PRIMARY KEY (`rabbit_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `cat`
--
ALTER TABLE `cat`
  MODIFY `cat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT für Tabelle `dog`
--
ALTER TABLE `dog`
  MODIFY `dog_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `rabbit`
--
ALTER TABLE `rabbit`
  MODIFY `rabbit_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
