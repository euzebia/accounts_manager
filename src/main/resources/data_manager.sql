-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 03, 2023 at 04:04 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `data_manager`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetSystemUserDetails` (IN `vendorUserName` VARCHAR(100), IN `vendorPassword` VARCHAR(100))   begin
select * from Vw_AllUsers where user_name =vendorUserName and password=vendorPassword ;
end$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `saveBankStaffDetails` (`firstName` VARCHAR(100), `lastName` VARCHAR(100), `emailAddress` VARCHAR(100), `bankStaffUserName` VARCHAR(100), `bankStaffPassword` VARCHAR(100), `roleId` INT, `branchId` INT, `createdBy` INT) RETURNS VARCHAR(200) CHARSET latin1 DETERMINISTIC BEGIN
    DECLARE counter integer;
    DECLARE DuplicateUserNameExists integer;
    declare newlyCreatedUserId integer;
   
    select COUNT(account_id) into DuplicateUserNameExists from account a  where user_name  = bankStaffUserName;
    IF DuplicateUserNameExists > 0 THEN
       return ('DUPLICATE USERNAME');
	
    ELSE
        select COUNT(account_id) into counter from account a  where email_address  = emailAddress;
        IF counter >0 THEN
          return ('DUPLICATE EMAIL');
        END IF;
	    insert into account (first_name,last_name,user_name,password,email_address)
	    values(firstName,lastName,bankStaffUserName,bankStaffPassword,emailAddress);
	      
        select account_id into newlyCreatedUserId from account a  where email_address  = emailAddress;
        IF(newlyCreatedUserId > 0) then
         insert into bank_staff(account_id,branch_id,vendor_id,role_id)
                        values(newlyCreatedUserId,branchId,createdBy,roleId);
              return ('SUCCESS');
        END IF;
    END IF;
   #END IF;
   END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email_address` varchar(100) DEFAULT NULL,
  `creation_date` datetime DEFAULT current_timestamp(),
  `password_reset_required` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`account_id`, `first_name`, `last_name`, `user_name`, `password`, `email_address`, `creation_date`, `password_reset_required`) VALUES
(1, 'Test', 'Super User', 'admin', 'SMPwNSich95pyLgQszlQtcyZThKfaL61SplfAtIO5rM9AUPox38JNeWWmCmJbRwa0wnRxmFlJLWSUqmRwAYlJQ==', 'testadmin@gmail.com', '2023-07-31 18:22:31', 1),
(2, 'Joyce', 'Nansubuga', 'jnansubuga', 'SMPwNSich95pyLgQszlQtcyZThKfaL61SplfAtIO5rM9AUPox38JNeWWmCmJbRwa0wnRxmFlJLWSUqmRwAYlJQ==', 'jnansubuga@gmail.com', '2023-07-31 23:15:15', 1),
(3, 'John', 'Doe', 'jDoe', 'SMPwNSich95pyLgQszlQtcyZThKfaL61SplfAtIO5rM9AUPox38JNeWWmCmJbRwa0wnRxmFlJLWSUqmRwAYlJQ==', 'jdoe@gmail.com', '2023-07-31 23:16:13', 1),
(4, 'Test', 'Personal Banker', 'test', 'SMPwNSich95pyLgQszlQtcyZThKfaL61SplfAtIO5rM9AUPox38JNeWWmCmJbRwa0wnRxmFlJLWSUqmRwAYlJQ==', 'testpersonalbanker@gmail.com', '2023-08-01 11:27:01', 1),
(5, 'Test', 'Personal Banker', 'test2', 'SMPwNSich95pyLgQszlQtcyZThKfaL61SplfAtIO5rM9AUPox38JNeWWmCmJbRwa0wnRxmFlJLWSUqmRwAYlJQ==', 'testbanker2023@gmail.com', '2023-08-01 11:32:08', 1),
(6, 'Test', 'Personal Banker', 'test1', 'SMPwNSich95pyLgQszlQtcyZThKfaL61SplfAtIO5rM9AUPox38JNeWWmCmJbRwa0wnRxmFlJLWSUqmRwAYlJQ==', 'joyce.euzebia@gmail.com', '2023-08-01 13:47:38', 1),
(15, 'Test', 'Personal Banker', 'test111', 'SMPwNSich95pyLgQszlQtcyZThKfaL61SplfAtIO5rM9AUPox38JNeWWmCmJbRwa0wnRxmFlJLWSUqmRwAYlJQ==', 'joyc66@gmail.com', '2023-08-01 14:32:48', 1);

