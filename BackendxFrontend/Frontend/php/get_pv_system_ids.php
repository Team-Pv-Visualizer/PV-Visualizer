<?php
// Replace the following values with your database credentials
$servername = "132.145.234.32";
$username = "admin";
$password = "PVV123";
$dbname = "PVV_DB";

// Create a connection to the database
$conn = new mysqli($servername, $username, $password, $dbname);

// Check the connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Prepare and execute the SQL query
$sql = "SELECT pvSystemId FROM FroniusMessages WHERE approved IS NULL";
$result = $conn->query($sql);
$data = array();

// Fetch the results and store them in an array
if ($result->num_rows > 0) {
  while ($row = $result->fetch_assoc()) {
    $data[] = $row;
  }
} else {
  $data = "0 results";
}

// Output the results as a JSON object
echo json_encode($data);

// Close the database connection
$conn->close();
?>
