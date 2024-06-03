<?php

require_once(dirname(__FILE__, 3).'/repos/DbProductRepository.php');
require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');
require_once(dirname(__FILE__, 3).'/repos/DbShopRepository.php');
require_once(dirname(__FILE__, 3).'/repos/DbCategoryRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){
        if (isset($_GET['weight'])) {
            $dbProduct = new DbProductRepository();
            $dbImage = new DbImageRepository();
            $dbShop = new DbShopRepository();
            $dbCategory = new DbCategoryRepository();
            $weight = $_GET['weight'];

            $products = $dbProduct->getProductsWithWeightMoreThan($weight);
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
            $response['message'] = 'Missing parameter: weight';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>
