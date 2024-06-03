<?php

    require_once(dirname(__FILE__, 3).'/repos/DbShopRepository.php');
    require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');
    require_once(dirname(__FILE__, 3).'/repos/DbCategoryRepository.php');

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'GET') {
        if (isset($_GET['shop_id'])) {
            $shop_id = $_GET['shop_id'];
            $dbShop = new DbShopRepository();
            $dbImage = new DbImageRepository();
            $dbCategory = new DbCategoryRepository();
    
            $shop = $dbShop->getShopById($shop_id);
            if ($shop) {
                $response['error'] = false;
                $response['message'] = 'Shop received successfully';

                $image = $dbImage->getImageById($shop['FK_image_ID']);
                $shop['image'] = $image ? $image : null;

                $category = $dbCategory->getCategoryById($shop['FK_category_id']);
                $shop['category'] = $category ? $category : null;
                if($category) {
                    $category_image = $dbImage->getImageById($category['FK_image_id']);
                    $shop['category']['image'] = $category_image ? $category_image : null;
                }

                unset($shop['FK_image_ID'], $shop['FK_category_id'], $shop['category']['FK_image_id']);
                $response['data'] = $shop;
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occurred, please try again';
            }
        } else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: shop_id';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }
    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>
