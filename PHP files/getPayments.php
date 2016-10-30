<?php
$host = "localhost";
$db_user = "root" ;
$db_password = "root" ;
$db_name = "ParkSafe";

$user = $_POST["User"];
$con = mysqli_connect($host,$db_user,$db_password,$db_name);

$sql = "select * from Payment WHERE user = '$user'";

$res = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($res)){
$result[]=$row;
}

echo json_encode(array("payments"=>$result));

mysqli_close($con);

?>
