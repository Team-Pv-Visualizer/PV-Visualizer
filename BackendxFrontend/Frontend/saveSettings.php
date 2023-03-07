<?php
$servername = "192.168.10.192";
$username = "root";
$password = "pvv_perlau";
$dbname = "PVV_DB";

// Create a new connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check the connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$pvsystemid = $_POST["pvsystemid"];

// Check if the pvsystemid already exists
$sql = "SELECT pvSystemId FROM User ORDER BY id DESC LIMIT 1";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    if ($row["pvSystemId"] == $pvsystemid) {
        echo "Error: pvsystemid already exists";
    } else {
        // Insert new record
        $sql = "INSERT INTO User (pvSystemId) VALUES ('$pvsystemid')";
        if ($conn->query($sql) === TRUE) {
            echo "Record inserted successfully";
        } else {
            echo "Error inserting record: " . $conn->error;
        }
    }
} else {
    // Insert new record if table is empty
    $sql = "INSERT INTO User (pvSystemId) VALUES ('$pvsystemid')";
    if ($conn->query($sql) === TRUE) {
        echo "Record inserted successfully";
    } else {
        echo "Error inserting record: " . $conn->error;
    }
}


// Close the connection
$conn->close();
?>