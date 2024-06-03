<?php

    require_once(dirname(__FILE__, 3).'/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['client_id'])) {
            $dbClient = new DbClientRepository();
            $client_id = $_GET['client_id'];

            if ($dbClient->getClientById($client_id)) {
                $response['error'] = false;
                $response['message'] = 'Client received successfully';
                $response['data'] = $dbClient->getClientById($client_id);
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