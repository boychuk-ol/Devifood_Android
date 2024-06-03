<?php

    require_once(dirname(__FILE__, 3).'/DbShopRepository.php');
    require_once(dirname(__FILE__, 3).'/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){
        if (isset($_GET['reviews'])) {
            $dbShop = new DbShopRepository();
            $dbImage = new DbImageRepository();
            $reviews = $_GET['reviews'];

            $shops = $dbShop->getShopsWithReviewsMoreThan($reviews);
            if ($shops) {
                $response['error'] = false;
                $response['message'] = 'Shops received successfully';
                $response['data'] = array();
                foreach ($shops as $shop) {
                    $image = $dbImage->getImageById($shop['FK_image_ID']);
                    $shop['image'] = $image ? $image : null;
    
                    $category = $dbCategory->getCategoryById($shop['FK_category_id']);
                    $shop['category'] = $category ? $category : null;
                    if($category) {
                        $category_image = $dbImage->getImageById($category['FK_image_id']);
                        $shop['category']['image'] = $category_image ? $category_image : null;
                    }
    
                    unset($shop['FK_image_ID'], $shop['FK_category_id'], $shop['category']['FK_image_id']);
                    array_push($response['data'], $shop);
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        } else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: reviews';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>
