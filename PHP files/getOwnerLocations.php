<?php
$host = "localhost";
$db_user = "root" ;
$db_password = "root" ;
$db_name = "ParkSafe";


$con = mysqli_connect($host,$db_user,$db_password,$db_name);
$user=$_POST['User'];

$f = -20.0;
$sql = "SELECT * FROM parkingLocations where owner='$user'";

$res = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($res)){
$result[]=$row;
}

echo json_encode(array("locations"=>$result));

mysqli_close($con);

?>
