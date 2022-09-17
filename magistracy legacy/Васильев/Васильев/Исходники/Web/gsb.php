<!DOCTYPE html>
<html>
<head>
<title>GameScoreboard</title>
<link rel="stylesheet" href="mystyle.css">
<meta charset="utf-8" />
<style type="text/css">
   .space { 
    padding: 20px; 
    background: #E5D3BD; 
    border: 2px solid #E81E25; 
   }
  </style>
</head>
<body>
<?php
	$hostname = "localhost";
	$dbName = "table";
	$username = "Table";
	$password = "!234";
	$conn = mysqli_connect($hostname,$username,$password, $dbName) OR DIE("No connection");   
	if (!$conn) {
		echo "Error: Impossible to connect." . PHP_EOL;
		echo "Error code errno: " . mysqli_connect_errno() . PHP_EOL;
		echo "Error text error: " . mysqli_connect_error() . PHP_EOL;
		exit;
	}
	//echo "Connection established";
	$sql = "SELECT * FROM tabledata ORDER BY id DESC LIMIT 1";
	if($result = $conn->query($sql)){
    foreach($result as $row){
        $teamName1 = $row["teamName1"];
		$teamName2 = $row["teamName2"];
		$Part = $row["Part"];
		$round = $row["Round"];
		$scoreRound1 = $row["scoreRound1"];
		$scoreRound2 = $row["ScoreRound2"];
		$scorePart1 = $row["ScorePart1"];
		$scorePart2 = $row["ScorePart2"];
    }
	}
	$conn->close();
   ?>
<p align="center"><big><big><big>Партия</big></big></big></p>
<p align="center"><big><big>
   <?php
   echo "$Part";
   ?>
   </big></big></p>
<p align="center"><big><big><big>Раунд</big></big></big></p>
<p align="center"><big><big>
   <?php
   echo "$round";
   ?>
   </big></big></p>
<hr>
<div id="block1">
   <p align="center"><big><big><big>Команда</big></big></big></p>
   <p align="center"><big><big>
   <?php
   echo "$teamName1";
   ?>
   </big></big></p>
   <p align="center"><big><big><big>Счет по раундам</big></big></big></p>
   <p align="center"><big><big>
   <?php
   echo "$scoreRound1";
   ?>
   </big></big></p>
   <p align="center"><big><big><big>Счет по партиям</big></big></big></p>
   <p align="center"><big><big>
   <?php
   echo "$scorePart1";
   ?>
   </big></big></</p>
  </div>
 <div id="block2">
   <p align="center"><big><big><big>Команда</big></big></big></p>
   <p align="center"><big><big>
   <?php
   echo "$teamName2";
   ?>
   </big></big></p>
   <p align="center"><big><big><big>Счет по раундам</big></big></big></p>
   <p align="center"><big><big>
   <?php
   echo "$scoreRound2";
   ?>
   </big></big></p>
   <p align="center"><big><big><big>Счет по партиям</big></big></big></p>
   <p align="center"><big><big>
   <?php
   echo "$scorePart2";
   ?>
   </big></big></p>
  </div>
  <?php
  //header("Refresh:5");
  ?>
</body>
</html>