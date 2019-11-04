<?php
	$servername = "localhost";
	$username = "id8251698_root";
	$password = "123456";
	$dbname="id8251698_awsheet";
	$connect = mysqli_connect($servername, $username, $password) or die("Unable to Connect to '$dbhost'");
	mysqli_select_db($connect , $dbname) or die("Could not open the db '$dbname'");

	$mail= $_POST['mailAdress'];
	$coursecode = $_POST['coursecode'];

	$sql= "select (courseCode) from (student_course) where (Student_Mail_Adress IS NOT '$mail')AND(CourseCode IS NOT '$coursecode) ";
	$r = mysqli_query($connect ,$sql);
    $result = array();
    while($row = mysqli_fetch_array($r)){
        array_push($result,array(
         'coursecode'=>$row['courseCode']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($connect);
  
?>