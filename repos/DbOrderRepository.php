<?php

class DbOrderRepository {

    private $con;

    function __construct(){
        require_once(dirname(__FILE__, 2) . "/DbConnection.php");

        $db = new DbConnection();

        $this->con = $db->connect();
    }

    function getOrders() {
        $response = $this->con->query("SELECT * FROM orders");
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrderById($order_id) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE order_id = ?");
        $stmt->bind_param("i", $order_id);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = $response->fetch_assoc();

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByClient($client_id) {
        $stmt = $this->con->prepare("SELECT s.* 
                                        FROM orders s 
                                        JOIN client c 
                                        ON s.FKclient_id = c.client_id 
                                        WHERE s.FKclient_id=?;");
        $stmt->bind_param("i", $client_id);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByCourier($courier_id) {
        $stmt = $this->con->prepare("SELECT s.* 
                                        FROM orders s 
                                        JOIN courier c 
                                        ON s.FKcourier_id = c.courier_id 
                                        WHERE s.FKcourier_id=?;");
        $stmt->bind_param("i", $courier_id);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByDoneTime($done_time) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE done = ?");
        $stmt->bind_param("i", $done_time);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByDoneTimePeriod($from_time, $to_time) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE done BETWEEN ? AND ?");
        $stmt->bind_param("ii", $from_time, $to_time);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByCreatedTime($created_time) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE created = ?");
        $stmt->bind_param("s", $created_time);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByCreatedTimePeriod($from_time, $to_time) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE created BETWEEN ? AND ?");
        $stmt->bind_param("ii", $from_time, $to_time);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByStatus($status) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE o_status = ?");
        $stmt->bind_param("s", $status);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByDeliveryPrice($delivery_price) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE delivery_price = ?");
        $stmt->bind_param("d", $delivery_price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByDeliveryTime($delivery_time) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE delivery_time = ?");
        $stmt->bind_param("i", $delivery_time);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByTotalPrice($total_price) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE total_price = ?");
        $stmt->bind_param("d", $total_price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersByPaymentMethod($payment_method) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE payment_method = ?");
        $stmt->bind_param("s", $payment_method);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithDeliveryPriceBetween($min_price, $max_price) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE delivery_price BETWEEN ? AND ?");
        $stmt->bind_param("dd", $min_price, $max_price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithDeliveryPriceLessThan($price) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE delivery_price < ?");
        $stmt->bind_param("d", $price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithDeliveryPriceMoreThan($price) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE delivery_price > ?");
        $stmt->bind_param("d", $price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithDeliveryTimeBetween($min_time, $max_time) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE delivery_time BETWEEN ? AND ?");
        $stmt->bind_param("ii", $min_time, $max_time);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithDeliveryTimeLessThan($time) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE delivery_time < ?");
        $stmt->bind_param("i", $time);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithDeliveryTimeMoreThan($time) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE delivery_time > ?");
        $stmt->bind_param("i", $time);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithTotalPriceBetween($min_price, $max_price) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE total_price BETWEEN ? AND ?");
        $stmt->bind_param("dd", $min_price, $max_price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithTotalPriceLessThan($price) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE total_price < ?");
        $stmt->bind_param("d", $price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getOrdersWithTotalPriceMoreThan($price) {
        $stmt = $this->con->prepare("SELECT * FROM orders WHERE total_price > ?");
        $stmt->bind_param("d", $price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function deleteOrder($column_name, $value, $condition_type = '=', $value2 = null) {
        $valid_columns = ['order_id', 'created', 'done', 'o_status', 'delivery_price', 'delivery_time', 'total_price', 'payment_method',
                            'FKcourier_id', 'FKclient_id', 'shop_location_id', 'client_location_id'];
        if (!in_array($column_name, $valid_columns)) {
            throw new Exception("Invalid column name");
        }

        $query = "DELETE FROM orders WHERE $column_name ";

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

    function updateOrder($update_column, $new_value, $condition_column, $condition_value, $condition_type = '=', $condition_value2 = null) {
        // Prepare the SQL query based on the condition type
        $valid_columns = ['order_id', 'created', 'done', 'o_status', 'delivery_price', 'delivery_time', 'total_price', 'payment_method',
                            'FKcourier_id', 'FKclient_id', 'shop_location_id', 'client_location_id'];
        if (!in_array($update_column, $valid_columns) || !in_array($condition_column, $valid_columns)) {
            throw new Exception("Invalid column name");
        }
        $sql = "UPDATE orders SET $update_column = ";
        
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