<?php
	$servername = "localhost";
	$username = "id8251698_root";
	$password = "123456";
	$dbname="id8251698_awsheet";
	$connect = mysqli_connect($servername, $username, $password) or die("Unable to Connect to '$dbhost'");
	mysqli_select_db($connect , $dbname) or die("Could not open the db '$dbname'");
	
	$mailAdress = $_POST['mailAdress'];
    $name = $_POST['name'];
    $surname = $_POST['surname'];
    $userpassword = $_POST['password'];
	$sql= "insert into user (mailAdress,name,surname,password) values ('".$mailAdress."','".$name."','".$surname."','".$userpassword."')";
	$result = mysqli_query($connect ,$sql);
	
  
?>


