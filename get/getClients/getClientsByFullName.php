<?php

    require_once(dirname(__FILE__, 3).'/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){
        if(isset($_GET['full_name'])) {
            $dbClient = new DbClientRepository();
            $full_name = $_GET['full_name'];

            if ($dbClient->getClientsByFullName($full_name)) {
                $response['error'] = false;
                $response['message'] = 'Client received successfully';
                $response['data'] = $dbClient->getClientsByFullName($full_name);
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: full_name';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>