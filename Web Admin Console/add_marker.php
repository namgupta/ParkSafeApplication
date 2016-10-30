<?php

$host = "localhost";
 $db_user = "root" ;
 $db_password = "root" ;
 $db_name = "ParkSafe";
    // Get image string posted from Android App
    $lat=$_REQUEST['latitude'];
    $lng=$_REQUEST['longitude'];
    

$con = mysqli_connect($host,$db_user,$db_password,$db_name);
     echo $con;
    $sql = "INSERT INTO parkingLocations (lat,lng) VALUES ('$lat','$lng')";

    $res = mysqli_query($con,$sql);
 
    mysqli_close($con);
?>