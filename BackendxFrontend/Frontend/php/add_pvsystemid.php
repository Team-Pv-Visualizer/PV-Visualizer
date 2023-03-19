<?php

if (!isset($_REQUEST['pvSystemId'])) {
  header('HTTP/1.0 400 Bad Request');
  echo 'pvSystemId parameter is missing';
  exit();
}

$pvSystemId = $_REQUEST['pvSystemId'];

// make a connection to the database
$servername = "132.145.234.32";
$username = "admin";
$password = "PVV123";
$dbname = "PVV_DB";

$conn = new mysqli($servername, $username, $password, $dbname);

// check connection
if ($conn->connect_error) {
  header('HTTP/1.0 500 Internal Server Error');
  echo "Connection failed: " . $conn->connect_error;
  exit();
}

$sql = "INSERT INTO FroniusLogin (pvSystemId) VALUES ('$pvSystemId')";
if ($conn->query($sql) === TRUE) {
  echo "pvSystemId inserted successfully";
} else {
  header('HTTP/1.0 500 Internal Server Error');
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$sql = "UPDATE FroniusMessages SET approved='1' WHERE pvSystemId='$pvSystemId'";
if ($conn->query($sql) === TRUE) {
  echo "FroniusMessages updated successfully";
} else {
  header('HTTP/1.0 500 Internal Server Error');
  echo "Error: " . $sql . "<br>" . $conn->error;
}


$conn->close();

?>
