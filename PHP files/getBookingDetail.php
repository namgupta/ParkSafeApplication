<?php
$host = "localhost";
$db_user = "root" ;
$db_password = "root" ;
$db_name = "ParkSafe";

$ref = $_POST["BookingReference"];
$con = mysqli_connect($host,$db_user,$db_password,$db_name);

$sql = "select * from Bookings WHERE booking_reference = '$ref'";

$res = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($res)){
$result[]=$row;
}

echo json_encode(array("bookings"=>$result));

mysqli_close($con);

?>
