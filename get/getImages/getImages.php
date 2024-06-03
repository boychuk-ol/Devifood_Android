<?php

    require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        $dbImage = new DbImageRepository();

        if ($dbImage->getImages()) {
            $response['error'] = false;
            $response['message'] = 'Images received successfully';
            $response['data'] = $dbImage->getImages();
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