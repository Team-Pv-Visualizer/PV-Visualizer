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
$sql = "SELECT DATE_FORMAT(h.date, '%M') AS month, (ROUND(SUM(h.p_Load) / 1000) / 15  AS Verbrauch FROM PVV_DB.FroniusObject h WHERE login_id IN ( SELECT id FROM FroniusLogin WHERE pvSystemId = '$pvsystemid' ) GROUP BY month ORDER BY month DESC;";
$result = $conn->query($sql);

$data = array();
while ($row = $result->fetch_assoc()) {
    $data[] = $row;
}

// Array als JSON ausgeben
echo json_encode($data);

$conn->close();
?>