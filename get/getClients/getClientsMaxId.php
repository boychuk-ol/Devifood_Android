<?php

    require_once(dirname(__FILE__, 3).'/repos/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        $dbClient = new DbClientRepository();

        $client_id = $dbClient->getClientMaxId(); 
        if ($client_id !== null) {
            $response['error'] = false;
            $response['message'] = 'Clients max id received successfully';
            $response['client_id'] = $client_id;
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