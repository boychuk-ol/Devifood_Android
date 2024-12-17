<?php

    require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['extension'])) {
            $dbImage = new DbImageRepository();
            $extension = $_GET['extension'];

            if ($dbImage->getImagesByExtension($extension)) {
                $response['error'] = false;
                $response['message'] = 'Images received successfully';
                $response['data'] = $dbImage->getImagesByExtension($extension);
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