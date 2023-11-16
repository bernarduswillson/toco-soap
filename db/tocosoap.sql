

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";




CREATE TABLE `logging` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `description` char(255) NOT NULL,
  `IP` char(16) NOT NULL,
  `endpoint` char(255) NOT NULL,
  `requested_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `userGems` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `gem` int NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `transaction`(
    `tid` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `amount` int NOT NULL,
    `image` VARCHAR(255) NOT NULL,
    `status` enum('PENDING','ACCEPTED','REJECTED') NOT NULL DEFAULT 'PENDING',
    `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`tid`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `api`(
    api_id int(11) NOT NULL AUTO_INCREMENT,
    api_key VARCHAR(255) NOT NULL,
    PRIMARY KEY (`api_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `api` (`api_key`) VALUES
    ('Postman'),
    ('toco_rest'),
    ('toco_php');

CREATE TABLE `voucher`(
    `vid` int(11) NOT NULL AUTO_INCREMENT,
    `code` varchar(255) NOT NULL,
    `user_id` int NOT NULL,
    `amount` int NOT NULL,
    `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`vid`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


  





