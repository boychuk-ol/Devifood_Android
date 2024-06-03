<?php

    require_once(dirname(__FILE__, 3).'/DbClientRepository.php');
    require_once(dirname(__FILE__, 3).'/DbImageRepository.php');
    

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['client_id'])) {
            $dbClient = new DbClientRepository();
            $dbImage = new DbImageRepository();
            $client_id = $_GET['client_id'];

            $client = $dbClient->getClientById($client_id);
            if ($client) {
                $response['error'] = false;
                $response['message'] = 'Client received successfully';
                $response['data'] = $dbClient->getClientById($client_id);
                $image = $dbImage->getImageById($client['FK_image_id']);
                if ($image) {
                    $client['image'] = $image;
                    unset($client['FK_image_id']);
                } else {
                    $client['image'] = null;
                }
                $response['data'] = $client;
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: client_id';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>