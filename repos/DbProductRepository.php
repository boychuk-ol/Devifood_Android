<?php

class DbProductRepository {

    private $con;

    function __construct(){
        require_once(dirname(__FILE__, 2) . "/DbConnection.php");

        $db = new DbConnection();

        $this->con = $db->connect();
    }

    function getProducts() {
        $response = $this->con->query("SELECT * FROM products");
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductById($product_id) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE product_id = ?");
        $stmt->bind_param("i", $product_id);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = $response->fetch_assoc();

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductByCode($product_code) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE product_code = ?");
        $stmt->bind_param("s", $product_code);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = $response->fetch_assoc();

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductsByProducer($producer) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE producer = ?");
        $stmt->bind_param("s", $producer);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    
    function getProductsByShop($shop_id) {
        $stmt = $this->con->prepare("SELECT s.*
                                    FROM products s
                                    JOIN shop c ON s.FK_shop_id = c.shop_id
                                    WHERE c.shop_id = ?;");
        $stmt->bind_param("s", $shop_id);
        $stmt->execute();
        $response = $stmt->get_result();
        
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductsByCategory($category_id) {
        $stmt = $this->con->prepare("SELECT s.*
                                    FROM products s
                                    JOIN categories c ON s.FK_category_id = c.category_id
                                    WHERE c.category_id = ?;");
        $stmt->bind_param("i", $category_id);
        $stmt->execute();
        $response = $stmt->get_result();
        
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);
        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductBySubcategory($subcategory) {
        $stmt = $this->con->prepare("SELECT s.*
                                    FROM products s
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

    function getProductBySubsubcategory($subsubcategory) {
        $stmt = $this->con->prepare("SELECT s.*
                                    FROM products s
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

    function getProductsByCountry($country) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE country = ?");
        $stmt->bind_param("s", $country);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductsByOrganic($organic) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE organic = ?");
        $stmt->bind_param("i", $organic);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductsByVegetarian($for_vegeterians) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE for_vegeterians = ?");
        $stmt->bind_param("i", $for_vegeterians);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductsByVegan($for_vegans) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE for_vegans = ?");
        $stmt->bind_param("i", $for_vegans);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductsByRating($rating) {
        $tolerance = 0.0001; // You can adjust this tolerance level
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

    function getProductsWithRatingBetween($min_rating, $max_rating) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE rating BETWEEN ? AND ?");
        $stmt->bind_param("dd", $min_rating, $max_rating);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsWithRatingLessThan($rating) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE rating < ?");
        $stmt->bind_param("d", $rating);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsWithRatingMoreThan($rating) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE rating > ?");
        $stmt->bind_param("d", $rating);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsByWeight($weight) {
        $tolerance = 0.0001;
        $lower_bound = $weight - $tolerance;
        $upper_bound = $weight + $tolerance;
    
        $stmt = $this->con->prepare("SELECT * FROM products WHERE weight BETWEEN ? AND ?");
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

    function getProductsWithWeightBetween($min_weight, $max_weight) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE weight BETWEEN ? AND ?");
        $stmt->bind_param("dd", $min_weight, $max_weight);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsWithWeightLessThan($weight) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE weight < ?");
        $stmt->bind_param("d", $weight);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsWithWeightMoreThan($weight) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE weight > ?");
        $stmt->bind_param("d", $weight);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsByCaloriesTotal($calories_total) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE calories_total = ?");
        $stmt->bind_param("i", $calories_total);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    function getProductsWithCaloriesTotalBetween($min_calories, $max_calories) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE calories_total BETWEEN ? AND ?");
        $stmt->bind_param("ii", $min_calories, $max_calories);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsWithCaloriesTotalLessThan($calories) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE calories_total < ?");
        $stmt->bind_param("i", $calories);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsWithCaloriesTotalMoreThan($calories) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE calories_total > ?");
        $stmt->bind_param("i", $calories);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsByPrice($price) {
        $tolerance = 0.0001; // You can adjust this tolerance level
        $lower_bound = $price - $tolerance;
        $upper_bound = $price + $tolerance;
    
        $stmt = $this->con->prepare("SELECT * FROM products WHERE price BETWEEN ? AND ?");
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

    function getProductsWithPriceBetween($min_price, $max_price) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE actual_price BETWEEN ? AND ?");
        $stmt->bind_param("dd", $min_price, $max_price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsWithPriceLessThan($price) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE actual_price < ?");
        $stmt->bind_param("d", $price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function getProductsWithPriceMoreThan($price) {
        $stmt = $this->con->prepare("SELECT * FROM products WHERE actual_price > ?");
        $stmt->bind_param("d", $price);
        $stmt->execute();
        $response = $stmt->get_result();
        $result = mysqli_fetch_all($response, MYSQLI_ASSOC);

        return $result ?: false;
    }

    function createProduct($product) {
        $stmt = $this->con->prepare("INSERT INTO products(`product_code`, `description`, `full_title`, `short_title`, `price_with_no_discount`, `actual_price`, `fats_per_100g`, `proteins_per_100g`, `carbohydrates_per_100g`, `calories_per_100g`, `calories_total`, `ingridients`, `producer`, `country`, `organic`, `for_vegeterians`, `for_vegans`, `weight`, `liters`, `numb_of_items`, `rating`, `FK_image_id`, `FK_shop_id`, `FK_category_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssssddddddisssiiiddidiii", $product->product_code, 
                                                        $product->description, 
                                                        $product->full_title,
                                                        $product->short_title,
                                                        $product->price_with_no_discount,
                                                        $product->actual_price,
                                                        $product->fats_per_100g,
                                                        $product->proteins_per_100g,
                                                        $product->carbohydrates_per_100g,
                                                        $product->calories_per_100g,
                                                        $product->calories_total,
                                                        $product->ingredients,
                                                        $product->producer,
                                                        $product->country,
                                                        $product->organic,
                                                        $product->for_vegetarians,
                                                        $product->for_vegans,
                                                        $product->weight,
                                                        $product->liters,
                                                        $product->numb_of_items,
                                                        $product->rating,
                                                        $product->FK_image_id,
                                                        $product->FK_shop_id,
                                                        $product->FK_category_id);
        $stmt->execute();
    }

    function deleteProduct($column_name, $value, $condition_type = '=', $value2 = null) {
        $valid_columns = ['product_id', 'product_code', 'description', 'full_title', 'short_title', 'price_with_no_discount', 'actual_price', 'fats_per_100g',
        'proteins_per_100g', 'carbohydrates_per_100g', 'calories_per_100g', 'calories_total', 'ingridients', 'producer', 'country', 'organic', 'for_vegans',
        'for_vegeterians', 'weight', 'liters', 'numb_of_items', 'rating', 'FK_image_id', 'FK_shop_id', 'FK_category_id'];
        if (!in_array($column_name, $valid_columns)) {
            throw new Exception("Invalid column name");
        }

        if($this->getColumnType($column_name) == 'd' && $condition_type == '=') {
            $condition_value = $value;
            $tolerance = 0.0001;
            $value = $condition_value - $tolerance;
            $value2 = $condition_value + $tolerance;
            $condition_type = 'BETWEEN';
        }

        $query = "DELETE FROM products WHERE $column_name ";

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

    function updateProduct($update_column, $new_value, $condition_column, $condition_value, $condition_type = '=', $condition_value2 = null) {
        // Prepare the SQL query based on the condition type
        $valid_columns = ['product_id', 'product_code', 'description', 'full_title', 'short_title', 'price_with_no_discount', 'actual_price', 'fats_per_100g',
        'proteins_per_100g', 'carbohydrates_per_100g', 'calories_per_100g', 'calories_total', 'ingridients', 'producer', 'country', 'organic', 'for_vegans',
        'for_vegeterians', 'weight', 'liters', 'numb_of_items', 'rating', 'FK_image_id', 'FK_shop_id', 'FK_category_id'];
        if (!in_array($update_column, $valid_columns) || !in_array($condition_column, $valid_columns)) {
            throw new Exception("Invalid column name");
        }

        if($this->getColumnType($update_column) == 'd' && $condition_type == '=') {
            $tolerance = 0.0001;
            $condition_value = $new_value - $tolerance;
            $condition_value2 = $new_value + $tolerance;
            $condition_type = 'BETWEEN';
        }

        $sql = "UPDATE products SET $update_column = ";
            
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
        $query = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'product' AND COLUMN_NAME = ?";
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