<?php
$host = "localhost";
$db_user = "root" ;
$db_password = "root" ;
$db_name = "ParkSafe";


$con = mysqli_connect($host,$db_user,$db_password,$db_name);
$uid=$_POST['uid'];

$f = -20.0;
$sql = "SELECT * FROM parkingLocations where uid='$uid'";

$res = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($res)){
$result[]=$row;
}

echo json_encode(array("messages"=>$result));

mysqli_close($con);

?>
