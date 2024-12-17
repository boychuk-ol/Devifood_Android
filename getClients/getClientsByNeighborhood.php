<?php

    require_once(dirname(__FILE__, 3).'/repos/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){
        if(isset($_GET['neighborhood'])) {
            $dbClient = new DbClientRepository();
            $neighborhood = $_GET['neighborhood'];

            if ($dbClient->getClientsByNeighborhood($neighborhood)) {
                $response['error'] = false;
                $response['message'] = 'Client received successfully';
                $response['data'] = $dbClient->getClientsByNeighborhood($neighborhood);
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: neighborhood';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>