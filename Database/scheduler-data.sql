/*
	Authors:	Edward Pino | Juan Pita
	Date:		2021 - 03 - 17
	Purpose:	Scheduler Project | Object-oriented Programming	(Oop)
*/



USE Scheduler;

INSERT INTO Accounts(userId, fName, lName, username, passwd, email) VALUES (101,"Tatyana","Chase","V1V4MU5","keb75ONm2Xa","eget@liberet.edu"),
(102,"Stone","Duffy","ridiculusmu5","vLH86zeN4hl","arcu.eu@interdumNuncsollicitudin.ca"),
(103,"Edward","Pino","edapigo","marik6","omarciano35@hotmail.com"),
(104,"Patricia","Lane","eG3tMeTus","QxV11XkP1NE","non.nisi@tortorInteger.org"),
(105,"Kibo","Guzman","acrisus","SDH06QMO1NB","vitae@nascetur.ca"),
(106,"Sylvester","Perry","moles7iesodales","Age07quh9pi","sed.auctor@rhoncusidmollis.edu"),
(107,"Allen","Chambers","hendrerit","jZY14vVZ3Ej","auctor.odio@magnaPraesent.com"),
(108,"Juan","Pita","jxpita","123456789","asgardianwarrior@hotmail.com"),
(109,"Taylor","Fulton","PENATIBUS","iIf43jBa7Om","est.arcu@enimconsequatpurus.co.uk"),
(110,"Aimee","Kirk","l0b0rtisAugue","UPY54UEY4wi","Pellentesque@bibendumfermentummetus.ca");

INSERT INTO Tasks(taskId, title, overview) VALUES (1001,"ligula mauris","Nam nulla magna in cursus et eros"),
(1002, "at sem molestie", "elementum purus accumsan interdum libero"),
(1003, "ulcorper vel", "purus Duis elementum dui quis accumsan convallis ante lectus convallis"),
(1004, "Nunc velit", "enim mi tempor lorem eget mollis lectus"),
(1005, "Oop Project", "Develop a JavaFX App to manage user tasks"),
(1006, "amet metus era", "malesuada malesuada Integer id magna et ipsum cursus vestibulum Mauris"),
(1007, "enim Sed", "faucibus lectus a sollicitudin orci sem"),
(1008, "covalis cursus", "vitae purus gravida sagittis Duis gravida Praesent"),
(1009, "eget volutpat", "diam eu dolor egestas rhoncus Proin nisl sem consequat nec"),
(1010, "vel arcu eu", "dolor elit pellentesque a facilisis non, bibendum");

INSERT INTO Allocation (userId, taskId, beginning, ending) VALUES (101, 1001, "2021-11-26 14:30:00", "2021-12-26 14:30:00"),
(102, 1002, "2021-07-22 00:30:00", "2021-07-22 17:00:00"),
(103, 1003, "2021-08-17 05:30:00", "2021-10-28 10:00:00"),
(104, 1004, "2022-03-29 00:30:00", "2023-03-29 03:00:00"),
(105, 1005, "2021-04-20 22:00:00", "2021-04-30 22:00:00"),
(106, 1006, "2021-09-04 13:00:00", "2021-09-04 14:00:00"),
(107, 1007, "2025-03-28 19:00:00", "2025-03-31 08:00:00"),
(108, 1008, "2021-05-11 12:00:00", "2021-05-15 17:00:00"),
(109, 1009, "2023-11-20 08:00:00", "2023-11-21 06:00:00"),
(110, 1010, "2022-06-09 08:30:00", "2022-06-13 16:30:00"),
(105, 1008, "2021-05-11 12:00:00", "2021-05-15 17:00:00"),
(103, 1006, "2021-09-04 13:00:00", "2021-09-04 14:00:00"),
(108, 1005, "2021-04-20 22:00:00", "2021-04-30 22:00:00"),
(107, 1005, "2021-04-20 22:00:00", "2021-04-30 22:00:00"),
(110, 1009, "2023-11-20 08:00:00", "2023-11-21 06:00:00"),
(103, 1001, "2021-11-26 14:30:00", "2021-12-26 14:30:00"),
(102, 1010, "2022-06-09 08:30:00", "2022-06-13 16:30:00"),
(103, 1005, "2021-04-20 22:00:00", "2021-04-30 22:00:00"),
(104, 1003, "2021-08-17 05:30:00", "2021-10-28 10:00:00"),
(108, 1010, "2022-06-09 08:30:00", "2022-06-13 16:30:00");


