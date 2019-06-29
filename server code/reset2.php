<?php

$link=mysqli_connect("localhost","root","1004","medicine");
if (!$link)
{
	echo "mysql error : ";
	echo mysqli_connect_error();
	exit();
}


$sql="UPDATE storage SET cnt = 0, date = '0000-00-00 00:00:00'   WHERE num=2";

if (mysqli_query($link,$sql)){
	echo "record updated successfully";
} else {
	echo " Error updating record: " . mysqli_error($conn);
}

mysqli_close($link);

?>