-- --------------------------------------------------------

--
-- Table structure for table `bank_staff`
--

CREATE TABLE `bank_staff` (
  `bank_staff_id` int(11) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `vendor_id` int(11) DEFAULT NULL,
  `is_password_reseton` tinyint(1) DEFAULT 0,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bank_staff`
--

INSERT INTO `bank_staff` (`bank_staff_id`, `account_id`, `address`, `branch_id`, `vendor_id`, `is_password_reseton`, `role_id`) VALUES
(2, 2, NULL, 1, 1, 0, 2),
(3, 4, NULL, 1, 1, 0, 2),
(4, 5, NULL, 1, 1, 0, 2),
(5, 6, NULL, 1, 1, 0, 2),
(6, 15, NULL, 1, 1, 0, 2);

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `branch_id` int(11) NOT NULL,
  `branch_code` varchar(100) DEFAULT NULL,
  `branch_name` varchar(100) DEFAULT NULL,
  `institution_id` int(11) DEFAULT NULL,
  `creation_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`branch_id`, `branch_code`, `branch_name`, `institution_id`, `creation_date`) VALUES
(1, 'MAIN', 'Head office', 1, '2023-07-31 23:28:39');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `customer_type` enum('corporate','ordinary') DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `creation_date` datetime DEFAULT current_timestamp(),
  `last_updated_on` datetime DEFAULT NULL,
  `last_updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `customer_type`, `role_id`, `account_id`, `creation_date`, `last_updated_on`, `last_updated_by`) VALUES
(1, 'corporate', 3, 3, '2023-07-31 23:38:47', NULL, 2);

-- --------------------------------------------------------

--
-- Table structure for table `institution`
--

CREATE TABLE `institution` (
  `institution_id` int(11) NOT NULL,
  `institution_name` varchar(100) DEFAULT NULL,
  `institution_type` varchar(100) DEFAULT NULL,
  `creation_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `institution`
--

INSERT INTO `institution` (`institution_id`, `institution_name`, `institution_type`, `creation_date`) VALUES
(1, 'Stanbic Bank ', 'Bank', '2023-07-31 08:08:19'),
(2, 'R&C software solutions Ltd', 'Third party software vendor', '2023-07-31 08:10:08');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `role_code` varchar(100) DEFAULT NULL,
  `institution_id` int(11) DEFAULT NULL,
  `creation_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role_name`, `role_code`, `institution_id`, `creation_date`) VALUES
(1, 'Administrator', 'ADMIN', 2, '2023-07-31 08:14:35'),
(2, 'Account manager', 'ACC MNGR', 1, '2023-07-31 08:16:46'),
(3, 'Bank Customer', 'CUSTOMER', 1, '2023-07-31 18:26:43');

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `vendor_id` int(11) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `creation_date` datetime DEFAULT current_timestamp(),
  `role_id` int(11) DEFAULT NULL,
  `institution_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vendor`
--

INSERT INTO `vendor` (`vendor_id`, `account_id`, `creation_date`, `role_id`, `institution_id`) VALUES
(1, 1, '2023-07-31 18:29:52', 1, 2);

-- --------------------------------------------------------

