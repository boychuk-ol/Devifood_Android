<?php

    class DbLocationRepository {

        private $con;

        function __construct(){
            require_once(dirname(__FILE__) . "/DbConnection.php");

            $db = new DbConnection();

            $this->con = $db->connect();
        }

        function getLocations() {
            $response = $this->con->query("SELECT * FROM locations");
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getLocationById($location_ID) {
            $stmt = $this->con->prepare("SELECT * FROM locations WHERE location_id = ?");
            $stmt->bind_param("i", $location_ID);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = $response->fetch_assoc();

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }
        
        function getLocationByFullAdress($full_address) {
            $stmt = $this->con->prepare("SELECT * FROM locations WHERE full_address = ?");
            $stmt->bind_param("s", $full_address);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = $response->fetch_assoc();
            
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getLocationsByCity($city) {
            $stmt = $this->con->prepare("SELECT * FROM locations WHERE city = ?");
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

        function getLocationsByNeighborhood($neighborhood) {
            $stmt = $this->con->prepare("SELECT * FROM locations WHERE neighborhood = ?");
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

        function getLocationsByStreet($street) {
            $stmt = $this->con->prepare("SELECT * FROM locations WHERE street = ?");
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

        function getLocationsByStreetNumber($street_number) {
            $stmt = $this->con->prepare("SELECT * FROM locations WHERE street_number = ?");
            $stmt->bind_param("s", $street_number);
            $stmt->execute();
            $response = $stmt->get_result();

            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function deleteLocation($column_name, $value, $condition_type = '=', $value2 = null) {
            $valid_columns = ['location_id', 'full_address', 'city', 'street', 'street_number', 'shop_id', 'client_id'];
            if (!in_array($column_name, $valid_columns)) {
                throw new Exception("Invalid column name");
            }
    
            $query = "DELETE FROM locations WHERE $column_name ";
    
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

        function updateLocation($update_column, $new_value, $condition_column, $condition_value, $condition_type = '=', $condition_value2 = null) {
            // Prepare the SQL query based on the condition type
            $valid_columns = ['location_id', 'full_address', 'city', 'street', 'street_number', 'shop_id', 'client_id'];
            if (!in_array($update_column, $valid_columns) || !in_array($condition_column, $valid_columns)) {
                throw new Exception("Invalid column name");
            }
            $sql = "UPDATE locations SET $update_column = ";
            
            if (strtoupper($new_value) === 'NULL') {
                $sql .= "NULL WHERE $condition_column ";
            } else {
                $sql .= "? WHERE $condition_column ";
            }
            
            switch ($condition_type) {
                case '=':
                    $sql .= "= ?";
                    break;
                case 'BETWEEN':
                    $sql .= "BETWEEN ? AND ?";
                    break;
                case '>':
                    $sql .= "> ?";
                    break;
                case '<':
                    $sql .= "< ?";
                    break;
                default:
                    throw new Exception("Unsupported condition type");
            }
        
            $stmt = $this->con->prepare($sql);
            if ($stmt === false) {
                die('prepare() failed: ' . htmlspecialchars($this->con->error));
            }
    
            // Determine parameter types
            if (strtoupper($new_value) !== 'NULL') {
                $update_type = $this->getColumnType($update_column);
            } else {
                $update_type = '';
            }
            $condition_type1 = $this->getColumnType($condition_column);
            $condition_type2 = $condition_type == 'BETWEEN' ? $condition_type1 : '';
            $types = $update_type . $condition_type1 . $condition_type2;
            
            // Bind parameters dynamically
            if (strtoupper($new_value) === 'NULL') {
                if ($condition_type == 'BETWEEN') {
                    $stmt->bind_param($condition_type1 . $condition_type1, $condition_value, $condition_value2);
                } else {
                    $stmt->bind_param($condition_type1, $condition_value);
                }
            } else {
                if ($condition_type == 'BETWEEN') {
                    $stmt->bind_param($types, $new_value, $condition_value, $condition_value2);
                } else {
                    $stmt->bind_param($types, $new_value, $condition_value);
                }
            }
    
            // Execute the statement
            $result = $stmt->execute();
        
            // Check for errors
            if ($result === false) {
                die('execute() failed: ' . htmlspecialchars($stmt->error));
            }
        
            $stmt->close();
        
            return $result;
        }
    
        private function getColumnType($column_name) {
            $query = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'categories' AND COLUMN_NAME = ?";
            $stmt = $this->con->prepare($query);
        
            if ($stmt === false) {
                die('prepare() failed: ' . htmlspecialchars($this->con->error));
            }
        
            $stmt->bind_param('s', $column_name);
            $stmt->execute();
            $stmt->bind_result($data_type);
            $stmt->fetch();
            $stmt->close();
        
            switch($data_type) {
                case 'int':
                case 'tinyint':
                case 'smallint':
                case 'mediumint':
                case 'bigint':
                    return 'i';
                case 'float':
                case 'double':
                case 'decimal':
                    return 'd';
                default:
                    return 's';
            }
        }
    }

?>