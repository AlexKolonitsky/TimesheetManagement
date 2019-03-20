CREATE TABLE `project` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `logoUrl` varchar(200) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `manHours` int(11) NOT NULL,
  `code` varchar(6) NOT NULL,
  `colour` varchar(6) NOT NULL,
  `description` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee` (
  `id` BIGINT NOT NULL,
  `firstName` VARCHAR(40) NOT NULL,
  `lastName` VARCHAR(40) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `positionId` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
 );

CREATE TABLE  `position` (
  `id` BIGINT NOT NULL,
  `position` VARCHAR(100) NOT NULL,
  `companyId` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `companyId_UNIQUE, position_UNIQUE` (`companyId, position` ASC) VISIBLE
 );

create table Assigment (
projectId bigint NOT NULL, 
employeeId bigint NOT NULL, 
workLoadInMinuts int 
);

create table Invitations (
employeeId bigint NOT NULL PRIMARY KEY,
companyId bigint NOT NULL,
email VARCHAR(150) NOT NULL,
invitationsCode VARCHAR(40) NOT NULL,
dateEnd datetime NOT NULL,
status varchar(25) NOT NULL
 );

create table Timesheet (
id bigint NOT NULL primary key,
periodId bigint NOT NULL,
timesheetJson json NOT NULL,
status varchar(100) NOT NULL
);

CREATE TABLE `logs` (
  `projectId` bigint(20) NOT NULL,
  `employeeId` bigint(25) NOT NULL,
  `time` double NOT NULL,
  `comment` varchar(100) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


create table Notification (
id bigint NOT NULL PRIMARY KEY,
createdAt DATETIME NOT NULL,
employeId bigint NOT NULL,
status varchar(100) NOT NULL,
title varchar(100) NOT NULL,
description text(200),
link varchar(100)
);