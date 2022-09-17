<?  
 
/* Получаем значения переменных, переданных методом POST*/
$teamName1 = $_POST['teamName1'];
$teamName2 = $_POST['teamName2'];
$Part = $_POST['Part']; 
$Round = $_POST['Round']; 
$scoreRound1 = $_POST['scoreRound1']; 
$scoreRound2 = $_POST['scoreRound2'];
$scorePart1 = $_POST['scorePart1'];
$scorePart2 = $_POST['scorePart2'];
 
/* создать соединение  с заданными ранее именем сервера MySQL, логином и паролем*/ 
$hostname = "localhost";
$dbName = "table";
$username = "Table";
$password = "!234";
$conn = mysqli_connect($hostname,$username,$password, $dbName) OR DIE("No connection");  
/* выбрать базу данных с ранее заданным именем. Если произойдет ошибка - вывести ее */ 
if (!$conn) {
    echo "Error: Impossible to connect." . PHP_EOL;
    echo "Error code errno: " . mysqli_connect_errno() . PHP_EOL;
    echo "Error text error: " . mysqli_connect_error() . PHP_EOL;
    exit;
}
echo "Connection established";
/*echo "$teamName1 <br>";
echo "$teamName2 <br>";
echo "$Part <br>";
echo "$Round <br>";
echo "$scoreRound1 <br>";
echo "$scoreRound2 <br>";
echo "$scorePart1 <br>";
echo "$scorePart2 <br>";*/
$sql = mysqli_query($conn, "INSERT INTO tabledata (teamName1, teamName2, Part, Round, 
scoreRound1, scoreRound2, scorePart1, scorePart2) 
VALUES ('{$_POST['teamName1']}', '{$_POST['teamName2']}', {$_POST['Part']}, {$_POST['Round']}, 
{$_POST['scoreRound1']}, {$_POST['scoreRound2']}, 
{$_POST['scorePart1']}, {$_POST['scorePart2']})");
if ($sql) {
      echo '<p>Data successfully added.</p>';
    } else {
      echo '<p>Error: ' . mysqli_error($conn) . '</p>';
    }
$conn->close();
?>