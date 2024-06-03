<?php

    require_once(dirname(__FILE__, 3).'/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['image_id'])) {
            $dbImage = new DbImageRepository();
            $image_id = $_GET['image_id'];

            if ($dbImage->getImageById($image_id)) {
                $response['error'] = false;
                $response['message'] = 'Image received successfully';
                $response['data'] = $dbImage->getImageById($image_id);
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: image_id';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>