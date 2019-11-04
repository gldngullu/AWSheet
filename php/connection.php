<?php
	$servername = "localhost";
	$username = "id8251698_root";
	$password = "123456";
	$dbname="id8251698_awsheet";
	$connect = mysqli_connect($servername, $username, $password) or die("Unable to Connect to '$dbhost'");
	mysqli_select_db($connect , $dbname) or die("Could not open the db '$dbname'");
	
	$test_query = "SHOW TABLES FROM $dbname";
	$result = mysqli_query($connect ,$test_query);
	$tblCnt = 0;
	while($tbl = mysqli_fetch_array($result)) {
  $tblCnt++;
	echo $tbl[0]."<br />\n";
}
?>