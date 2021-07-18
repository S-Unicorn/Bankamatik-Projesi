use bankamatikschema;

CREATE TABLE `bankamatikschema`.`register` (
  `register_id` INT NOT NULL,
  `register_name` VARCHAR(450) NOT NULL,
  `register_surname` VARCHAR(150) NOT NULL,
  `register_password` VARCHAR(100) NOT NULL,
  `account_balance` INT NULL DEFAULT 0,
  `operation_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `roles` VARCHAR(100) NULL DEFAULT 'User',
  `aktiflik` VARCHAR(45) NOT NULL DEFAULT 'Aktif',
  PRIMARY KEY (`register_id`));
  
  
  CREATE TABLE `bankamatikschema`.`userpanel` (
  `user_id` INT NOT NULL,
  `operation_type` VARCHAR(1000) NOT NULL,
  `miktar` INT NULL DEFAULT NULL,
  `kime` VARCHAR(450) NULL DEFAULT NULL,
   PRIMARY KEY (`user_id`));
  
  CREATE TABLE `bankamatikschema`.`adminpanel` (
  `op_type` VARCHAR(1000) NOT NULL,
  `id_user` VARCHAR(1000) NOT NULL);