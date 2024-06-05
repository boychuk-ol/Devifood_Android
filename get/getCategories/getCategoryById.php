<?php

    require_once(dirname(__FILE__, 3).'/repos/DbCategoryRepository.php');
    require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');
    

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['category_id'])) {
            $dbCategory = new DbCategoryRepository();
            $dbImage = new DbImageRepository();
            $category_id = $_GET['category_id'];

            $category = $dbCategory->getCategoryById($category_id);
            if ($category) {
                $response['error'] = false;
                $response['message'] = 'Category received successfully';
                $image = $dbImage->getImageById($category['FK_image_id']);
                $category['image'] = $image ? $image : null;
                unset($category['FK_image_id']);
                $response['data'] = $category;
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: category_id';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>