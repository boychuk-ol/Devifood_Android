<?php

    require_once(dirname(__FILE__, 3).'/DbLocationRepository.php');
    require_once(dirname(__FILE__, 3).'/DbShopRepository.php');
    require_once(dirname(__FILE__, 3).'/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['full_address'])) {
            $dbLocation = new DbLocationRepository();
            $dbShop = new DbShopRepository();
            $dbClient = new DbClientRepository();
            $full_address = $_GET['full_address'];

            $location = $dbLocation->getLocationByFullAdress($full_address); 
            if ($location) {
                $response['error'] = false;
                $response['message'] = 'Location received successfully';
                
                $shop = $dbShop->getShopById($location['shop_id']);
                $client = $dbClient->getClientById($location['client_id']);
                
                $location['shop'] = $shop ? $shop : null;
                $location['client'] = $client ? $client : null;
                
                unset($location['shop_id'], $location['client_id']);
                $response['data'] = $location;
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: full_address';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>