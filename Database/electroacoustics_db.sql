-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Czas generowania: 05 Maj 2023, 01:20
-- Wersja serwera: 10.4.21-MariaDB
-- Wersja PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `electroacoustics_db`
--
CREATE DATABASE IF NOT EXISTS `electroacoustics_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `electroacoustics_db`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Customers`
--

DROP TABLE IF EXISTS `Customers`;
CREATE TABLE `Customers` (
  `CustomerID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Address` varchar(70) NOT NULL,
  `PhoneNum` varchar(15) NOT NULL,
  `Email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Customers`
--

INSERT INTO `Customers` (`CustomerID`, `Name`, `Address`, `PhoneNum`, `Email`) VALUES
(1, 'Jan Kowalski', 'Rybnik, 44-200, ul. Grottgera Artura 48', '798021542', 'kowalski.jan@gmail.com'),
(2, 'Antoni Nowak', 'Warszawa, 03-616, ul. Remontowa 88', '795404285', 'antoni1967@o2.pl'),
(3, 'Anna Wiśniewska', 'Kraków, 30-001, ul. Floriańska 15', '789123456', 'anna.wisniewska@gmail.com'),
(4, 'Barbara Kaczmarek', 'Poznań, 61-001, ul. Wielka 10', '788765432', 'barbara.kaczmarek@gmail.com'),
(5, 'Marek Lewandowski', 'Wrocław, 50-001, ul. Świdnicka 20', '787654321', 'marek.lewandowski@gmail.com'),
(6, 'Ewa Nowicka', 'Gdańsk, 80-001, ul. Długa 5', '786543210', 'ewa.nowicka@gmail.com'),
(7, 'Piotr Adamczyk', 'Łódź, 90-001, ul. Piotrkowska 30', '785432109', 'piotr.adamczyk@gmail.com'),
(8, 'Katarzyna Szymańska', 'Szczecin, 70-001, ul. Wojska Polskiego 25', '784321098', 'katarzyna.szymanska@gmail.com'),
(9, 'Tomasz Wójcik', 'Bydgoszcz, 85-001, ul. Gdańska 10', '783210987', 'tomasz.wojcik@gmail.com'),
(10, 'Małgorzata Dąbrowska', 'Lublin, 20-001, ul. Krakowskie Przedmieście 5', '782109876', 'malgorzata.dabrowska@gmail.com');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Employees`
--

DROP TABLE IF EXISTS `Employees`;
CREATE TABLE `Employees` (
  `EmployeeID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `PhoneNum` varchar(15) NOT NULL,
  `Email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Employees`
--

INSERT INTO `Employees` (`EmployeeID`, `Name`, `PhoneNum`, `Email`) VALUES
(1, 'Jakub Haraf', '664296357', 'harafjakub@gmail.com'),
(2, 'Aleksander Wojciechowski', '783596235', 'wojciechowski@gmail.com'),
(3, 'Natan Baranowski', '722645310', 'baranowski99@gmail.com');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Installations`
--

DROP TABLE IF EXISTS `Installations`;
CREATE TABLE `Installations` (
  `InstallationID` int(11) NOT NULL,
  `LocationID` int(11) NOT NULL,
  `CustomerID` int(11) NOT NULL,
  `EmployeeID` int(11) NOT NULL,
  `DateStart` date NOT NULL,
  `DateEnd` date DEFAULT NULL,
  `Description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Installations`
--

INSERT INTO `Installations` (`InstallationID`, `LocationID`, `CustomerID`, `EmployeeID`, `DateStart`, `DateEnd`, `Description`) VALUES
(1, 3, 1, 3, '2023-02-01', '2023-02-03', NULL),
(2, 1, 2, 1, '2023-03-15', '2023-03-17', NULL),
(3, 4, 3, 1, '2023-04-20', '2023-04-21', NULL),
(4, 5, 4, 3, '2023-05-09', '2023-05-12', NULL),
(5, 6, 5, 1, '2023-06-18', '2023-06-21', NULL),
(7, 8, 7, 2, '2023-08-08', '2023-08-09', NULL),
(8, 9, 8, 2, '2023-09-17', '2023-09-21', NULL),
(9, 10, 9, 2, '2023-10-05', '2023-10-06', NULL),
(10, 7, 10, 2, '2023-11-12', '2023-11-15', NULL),
(11, 2, 6, 1, '2023-05-03', '2023-05-05', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Invoices`
--

DROP TABLE IF EXISTS `Invoices`;
CREATE TABLE `Invoices` (
  `InvoiceID` int(11) NOT NULL,
  `InstallationID` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `Description` text DEFAULT NULL,
  `Status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Invoices`
--

INSERT INTO `Invoices` (`InvoiceID`, `InstallationID`, `Date`, `Amount`, `Description`, `Status`) VALUES
(1, 1, '2023-05-05', '9600', 'Opis faktury dla Instalacji 1', 'Opłacona'),
(2, 2, '2023-05-05', '10500', 'Opis faktury dla Instalacji 2', 'Nie opłacona'),
(3, 3, '2023-05-05', '22100', 'Opis faktury dla Instalacji 3', 'Opłacona');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Locations`
--

DROP TABLE IF EXISTS `Locations`;
CREATE TABLE `Locations` (
  `LocationID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Address` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Locations`
--

INSERT INTO `Locations` (`LocationID`, `Name`, `Type`, `Address`) VALUES
(1, 'Arena Ursynów', 'Hala sportowa', 'Warszawa, 04-030, Al. Waszyngtona Jerzego 147'),
(2, 'Sanktuarium św. Jan Pawła II', 'Kościół', 'Gdańsk, 80-126, ul. Cedrowa 79'),
(3, 'Bazylika św. Antoniego ', 'Kościół', 'Rybnik, 44-200, ul. Mikołowska 4'),
(4, 'Kościół pw. Świętej Rodziny', 'Kościół', 'Kraków, 30-001, ul. Floriańska 15'),
(5, 'Hala Widowiskowo-Sportowa', 'Hala sportowa', 'Poznań, 61-001, ul. Wielka 10'),
(6, 'Hala Stulecia', 'Hala sportowa', 'Wrocław, 50-001, ul. Świdnicka 20'),
(7, 'Kościół św. Eliasza', 'Kościół', 'Lublin, 20-089, ul. Biernackiego 9A'),
(8, 'Arena Łódź', 'Hala sportowa', 'Łódź, 90-001, ul. Piotrkowska 30'),
(9, 'Hala Netto Arena', 'Hala sportowa', 'Szczecin, 70-001, ul. Wojska Polskiego 25'),
(10, 'Kościół pw. św. Trójcy', 'Kościół', 'Bydgoszcz, 85-001, ul. Gdańska 10');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Orders`
--

DROP TABLE IF EXISTS `Orders`;
CREATE TABLE `Orders` (
  `InstallationID` int(11) NOT NULL,
  `ProductID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Orders`
--

INSERT INTO `Orders` (`InstallationID`, `ProductID`, `Quantity`) VALUES
(1, 1, 2),
(1, 4, 4),
(1, 8, 1),
(1, 9, 1),
(2, 2, 1),
(2, 5, 2),
(3, 3, 1),
(3, 6, 2),
(3, 10, 3),
(4, 1, 2),
(4, 7, 1),
(4, 12, 2),
(5, 3, 1),
(5, 5, 3),
(5, 9, 2),
(7, 2, 2),
(7, 6, 1),
(7, 11, 1),
(7, 13, 1),
(8, 4, 3),
(8, 8, 2),
(9, 5, 2),
(9, 9, 1),
(9, 14, 2),
(10, 2, 1),
(10, 7, 2),
(10, 12, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Products`
--

DROP TABLE IF EXISTS `Products`;
CREATE TABLE `Products` (
  `ProductID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `Manufacture` varchar(50) DEFAULT NULL,
  `Model` varchar(50) DEFAULT NULL,
  `Price` decimal(10,2) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Products`
--

INSERT INTO `Products` (`ProductID`, `Name`, `Type`, `Manufacture`, `Model`, `Price`, `Quantity`) VALUES
(1, 'JBL EON615', 'Głośnik', 'JBL', NULL, '1500', 10),
(2, 'Yamaha DXR12', 'Kolumna', 'Yamaha', NULL, '2500', 5),
(3, 'Behringer X32', 'Wzmacniacz', 'Behringer', NULL, '6000', 2),
(4, 'Shure SM58', 'Mikrofon', 'Shure', NULL, '500', 20),
(5, 'QSC KW181', 'Głośnik', 'QSC', NULL, '3500', 8),
(6, 'Bose L1 Compact', 'Kolumna', 'Bose', NULL, '4000', 3),
(7, 'Crown XLS1502', 'Wzmacniacz', 'Crown', NULL, '2000', 4),
(8, 'Audio-Technica AT2020', 'Mikrofon', 'Audio-Technica', NULL, '800', 15),
(9, 'Mackie Thump15A', 'Głośnik', 'Mackie', NULL, '1800', 6),
(10, 'Electro-Voice ZLX-12P', 'Kolumna', 'Electro-Voice', NULL, '2200', 4),
(11, 'Fender Mustang GT40', 'Wzmacniacz', 'Fender', NULL, '1200', 3),
(12, 'Sennheiser e835', 'Mikrofon', 'Sennheiser', NULL, '600', 12),
(13, 'QSC K12.2', 'Głośnik', 'QSC', NULL, '3200', 7),
(14, 'JBL PRX815W', 'Kolumna', 'JBL', NULL, '2800', 5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Users`
--

DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `Users`
--

INSERT INTO `Users` (`UserID`, `Username`, `Password`) VALUES
(1, 'admin', 'admin'),
(2, 'jakub', 'haraf');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `Customers`
--
ALTER TABLE `Customers`
  ADD PRIMARY KEY (`CustomerID`);

--
-- Indeksy dla tabeli `Employees`
--
ALTER TABLE `Employees`
  ADD PRIMARY KEY (`EmployeeID`);

--
-- Indeksy dla tabeli `Installations`
--
ALTER TABLE `Installations`
  ADD PRIMARY KEY (`InstallationID`),
  ADD KEY `CustomerID` (`CustomerID`),
  ADD KEY `EmployeeID` (`EmployeeID`),
  ADD KEY `LocationID` (`LocationID`);

--
-- Indeksy dla tabeli `Invoices`
--
ALTER TABLE `Invoices`
  ADD PRIMARY KEY (`InvoiceID`),
  ADD KEY `InstallationID` (`InstallationID`);

--
-- Indeksy dla tabeli `Locations`
--
ALTER TABLE `Locations`
  ADD PRIMARY KEY (`LocationID`);

--
-- Indeksy dla tabeli `Orders`
--
ALTER TABLE `Orders`
  ADD KEY `InstallationID` (`InstallationID`),
  ADD KEY `ProductID` (`ProductID`);

--
-- Indeksy dla tabeli `Products`
--
ALTER TABLE `Products`
  ADD PRIMARY KEY (`ProductID`);

--
-- Indeksy dla tabeli `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `Customers`
--
ALTER TABLE `Customers`
  MODIFY `CustomerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT dla tabeli `Employees`
--
ALTER TABLE `Employees`
  MODIFY `EmployeeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `Installations`
--
ALTER TABLE `Installations`
  MODIFY `InstallationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT dla tabeli `Invoices`
--
ALTER TABLE `Invoices`
  MODIFY `InvoiceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `Locations`
--
ALTER TABLE `Locations`
  MODIFY `LocationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `Products`
--
ALTER TABLE `Products`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT dla tabeli `Users`
--
ALTER TABLE `Users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `Installations`
--
ALTER TABLE `Installations`
  ADD CONSTRAINT `installations_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `Customers` (`CustomerID`),
  ADD CONSTRAINT `installations_ibfk_2` FOREIGN KEY (`EmployeeID`) REFERENCES `Employees` (`EmployeeID`),
  ADD CONSTRAINT `installations_ibfk_3` FOREIGN KEY (`LocationID`) REFERENCES `Locations` (`LocationID`);

--
-- Ograniczenia dla tabeli `Invoices`
--
ALTER TABLE `Invoices`
  ADD CONSTRAINT `invoices_ibfk_1` FOREIGN KEY (`InstallationID`) REFERENCES `Installations` (`InstallationID`);

--
-- Ograniczenia dla tabeli `Orders`
--
ALTER TABLE `Orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`InstallationID`) REFERENCES `Installations` (`InstallationID`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `Products` (`ProductID`);



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
