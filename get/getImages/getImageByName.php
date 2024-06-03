<?php

    require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['name'])) {
            $dbImage = new DbImageRepository();
            $name = $_GET['name'];

            if ($dbImage->getImageByName($name)) {
                $response['error'] = false;
                $response['message'] = 'Image received successfully';
                $response['data'] = $dbImage->getImageByName($name);
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: name';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>