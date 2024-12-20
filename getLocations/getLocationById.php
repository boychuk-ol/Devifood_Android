<?php

    require_once(dirname(__FILE__, 3).'/repos/DbLocationRepository.php');
    require_once(dirname(__FILE__, 3).'/repos/DbShopRepository.php');
    require_once(dirname(__FILE__, 3).'/repos/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['location_id'])) {
            $dbLocation = new DbLocationRepository();
            $dbShop = new DbShopRepository();
            $dbClient = new DbClientRepository();
            $location_id = $_GET['location_id'];

            $location = $dbLocation->getLocationById($location_id); 
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
            $response['message'] = 'Missing parameter: location_id';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>