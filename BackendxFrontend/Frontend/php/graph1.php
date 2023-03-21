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
$sql = "SELECT DATE_FORMAT(date, '%d-%m') AS Datum, (SUM(p_Load) / 1000) / 15 as Stromverbrauch FROM FroniusObject WHERE login_id IN ( SELECT id FROM FroniusLogin WHERE pvSystemId = '$pvsystemid' ) GROUP BY DATE_FORMAT(date, '%d-%m') ORDER BY MIN(date) ASC;";
$result = $conn->query($sql);

$data = [];
while ($row = $result->fetch_assoc()) {
    $data[] = $row;
}

// Array als JSON ausgeben
echo json_encode($data);

$conn->close();
?>