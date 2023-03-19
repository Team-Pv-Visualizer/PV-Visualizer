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

$pvsystemid = $_POST["pvsystemid"];

$sql = "SELECT pvSystemId FROM FroniusLogin WHERE pvSystemId = '$pvsystemid'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $_SESSION["pvsystemid"] = $pvsystemid;
    if($pvsystemid == "pvv_admin_pl"){
        echo "admin";
    } else {
        echo "true";
    }
} else {
    echo "false";
}

$conn->close();
?>
