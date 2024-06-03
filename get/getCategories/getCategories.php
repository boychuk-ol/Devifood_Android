<?php

    require_once(dirname(__FILE__, 3).'/repos/DbCategoryRepository.php');
    require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        $dbCategory = new DbCategoryRepository();
        $dbImage = new DbImageRepository();

        $categories = $dbCategory->getCategories();
        if ($categories) {
            $response['error'] = false;
            $response['message'] = 'Categories received successfully';
            $response['data'] = array(); 
            foreach ($categories as $category) {
                $image = $dbImage->getImageById($category['FK_image_id']);
                $category['image'] = $image ? $image : null;
                unset($category['FK_image_id']);
                array_push($response['data'], $category);
            }
        } else {
            
            $response['error'] = true;
            $response['message'] = 'Some error occcurred please try again';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>