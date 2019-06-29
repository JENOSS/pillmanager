<?php

$link=mysqli_connect("localhost","root","1004","medicine");
if (!$link)
{
	echo "mysql error : ";
	echo mysqli_connect_error();
	exit();
}

mysqli_set_charset($link,"utf8");

$sql="select * from storage";

$result=mysqli_query($link,$sql);
$data = array();
if($result){

	while($row=mysqli_fetch_array($result)){
		array_push($data,
			array('num'=>$row[0],'cnt'=>$row[1],'date'=>$row[2]));
	}

	header('Content-Type: text/plain; charset=utf8');
	$json = json_encode(array("storage"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;
}
else{
	echo "SQL query error ! : ";
	echo mysqli_error($link);
}

mysqli_close($link);

?>