--
-- Stand-in structure for view `vw_allusers`
-- (See below for the actual view)
--
CREATE TABLE `vw_allusers` (
`account_id` int(11)
,`first_name` varchar(100)
,`last_name` varchar(100)
,`email_address` varchar(100)
,`password` varchar(100)
,`user_name` varchar(100)
,`creation_date` datetime
,`institution_id` int(11)
,`institution_name` varchar(100)
,`institution_type` varchar(100)
,`role_id` int(11)
,`role_name` varchar(100)
,`role_code` varchar(100)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `vw_bankstaff`
-- (See below for the actual view)
--
CREATE TABLE `vw_bankstaff` (
`account_id` int(11)
,`first_name` varchar(100)
,`last_name` varchar(100)
,`email_address` varchar(100)
,`password` varchar(100)
,`user_name` varchar(100)
,`creation_date` datetime
,`institution_id` int(11)
,`institution_name` varchar(100)
,`institution_type` varchar(100)
,`role_id` int(11)
,`role_name` varchar(100)
,`role_code` varchar(100)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `vw_customers`
-- (See below for the actual view)
--
CREATE TABLE `vw_customers` (
`account_id` int(11)
,`first_name` varchar(100)
,`last_name` varchar(100)
,`email_address` varchar(100)
,`password` varchar(100)
,`user_name` varchar(100)
,`creation_date` datetime
,`institution_id` int(11)
,`institution_name` varchar(100)
,`institution_type` varchar(100)
,`role_id` int(11)
,`role_name` varchar(100)
,`role_code` varchar(100)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `vw_vendors`
-- (See below for the actual view)
--
CREATE TABLE `vw_vendors` (
`account_id` int(11)
,`first_name` varchar(100)
,`last_name` varchar(100)
,`email_address` varchar(100)
,`password` varchar(100)
,`user_name` varchar(100)
,`creation_date` datetime
,`institution_id` int(11)
,`institution_name` varchar(100)
,`institution_type` varchar(100)
,`role_id` int(11)
,`role_name` varchar(100)
,`role_code` varchar(100)
);

-- --------------------------------------------------------

--
-- Structure for view `vw_allusers`
--
DROP TABLE IF EXISTS `vw_allusers`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_allusers`  AS SELECT `vw_vendors`.`account_id` AS `account_id`, `vw_vendors`.`first_name` AS `first_name`, `vw_vendors`.`last_name` AS `last_name`, `vw_vendors`.`email_address` AS `email_address`, `vw_vendors`.`password` AS `password`, `vw_vendors`.`user_name` AS `user_name`, `vw_vendors`.`creation_date` AS `creation_date`, `vw_vendors`.`institution_id` AS `institution_id`, `vw_vendors`.`institution_name` AS `institution_name`, `vw_vendors`.`institution_type` AS `institution_type`, `vw_vendors`.`role_id` AS `role_id`, `vw_vendors`.`role_name` AS `role_name`, `vw_vendors`.`role_code` AS `role_code` FROM `vw_vendors` union select `vw_customers`.`account_id` AS `account_id`,`vw_customers`.`first_name` AS `first_name`,`vw_customers`.`last_name` AS `last_name`,`vw_customers`.`email_address` AS `email_address`,`vw_customers`.`password` AS `password`,`vw_customers`.`user_name` AS `user_name`,`vw_customers`.`creation_date` AS `creation_date`,`vw_customers`.`institution_id` AS `institution_id`,`vw_customers`.`institution_name` AS `institution_name`,`vw_customers`.`institution_type` AS `institution_type`,`vw_customers`.`role_id` AS `role_id`,`vw_customers`.`role_name` AS `role_name`,`vw_customers`.`role_code` AS `role_code` from `vw_customers` union select `vw_bankstaff`.`account_id` AS `account_id`,`vw_bankstaff`.`first_name` AS `first_name`,`vw_bankstaff`.`last_name` AS `last_name`,`vw_bankstaff`.`email_address` AS `email_address`,`vw_bankstaff`.`password` AS `password`,`vw_bankstaff`.`user_name` AS `user_name`,`vw_bankstaff`.`creation_date` AS `creation_date`,`vw_bankstaff`.`institution_id` AS `institution_id`,`vw_bankstaff`.`institution_name` AS `institution_name`,`vw_bankstaff`.`institution_type` AS `institution_type`,`vw_bankstaff`.`role_id` AS `role_id`,`vw_bankstaff`.`role_name` AS `role_name`,`vw_bankstaff`.`role_code` AS `role_code` from `vw_bankstaff`  ;

-- --------------------------------------------------------

--
-- Structure for view `vw_bankstaff`
--
DROP TABLE IF EXISTS `vw_bankstaff`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_bankstaff`  AS SELECT DISTINCT `a`.`account_id` AS `account_id`, `a`.`first_name` AS `first_name`, `a`.`last_name` AS `last_name`, `a`.`email_address` AS `email_address`, `a`.`password` AS `password`, `a`.`user_name` AS `user_name`, `a`.`creation_date` AS `creation_date`, `i`.`institution_id` AS `institution_id`, `i`.`institution_name` AS `institution_name`, `i`.`institution_type` AS `institution_type`, `r`.`role_id` AS `role_id`, `r`.`role_name` AS `role_name`, `r`.`role_code` AS `role_code` FROM (((`account` `a` join `bank_staff` `bs` on(`a`.`account_id` = `bs`.`account_id`)) join `role` `r` on(`bs`.`role_id` = `r`.`role_id`)) join `institution` `i` on(`r`.`institution_id` = `i`.`institution_id`))  ;

-- --------------------------------------------------------

--
-- Structure for view `vw_customers`
--
DROP TABLE IF EXISTS `vw_customers`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_customers`  AS SELECT DISTINCT `a`.`account_id` AS `account_id`, `a`.`first_name` AS `first_name`, `a`.`last_name` AS `last_name`, `a`.`email_address` AS `email_address`, `a`.`password` AS `password`, `a`.`user_name` AS `user_name`, `a`.`creation_date` AS `creation_date`, `i`.`institution_id` AS `institution_id`, `i`.`institution_name` AS `institution_name`, `i`.`institution_type` AS `institution_type`, `r`.`role_id` AS `role_id`, `r`.`role_name` AS `role_name`, `r`.`role_code` AS `role_code` FROM ((((`account` `a` join `customer` `c` on(`c`.`account_id` = `a`.`account_id`)) join `bank_staff` `bs` on(`c`.`last_updated_by` = `bs`.`bank_staff_id`)) join `role` `r` on(`c`.`role_id` = `r`.`role_id`)) join `institution` `i` on(`r`.`institution_id` = `i`.`institution_id`))  ;

-- --------------------------------------------------------

--
-- Structure for view `vw_vendors`
--
DROP TABLE IF EXISTS `vw_vendors`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_vendors`  AS SELECT `a`.`account_id` AS `account_id`, `a`.`first_name` AS `first_name`, `a`.`last_name` AS `last_name`, `a`.`email_address` AS `email_address`, `a`.`password` AS `password`, `a`.`user_name` AS `user_name`, `a`.`creation_date` AS `creation_date`, `i`.`institution_id` AS `institution_id`, `i`.`institution_name` AS `institution_name`, `i`.`institution_type` AS `institution_type`, `r`.`role_id` AS `role_id`, `r`.`role_name` AS `role_name`, `r`.`role_code` AS `role_code` FROM (((`account` `a` join `vendor` `v` on(`v`.`account_id` = `a`.`account_id`)) join `institution` `i` on(`v`.`institution_id` = `i`.`institution_id`)) join `role` `r` on(`v`.`role_id` = `r`.`role_id`))  ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `user_name` (`user_name`),
  ADD KEY `account_id_index` (`account_id`),
  ADD KEY `email_index` (`email_address`);

--
-- Indexes for table `bank_staff`
--
ALTER TABLE `bank_staff`
  ADD PRIMARY KEY (`bank_staff_id`),
  ADD KEY `account_id` (`account_id`),
  ADD KEY `branch_id` (`branch_id`),
  ADD KEY `vendor_id` (`vendor_id`),
  ADD KEY `role_id` (`role_id`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`branch_id`),
  ADD UNIQUE KEY `branch_code` (`branch_code`),
  ADD KEY `institution_id` (`institution_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `last_updated_by` (`last_updated_by`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `institution`
--
ALTER TABLE `institution`
  ADD PRIMARY KEY (`institution_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `role_code` (`role_code`),
  ADD KEY `institution_id` (`institution_id`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`vendor_id`),
  ADD KEY `account_id` (`account_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `institution_id` (`institution_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `bank_staff`
--
ALTER TABLE `bank_staff`
  MODIFY `bank_staff_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `branch_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `institution`
--
ALTER TABLE `institution`
  MODIFY `institution_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `vendor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bank_staff`
--
ALTER TABLE `bank_staff`
  ADD CONSTRAINT `bank_staff_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  ADD CONSTRAINT `bank_staff_ibfk_2` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`),
  ADD CONSTRAINT `bank_staff_ibfk_3` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`),
  ADD CONSTRAINT `bank_staff_ibfk_4` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`);

--
-- Constraints for table `branch`
--
ALTER TABLE `branch`
  ADD CONSTRAINT `branch_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`);

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  ADD CONSTRAINT `customer_ibfk_2` FOREIGN KEY (`last_updated_by`) REFERENCES `bank_staff` (`bank_staff_id`),
  ADD CONSTRAINT `customer_ibfk_3` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`);

--
-- Constraints for table `role`
--
ALTER TABLE `role`
  ADD CONSTRAINT `role_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`);

--
-- Constraints for table `vendor`
--
ALTER TABLE `vendor`
  ADD CONSTRAINT `vendor_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  ADD CONSTRAINT `vendor_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  ADD CONSTRAINT `vendor_ibfk_3` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
