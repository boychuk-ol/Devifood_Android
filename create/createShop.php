<?php

    class Shop {
        // Properties
        public $shop_id;
        public $shop_name;
        public $rating;
        public $reviews;
        public $FK_image_id;
        public $FK_category_id;

        // Constructor
        public function __construct($shop_name, $rating, $reviews, $FK_image_id, $FK_category_id) {
            $this->shop_name = $shop_name;
            $this->rating = $rating;
            $this->reviews = $reviews;
            $this->FK_image_id = $FK_image_id;
            $this->FK_category_id = $FK_category_id;
        }
    }

    require_once(dirname(__FILE__, 2).'/repos/DbShopRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbImageRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbCategoryRepository.php');


    echo $_SERVER['REQUEST_METHOD'];
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $dbImage = new DbImageRepository();
        $dbShop = new DbShopRepository();
        $dbCategory = new DbCategoryRepository();
        if(!isset($_POST['shop_name'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter shop_name';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['FK_image_id'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter FK_image_id';
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
        } elseif(!$dbCategory->getCategoryById($_POST['FK_category_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing category";
            echo json_encode($response);
            exit;
        } else {
            $shop = $shop = new Shop($_POST['shop_name'],
                                        $_POST['rating'] ?? null,
                                        $_POST['reviews'] ?? null,
                                        $_POST['FK_image_id'],
                                        $_POST['FK_category_id']);
            $create_shop = $dbShop->createShop($shop);
            if($create_shop) {
                $response['error'] = false;
                $response['message'] = 'Shop created successfully';
                $response['data'] = $shop;
            } else {
                $response['error'] = true;
                $response['message'] = 'Shop not created';
            }
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }

    header('Content-Type: application/json');
    echo json_encode($shop);
    exit;
?>