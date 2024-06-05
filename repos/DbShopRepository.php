<?php

    class DbShopRepository {

        private $con;

        function __construct(){
            require_once(dirname(__FILE__, 2) . "/DbConnection.php");

            $db = new DbConnection();

            $this->con = $db->connect();
        }


        function createShop($shop_name, $category, $rating, $reviews) {
            $stmt = $this->con->prepare("INSERT INTO shop (shop_name, category, rating, reviews) VALUES (?, ?, ?, ?);");
            
            $stmt->bind_param("ssdi", $shop_name, $category, $rating, $reviews);

            if($stmt->execute()){
                return true;
            } else {
                return false;
            }
        }

        function getShops() {
            $response = $this->con->query("SELECT * FROM shop");
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopById($shop_ID) {
            $stmt = $this->con->prepare("SELECT * FROM shop where shop_id = ?");
            $stmt->bind_param("i", $shop_ID);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = $response->fetch_assoc();

            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopsByAddress($address) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM shop s
                                        JOIN locations c ON s.shop_id = c.shop_id
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

        function getShopsByCity($city) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM shop s
                                        JOIN locations c ON s.shop_id = c.shop_id
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

        function getShopsByNeighborhood($neighborhood) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM shop s
                                        JOIN locations c ON s.shop_id = c.shop_id
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

        function getShopsByStreet($street) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM shop s
                                        JOIN locations c ON s.shop_id = c.shop_id
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

        function getShopsByProduct($product_id) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM shop s
                                        JOIN products c ON s.FK_shop_id = c.shop_id
                                        WHERE c.product_id = ?;");
            $stmt->bind_param("s", $product_id);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = $response->fetch_assoc();
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopsByCategory($category) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM shop s
                                        JOIN categories c ON s.FK_category_id = c.category_id
                                        WHERE c.c_name = ?;");
            $stmt->bind_param("s", $category);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopsBySubcategory($subcategory) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM shop s
                                        JOIN categories c ON s.FK_category_id = c.category_id
                                        WHERE c.subcat_name = ?;");
            $stmt->bind_param("s", $subcategory);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopsBySubsubcategory($subsubcategory) {
            $stmt = $this->con->prepare("SELECT s.*
                                        FROM shop s
                                        JOIN categories c ON s.FK_category_id = c.category_id
                                        WHERE c.subsubcat_name = ?;");
            $stmt->bind_param("s", $subsubcategory);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopByName($name) {
            $stmt = $this->con->prepare("SELECT * FROM shop WHERE shop_name = ?");
            $stmt->bind_param("s", $name);
            $stmt->execute();
            $response = $stmt->get_result();
            $result = $response->fetch_assoc();
            
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getProductsByRating($rating) {
            $tolerance = 0.0001;
            $lower_bound = $rating - $tolerance;
            $upper_bound = $rating + $tolerance;
        
            $stmt = $this->con->prepare("SELECT * FROM products WHERE rating BETWEEN ? AND ?");
            if (!$stmt) {
                error_log("Prepare failed: " . $this->con->error);
                return false;
            }
        
            $stmt->bind_param("dd", $lower_bound, $upper_bound);
            if (!$stmt->execute()) {
                error_log("Execute failed: " . $stmt->error);
                return false;
            }
        
            $response = $stmt->get_result();
            if (!$response) {
                error_log("Get result failed: " . $stmt->error);
                return false;
            }
        
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
        
            return $result ? $result : false;
        }

        function getShopsWithRatingMoreThan($rating) {
            $stmt = $this->con->prepare("SELECT * FROM shop WHERE rating > ?");
            $stmt->bind_param("d", $rating);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopsWithRatingLessThan($rating) {
            $stmt = $this->con->prepare("SELECT * FROM shop WHERE rating < ?");
            $stmt->bind_param("d", $rating);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getProductsByReviews($reviews) {
            $tolerance = 0.0001;
            $lower_bound = $reviews - $tolerance;
            $upper_bound = $reviews + $tolerance;
        
            $stmt = $this->con->prepare("SELECT * FROM products WHERE reviews BETWEEN ? AND ?");
            if (!$stmt) {
                error_log("Prepare failed: " . $this->con->error);
                return false;
            }
        
            $stmt->bind_param("dd", $lower_bound, $upper_bound);
            if (!$stmt->execute()) {
                error_log("Execute failed: " . $stmt->error);
                return false;
            }
        
            $response = $stmt->get_result();
            if (!$response) {
                error_log("Get result failed: " . $stmt->error);
                return false;
            }
        
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
        
            return $result ? $result : false;
        }

        function getShopsWithRatingBetween($min_rating, $max_rating) {
            $stmt = $this->con->prepare("SELECT * FROM shop WHERE rating BETWEEN ? AND ?");
            $stmt->bind_param("dd", $min_rating, $max_rating);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopsWithReviewsMoreThan($reviews) {
            $stmt = $this->con->prepare("SELECT * FROM shop WHERE reviews > ?");
            $stmt->bind_param("i", $reviews);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopsWithReviewsLessThan($reviews) {
            $stmt = $this->con->prepare("SELECT * FROM shop WHERE reviews < ?");
            $stmt->bind_param("i", $reviews);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function getShopsWithReviewsBetween($min_reviews, $max_reviews) {
            $stmt = $this->con->prepare("SELECT * FROM shop WHERE reviews BETWEEN ? AND ?");
            $stmt->bind_param("ii", $min_reviews, $max_reviews);
            $stmt->execute();
            $response = $stmt->get_result();
            
            $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
            if ($result) {
                return $result;
            } else {
                return false;
            }
        }

        function deleteProduct($column_name, $value, $condition_type = '=', $value2 = null) {
            $valid_columns = ['shop_id', 'shop_name', 'rating', 'reviews', 'FK_image_ID', 'FK_category_id'];
            if (!in_array($column_name, $valid_columns)) {
                throw new Exception("Invalid column name");
            }
    
            $query = "DELETE FROM shop WHERE $column_name ";
    
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

        function updateShop($update_column, $new_value, $condition_column, $condition_value, $condition_type = '=', $condition_value2 = null) {
            // Prepare the SQL query based on the condition type
            $valid_columns = ['shop_id', 'shop_name', 'rating', 'reviews', 'FK_image_ID', 'FK_category_id'];
            if (!in_array($update_column, $valid_columns) || !in_array($condition_column, $valid_columns)) {
                throw new Exception("Invalid column name");
            }
            $sql = "UPDATE shop SET $update_column = ";
            
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