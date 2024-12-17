<?php

    require_once(dirname(__FILE__, 3).'/repos/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['email'])) {
            $dbClient = new DbClientRepository();
            $email = $_GET['email'];

            if ($dbClient->getClientByEmail($email)) {
                $response['error'] = false;
                $response['message'] = 'Client received successfully';
                $response['data'] = $dbClient->getClientByEmail($email);
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: email';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>