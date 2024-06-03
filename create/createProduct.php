<?php

    class Product {
        // Properties
        public $product_id;
        public $product_code;
        public $description;
        public $full_title;
        public $short_title;
        public $price_with_no_discount;
        public $actual_price;
        public $fats_per_100g;
        public $proteins_per_100g;
        public $carbohydrates_per_100g;
        public $calories_per_100g;
        public $calories_total;
        public $ingridients;
        public $producer;
        public $country;
        public $organic;
        public $for_vegeterians;
        public $for_vegans;
        public $weight;
        public $liters;
        public $numb_of_items;
        public $rating;
        public $FK_image_id;
        public $FK_shop_id;
        public $FK_category_id;

        // Constructor
        public function __construct(
            $product_code,
            $description,
            $full_title,
            $short_title,
            $price_with_no_discount,
            $actual_price,
            $fats_per_100g,
            $proteins_per_100g,
            $carbohydrates_per_100g,
            $calories_per_100g,
            $calories_total,
            $ingredients,
            $producer,
            $country,
            $organic,
            $for_vegetarians,
            $for_vegans,
            $weight,
            $liters,
            $numb_of_items,
            $rating,
            $FK_image_id,
            $FK_shop_id,
            $FK_category_id
        ) {
            $this->product_code = $product_code;
            $this->description = $description;
            $this->full_title = $full_title;
            $this->short_title = $short_title;
            $this->price_with_no_discount = $price_with_no_discount;
            $this->actual_price = $actual_price;
            $this->fats_per_100g = $fats_per_100g;
            $this->proteins_per_100g = $proteins_per_100g;
            $this->carbohydrates_per_100g = $carbohydrates_per_100g;
            $this->calories_per_100g = $calories_per_100g;
            $this->calories_total = $calories_total;
            $this->ingredients = $ingredients;
            $this->producer = $producer;
            $this->country = $country;
            $this->organic = $organic;
            $this->for_vegetarians = $for_vegetarians;
            $this->for_vegans = $for_vegans;
            $this->weight = $weight;
            $this->liters = $liters;
            $this->numb_of_items = $numb_of_items;
            $this->rating = $rating;
            $this->FK_image_id = $FK_image_id;
            $this->FK_shop_id = $FK_shop_id;
            $this->FK_category_id = $FK_category_id;
        }
    }

    require_once(dirname(__FILE__, 2).'/DbProductRepository.php');
    require_once(dirname(__FILE__, 2).'/DbImageRepository.php');
    require_once(dirname(__FILE__, 2).'/DbShopRepository.php');
    require_once(dirname(__FILE__, 2).'/DbCategoryRepository.php');


    $response = array();
    echo $_SERVER['REQUEST_METHOD'];
    if ($_SERVER['REQUEST_METHOD'] == 'POST'){
        $dbImage = new DbImageRepository();
        $dbShop = new DbShopRepository();
        $dbCategory = new DbCategoryRepository();
        if(!isset($_POST['product_code'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter product_code';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['full_title'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter full_title';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['FK_image_id'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter FK_image_id';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['FK_shop_id'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter FK_shop_id';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['FK_category_id'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter FK_category_id';
            echo json_encode($response);
            exit;
        } elseif(!$dbImage->getImageById($_POST['FK_image_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing image";
            echo json_encode($response);
            exit;
        } elseif(!$dbShop->getShopById($_POST['FK_shop_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing product";
            echo json_encode($response);
            exit;
        } elseif(!$dbCategory->getCategoryById($_POST['FK_category_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing category";
            echo json_encode($response);
            exit;
        } else {
            $dbProduct = new DbProductRepository();
            $product = new Product(
                $_POST['product_code'],
                $_POST['description'] ?? null,
                $_POST['full_title'],
                $_POST['short_title'] ?? null,
                $_POST['price_with_no_discount'] ?? null,
                $_POST['actual_price'],
                $_POST['fats_per_100g'] ?? null,
                $_POST['proteins_per_100g'] ?? null,
                $_POST['carbohydrates_per_100g'] ?? null,
                $_POST['calories_per_100g'] ?? null,
                $_POST['calories_total'] ?? null,
                $_POST['ingredients'] ?? null,
                $_POST['producer'] ?? null,
                $_POST['country'] ?? null,
                $_POST['organic'] ?? null,
                $_POST['for_vegetarians'] ?? null,
                $_POST['for_vegans'] ?? null,
                $_POST['weight'] ?? null,
                $_POST['liters'] ?? null,
                $_POST['numb_of_items'] ?? null,
                $_POST['rating'] ?? null,
                $_POST['FK_image_id'],
                $_POST['FK_shop_id'],
                $_POST['FK_category_id']
            );
            
            $create_product = $dbProduct->createProduct($product);

            if ($create_product) {
                $response['error'] = false;
                $response['message'] = 'Product created successfully';
                $response['data'] = $product;
            } else {
                $response['error'] = true;
                $response['message'] = 'Product not created';
            }
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }
    
    header('Content-Type: application/json');
    echo json_encode($product);
    exit;
?>