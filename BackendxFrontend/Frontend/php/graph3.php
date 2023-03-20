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
$sql = "SELECT h.date as Datum, CONCAT(ROUND(h.p_Load, 2), ' Wh') as Verbrauch FROM PVV_DB.FroniusObject h WHERE login_id IN ( SELECT id FROM FroniusLogin WHERE pvSystemId = '$pvsystemid' ) ORDER BY h.id DESC LIMIT 1;";
$result = $conn->query($sql);

$data = array();
while ($row = $result->fetch_assoc()) {
    $data[] = $row;
}

// Array als JSON ausgeben
echo json_encode($data);

$conn->close();
?>
