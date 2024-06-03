<?php

    require_once(dirname(__FILE__, 3).'/DbCategoryRepository.php');
    require_once(dirname(__FILE__, 3).'/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['entity_name'])) {
            $DbCategory = new DbCategoryRepository();
            $dbImage = new DbImageRepository();
            $entity = $_GET['entity_name'];

            $categories = $DbCategory->getCategoriesByEntity($entity);
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
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: entity_name';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>