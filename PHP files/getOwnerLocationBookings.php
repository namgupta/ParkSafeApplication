<?php
$host = "localhost";
$db_user = "root" ;
$db_password = "root" ;
$db_name = "ParkSafe";


$con = mysqli_connect($host,$db_user,$db_password,$db_name);
$id=$_POST['Location_id'];

$f = -20.0;
$sql = "SELECT * FROM Bookings where location_id='$id'";

$res = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($res)){
$result[]=$row;
}

echo json_encode(array("hubdetail"=>$result));

mysqli_close($con);

?>
