<?php

    class DbClientRepository {

        private $con;

        function __construct(){
            require_once(dirname(__FILE__, 2) . "/DbConnection.php");

            $db = new DbConnection();

            $this->con = $db->connect();
        }

        function getClients() {
            $response = $this->con->query("SELECT * FROM client");
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getClientById($client_id) {
            $stmt = $this->con->prepare("SELECT * FROM client WHERE client_id = ?");
            $stmt->bind_param("i", $client_id);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = $response->fetch_assoc();

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }
        
        function getClientsByPhone($phone) {
            $stmt = $this->con->prepare("SELECT * FROM client WHERE phone_number = ?");
            $stmt->bind_param("s", $phone);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getClientsByNeighborhood($neighborhood) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM client s
                                        JOIN locations c ON s.client_id = c.client_id
                                        WHERE c.neighborhood = ?;");
            $stmt->bind_param("s", $neighborhood);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getClientsByAddress($address) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM client s
                                        JOIN locations c ON s.client_id = c.client_id
                                        WHERE c.full_address = ?;");
            $stmt->bind_param("s", $address);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getClientsByCity($city) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM client s
                                        JOIN locations c ON s.client_id = c.client_id
                                        WHERE c.city = ?;");
            $stmt->bind_param("s", $city);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getClientsByStreet($street) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM client s
                                        JOIN locations c ON s.client_id = c.client_id
                                        WHERE c.street = ?;");
            $stmt->bind_param("s", $street);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getClientByEmail($email) {
            $stmt = $this->con->prepare("SELECT * FROM client WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = $response->fetch_assoc();

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getClientsByFullName($full_name) {
            $stmt = $this->con->prepare("SELECT * FROM client WHERE full_name = ?");
            $stmt->bind_param("s", $full_name);
            $stmt->execute();
            $response = $stmt->get_result();

            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function createClient($client) {
            $stmt = $this->con->prepare("INSERT INTO `client`(`phone_number`, `email`, `full_name`) VALUES (?, ?, ?)");
            $stmt->bind_param("sss", $client->phone_number, $client->email, $client->full_name);
            $stmt->execute();
            $response = $stmt->get_result();  

        }

        function deleteClient($column_name, $value, $condition_type = '=', $value2 = null) {
            $valid_columns = ['client_id', 'phone_number', 'email', 'full_name'];
            if (!in_array($column_name, $valid_columns)) {
                throw new Exception("Invalid column name");
            }
    
            $query = "DELETE FROM client WHERE $column_name ";
    
            switch ($condition_type) {
                case '=':
                    $query .= "= ?";
                    $types = $this->getColumnType($column_name);
                    $params = [$value];
                    break;
                case 'BETWEEN':
                    if ($value2 === null) {
                        throw new Exception("Two values are required for BETWEEN condition");
                    }
                    $query .= "BETWEEN ? AND ?";
                    $types = $this->getColumnType($column_name) . $this->getColumnType($column_name);
                    $params = [$value, $value2];
                    break;
                case '>':
                    $query .= "> ?";
                    $types = $this->getColumnType($column_name);
                    $params = [$value];
                    break;
                case '<':
                    $query .= "< ?";
                    $types = $this->getColumnType($column_name);
                    $params = [$value];
                    break;
                default:
                    throw new Exception("Unsupported condition type");
            }
    
            $stmt = $this->con->prepare($query);
            if ($stmt === false) {
                die('prepare() failed: ' . htmlspecialchars($this->con->error));
            }
    
            $stmt->bind_param($types, ...$params);
            $result = $stmt->execute();
            $stmt->close();
    
            return $result;
        }

        function updateClient($update_column, $new_value, $condition_column, $condition_value, $condition_type = '=', $condition_value2 = null) {
            // Prepare the SQL query based on the condition type
            $valid_columns = ['client_id', 'phone_number', 'email', 'full_name'];
            if (!in_array($update_column, $valid_columns) || !in_array($condition_column, $valid_columns)) {
                throw new Exception("Invalid column name");
            }
            $sql = "UPDATE client SET $update_column = ? WHERE $condition_column ";
    
            switch ($condition_type) {
                case '=':
                    $sql .= "= ?";
                    $types = $this->getColumnType($update_column) . $this->getColumnType($condition_column);
                    $params = [$new_value, $condition_value];
                    break;
                case 'BETWEEN':
                    $sql .= "BETWEEN ? AND ?";
                    $types = $this->getColumnType($update_column) . $this->getColumnType($condition_column) . $this->getColumnType($condition_column);
                    $params = [$new_value, $condition_value, $condition_value2];
                    break;
                case '>':
                    $sql .= "> ?";
                    $types = $this->getColumnType($update_column) . $this->getColumnType($condition_column);
                    $params = [$new_value, $condition_value];
                    break;
                case '<':
                    $sql .= "< ?";
                    $types = $this->getColumnType($update_column) . $this->getColumnType($condition_column);
                    $params = [$new_value, $condition_value];
                    break;
                default:
                    throw new Exception("Unsupported condition type");
            }
    
            // Initialize the prepared statement
            $stmt = $this->con->prepare($sql);
    
            if ($stmt === false) {
                die('prepare() failed: ' . htmlspecialchars($this->con->error));
            }

            // Handle NULL values
            if (strtoupper($new_value) === 'NULL') {
                $stmt->bind_param($types, ...$params);
                $sql = str_replace('?', 'NULL', "UPDATE client SET $update_column = ? WHERE $condition_column ");
            } else {
                $update_type = $this->getColumnType($update_column);
                $types = $update_type . $types;
                $stmt->bind_param($types, $new_value, ...$params);
            }
    
            // Execute the statement
            $result = $stmt->execute();
    
            // Check for errors
            if ($result === false) {
                die('execute() failed: ' . htmlspecialchars($stmt->error));
            }
    
            // Close the statement
            $stmt->close();
    
            return $result;
        }
    
        // Helper method to determine column data types
        private function getColumnType($column_name) {
            $query = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'client' AND COLUMN_NAME = ?";
            $stmt = $this->con->prepare($query);
    
            if ($stmt === false) {
                die('prepare() failed: ' . htmlspecialchars($this->con->error));
            }
    
            $stmt->bind_param('s', $column_name);
            $stmt->execute();
            $stmt->bind_result($data_type);
            $stmt->fetch();
            $stmt->close();
    
            // Map SQL data types to bind_param data types
            switch($data_type) {
                case 'int':
                case 'tinyint':
                case 'smallint':
                case 'mediumint':
                case 'bigint':
                    return 'i'; // integer
                case 'float':
                case 'double':
                case 'decimal':
                    return 'd'; // double
                default:
                    return 's'; // string
            }
        }
    }

?>