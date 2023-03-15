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

$pvsystemid = $_POST["pvsystemid"];

// Check if the pvsystemid already exists
$sql = "SELECT pvSystemId FROM FroniusLogin ORDER BY id DESC LIMIT 1";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    if ($row["pvSystemId"] == $pvsystemid) {
        echo "Error: pvsystemid already exists";
    } else {
        // Insert new record
        $sql = "INSERT INTO FroniusLogin (pvSystemId) VALUES ('$pvsystemid')";
        if ($conn->query($sql) === TRUE) {
            echo "Record inserted successfully";
        } else {
            echo "Error inserting record: " . $conn->error;
        }
    }
} else {
    // Insert new record if table is empty
    $sql = "INSERT INTO FroniusLogin (pvSystemId) VALUES ('$pvsystemid')";
    if ($conn->query($sql) === TRUE) {
        echo "Record inserted successfully";
    } else {
        echo "Error inserting record: " . $conn->error;
    }
}


// Close the connection
$conn->close();
?>