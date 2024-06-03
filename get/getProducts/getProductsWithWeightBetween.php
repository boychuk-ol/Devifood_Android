<?php

    require_once(dirname(__FILE__, 3).'/DbProductRepository.php');
    require_once(dirname(__FILE__, 3).'/DbImageRepository.php');
    require_once(dirname(__FILE__, 3).'/DbShopRepository.php');
    require_once(dirname(__FILE__, 3).'/DbCategoryRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){
        if (isset($_GET['min_weight']) && isset($_GET['max_weight'])) {
            $dbProduct = new DbProductRepository();
            $dbImage = new DbImageRepository();
            $dbShop = new DbShopRepository();
            $dbCategory = new DbCategoryRepository();
            $min_weight = $_GET['min_weight'];
            $max_weight = $_GET['max_weight'];

            $products = $dbProduct->getProductsWithWeightBetween($min_weight, $max_weight);
            if ($products) {
                $response['error'] = false;
                $response['message'] = 'Product received successfully';
                $response['data'] = array();
                
                foreach($products as $product) {
                    $image = $dbImage->getImageById($product['FK_image_id']);
                    $product['image'] = $image ? $image : null;
    
                    $shop = $dbShop->getShopById($product['FK_shop_id']);
                    $product['shop'] = $shop ? $shop : null;
                    if($shop) {
                        $shop_image = $dbImage->getImageById($shop['FK_image_ID']);
                        $product['shop']['image'] = $shop_image ? $shop_image : null;
                        $shop_category = $dbCategory->getCategoryById($shop['FK_category_id']);
                        $product['shop']['category'] = $shop_category ? $shop_category : null;
                        if($shop_category) {
                            $shop_category_image = $dbImage->getImageById($shop_category['FK_image_id']);
                            $product['shop']['category']['image'] = $shop_category_image ? $shop_category_image : null;
                            unset($product['shop']['category']['FK_image_id']);
                        }
                        unset($product['shop']['FK_image_ID'] , $product['shop']['FK_category_id'] );
                    }
    
                    $category = $dbCategory->getCategoryById($product['FK_category_id']);
                    $product['category'] = $category ? $category : null;
                    if($category) {
                        $category_image = $dbImage->getImageById($category['FK_image_id']);
                        $product['category']['image'] = $category_image ? $category_image : null;
                        unset($product['category']['FK_image_id']);
                    }
    
                    unset($product['FK_shop_id'], $product['FK_image_id'], $product['FK_category_id']);
                    array_push($response['data'], $product);
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occurred, please try again';
            }
        } else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: min_weight or max_weight';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>
