-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 30, 2016 at 11:02 PM
-- Server version: 5.6.28
-- PHP Version: 7.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ParkSafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `Bookings`
--

CREATE TABLE `Bookings` (
  `booking_reference` varchar(10) NOT NULL,
  `booking_time` datetime NOT NULL,
  `location_id` varchar(10) NOT NULL,
  `location_name` varchar(150) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `user` varchar(50) NOT NULL,
  `paid_till` date NOT NULL,
  `due_date` date NOT NULL,
  `pending_amount` int(11) NOT NULL,
  `booking_status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Bookings`
--

INSERT INTO `Bookings` (`booking_reference`, `booking_time`, `location_id`, `location_name`, `start_date`, `end_date`, `user`, `paid_till`, `due_date`, `pending_amount`, `booking_status`) VALUES
('BK0', '2016-10-20 11:16:53', 'LC1', '128 Leicester Street, Carlton - VIC 3053', '2016-10-27', '2016-12-31', 'admin', '2016-10-31', '2016-10-28', 0, 'Active'),
('BK1', '2016-10-20 11:19:26', 'LC2', '105 Villiers Street', '2016-10-22', '2016-12-31', 'admin', '2016-10-31', '2016-10-28', 0, 'Active'),
('BK2', '2016-10-28 06:47:39', 'LC1', '128 Leicester Street, Calton', '2016-11-01', '2016-12-14', 'admin', '2016-11-30', '2016-11-28', 0, 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `parkingLocations`
--

CREATE TABLE `parkingLocations` (
  `uid` varchar(12) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `rent` int(11) NOT NULL,
  `available_space` int(11) NOT NULL,
  `total_space` int(11) NOT NULL,
  `address` varchar(150) NOT NULL,
  `available_from` date NOT NULL,
  `available_till` date NOT NULL,
  `special` varchar(500) NOT NULL,
  `short_address` varchar(100) NOT NULL,
  `owner` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `parkingLocations`
--

INSERT INTO `parkingLocations` (`uid`, `lat`, `lng`, `rent`, `available_space`, `total_space`, `address`, `available_from`, `available_till`, `special`, `short_address`, `owner`) VALUES
('LC1', -37.78808138412046, 144.931640625, 300, 10, 20, '128 Leicester Street, Carlton, VIC - 3053', '2016-10-10', '2016-12-31', 'NA', '128 Leicester Street, Carlton', 'admin'),
('LC2', -37.16031654673676, 145.546875, 300, 2, 15, ' 105 Villiers Street, North Melbourne, VIC - 3051', '2016-10-10', '2017-06-30', 'NA', '105 Villiers Street, North Melbourne', 'admin'),
('LC3', -37.92629476639558, 145.94921875, 250, 5, 6, '121 Riley Street, Preston - 3092', '2016-10-10', '2017-02-28', 'NA', '121 Riley Street', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `Payment`
--

CREATE TABLE `Payment` (
  `payment_id` varchar(12) NOT NULL,
  `amount` int(11) NOT NULL,
  `booking_reference` varchar(10) NOT NULL,
  `location_reference` varchar(12) NOT NULL,
  `location_name` varchar(100) NOT NULL,
  `time` datetime NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `user` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Payment`
--

INSERT INTO `Payment` (`payment_id`, `amount`, `booking_reference`, `location_reference`, `location_name`, `time`, `start_date`, `end_date`, `user`) VALUES
('PY1', 400, 'BK1', 'LK1', '128 Leicester Street', '2016-10-20 14:27:12', '2016-10-21', '2016-12-31', 'admin'),
('PY2', 300, 'BK1', 'LK1', '128 Leicester Street', '2016-10-29 17:35:31', '2016-12-01', '2016-10-31', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Bookings`
--
ALTER TABLE `Bookings`
  ADD PRIMARY KEY (`booking_reference`);

--
-- Indexes for table `parkingLocations`
--
ALTER TABLE `parkingLocations`
  ADD PRIMARY KEY (`uid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
