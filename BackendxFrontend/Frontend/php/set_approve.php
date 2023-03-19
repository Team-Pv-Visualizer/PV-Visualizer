<?php

// check if the pvSystemId and value are set in the request
if (!isset($_REQUEST['pvSystemId'])) {
  header('HTTP/1.0 400 Bad Request');
  echo 'pvSystemId or value parameter is missing';
  exit();
}

$pvSystemId = $_REQUEST['pvSystemId'];
$value = $_REQUEST['value'];

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

// prepare and execute the SQL query to update the approve value in the entries table
$sql = "UPDATE FroniusMessages SET approved='0' WHERE pvSystemId='$pvSystemId'";
if ($conn->query($sql) === TRUE) {
  echo "approve updated successfully";
} else {
    header('HTTP/1.0 500 Internal Server Error');
    echo "Error: " . $sql . "<br>" . $conn->error;
  }
  
  $conn->close();
  
  ?>
