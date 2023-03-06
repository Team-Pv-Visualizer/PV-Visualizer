<?php
// Replace with your MySQL database credentials
$servername = "localhost";
$username = "root";
$password = "yourpassword";
$dbname = "PVV_DB";

// Create a new connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check the connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Check if the pvsystemid already exists
$pvsystemid = $_POST["pvsystemid"];
$sql = "SELECT * FROM User WHERE pvSystemId = '$pvsystemid'";
$result = $conn->query($sql);

if ($result->num_rows == 0) {
    // The pvsystemid does not exist, insert a new record
    $sql = "INSERT INTO User (pvSystemId) VALUES ('$pvsystemid')";
    if ($conn->query($sql) === TRUE) {
        echo "Record inserted successfully";
    } else {
        echo "Error inserting record: " . $conn->error;
    }
} else {
    echo "Record already exists";
}

// Close the connection
$conn->close();
?>



