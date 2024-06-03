<?php

    require_once(dirname(__FILE__, 3).'/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        $dbClient = new DbClientRepository();

        if ($dbClient->getClients()) {
            $response['error'] = false;
            $response['message'] = 'Clients received successfully';
            $response['data'] = $dbClient->getClients();
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