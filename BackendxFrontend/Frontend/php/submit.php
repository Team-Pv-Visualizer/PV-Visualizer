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
$message = $_POST["message"];

$sql = "INSERT INTO FroniusMessages (pvSystemId, messages) VALUES ('$pvsystemid', '$message')";
if ($conn->query($sql) === TRUE) {
    echo "Antrag erfolgreich abgeschickt!";
} else {
    echo "Fehler beim Einfügen der Daten: " . $conn->error;
}

$conn->close();
?>
