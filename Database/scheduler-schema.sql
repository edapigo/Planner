/*
	Authors:	Edward Pino | Juan Pita
	Date:		2021 - 03 - 17
	Purpose:	Scheduler Project | Object-oriented Programming	(Oop)
*/



SET GLOBAL time_zone = '-3:00';

DROP SCHEMA IF EXISTS Scheduler;
CREATE SCHEMA Scheduler;
USE Scheduler;


DROP TABLE IF EXISTS Accounts;
CREATE TABLE Accounts(
	userId INT NOT NULL AUTO_INCREMENT,
    fName VARCHAR(15) NOT NULL,
    lName VARCHAR(15) NOT NULL,
    username VARCHAR(20) UNIQUE NOT NULL,
	passwd VARCHAR(20) NOT NULL,
    email VARCHAR(40) UNIQUE NOT NULL,
    CONSTRAINT PK_userId PRIMARY KEY(userId)
) DEFAULT CHARSET=utf8mb4, AUTO_INCREMENT=101;

DROP TABLE IF EXISTS Tasks;
CREATE TABLE Tasks(
	taskId INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(15) NOT NULL,
    overview VARCHAR(100),
    CONSTRAINT PK_taskId PRIMARY KEY(taskId)
) DEFAULT CHARSET=utf8mb4, AUTO_INCREMENT=1001;

DROP TABLE IF EXISTS Allocation;
CREATE TABLE Allocation(
	userId INT NOT NULL,
    taskId INT NOT NULL,
    beginning DATETIME NOT NULL,
    ending DATETIME NOT NULL,
    CONSTRAINT PK_Allocaction PRIMARY KEY(userId, taskId),
    CONSTRAINT FK_user FOREIGN KEY(userId) REFERENCES Accounts(userId),
    CONSTRAINT FK_task FOREIGN KEY(taskId) REFERENCES Tasks(taskId)
) DEFAULT CHARSET=utf8mb4;



DROP PROCEDURE IF EXISTS sp_create_account;
DELIMITER $$
CREATE PROCEDURE sp_create_account(IN firstName VARCHAR(15), IN lastName VARCHAR(15), IN accountName VARCHAR(20), 
								   IN pass VARCHAR(20), IN e_mail VARCHAR(40))
BEGIN
	INSERT INTO Accounts(fName, lName, username, passwd, email) VALUE (firstName, lastName, accountName, pass, e_mail);
END;
$$ DELIMITER ;


