<?php

    require_once(dirname(__FILE__, 3).'/repos/DbShopRepository.php');
    require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');
    require_once(dirname(__FILE__, 3).'/repos/DbCategoryRepository.php');

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'GET') {
        if (isset($_GET['product_id'])) {
            $product_id = $_GET['product_id'];
            $dbShop = new DbShopRepository();
            $dbImage = new DbImageRepository();
    
            $shop = $dbShop->getShopByProduct($product_id);
            if ($shop) {
                $response['error'] = false;
                $response['message'] = 'Shop received successfully';
                $image = $dbImage->getImageById($shop['FK_image_ID']);
                if ($image) {
                    $shop['image'] = $image;
                    unset($shop['FK_image_ID']);
                } else {
                    $shop['image'] = null;
                }
                $response['data'] = $shop;
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occurred, please try again';
            }
        } else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: product_id';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }
    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>
