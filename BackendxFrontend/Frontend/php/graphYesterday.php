<?php
session_start();
$servername = "132.145.234.32";
$username = "admin";
$password = "PVV123";
$dbname = "PVV_DB";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$pvsystemid = $_SESSION["pvsystemid"];
$sql = "SELECT CAST(fo.date as datetime) as time, fo.p_Load as value FROM PVV_DB.FroniusObject fo WHERE fo.date BETWEEN DATE_SUB(NOW(), INTERVAL 48 HOUR) AND DATE_SUB(NOW(), INTERVAL 24 HOUR) AND login_id IN (  SELECT id FROM FroniusLogin WHERE pvSystemId = '$pvsystemid') ORDER BY fo.date ASC;";
$result = $conn->query($sql);

$data = array();
while ($row = $result->fetch_assoc()) {
    $data[] = $row;
}

// Array als JSON ausgeben
echo json_encode($data);

$conn->close();
?>
