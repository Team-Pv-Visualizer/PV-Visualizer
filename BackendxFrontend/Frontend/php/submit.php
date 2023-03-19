<?php
$pvsystemid = $_POST['pvsystemid'];
$nachricht = $_POST['nachricht'];

$servername = "132.145.234.32";
$username = "admin";
$password = "PVV123";
$dbname = "PVV_DB";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "INSERT INTO FroniusMessages (pvsystemid, messages) VALUES ('$pvsystemid', '$nachricht')";

if ($conn->query($sql) === TRUE) {
  echo "Data inserted successfully";
} else {
  echo "Error inserting data: " . $conn->error;
}

$conn->close();
?>
