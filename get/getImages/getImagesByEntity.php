<?php

    require_once(dirname(__FILE__, 3).'/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['entity_name'])) {
            $dbImage = new DbImageRepository();
            $entity_name = $_GET['entity_name'];

            if ($dbImage->getImageByEntity($entity_name)) {
                $response['error'] = false;
                $response['message'] = 'Images received successfully';
                $response['data'] = $dbImage->getImageByEntity($entity_name);
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