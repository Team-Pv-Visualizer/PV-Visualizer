<?php
$servername = "132.145.234.32";
$username = "admin";
$password = "PVV123";
$dbname = "PVV_DB";

// Create a new connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check the connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get the last pvsystemid from the database
$sql = "SELECT pvSystemId FROM FroniusLogin ORDER BY id DESC LIMIT 1";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Output the last pvsystemid
    $row = $result->fetch_assoc();
    echo $row["pvSystemId"];
} else {
    // No pvsystemid found
    echo "No pvsystemid found";
}

// Close the connection
$conn->close();
?>