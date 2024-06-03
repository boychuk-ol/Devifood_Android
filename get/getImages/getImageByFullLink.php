<?php

    require_once(dirname(__FILE__, 3).'/repos/DbImageRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['full_link'])) {
            $dbImage = new DbImageRepository();
            $full_link = $_GET['full_link'];

            if ($dbImage->getImageByFullLink($full_link)) {
                $response['error'] = false;
                $response['message'] = 'Image received successfully';
                $response['data'] = $dbImage->getImageByFullLink($full_link);
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: full_link';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>