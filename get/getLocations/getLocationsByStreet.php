<?php

    require_once(dirname(__FILE__, 3).'/DbLocationRepository.php');
    require_once(dirname(__FILE__, 3).'/DbShopRepository.php');
    require_once(dirname(__FILE__, 3).'/DbClientRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['street'])) {
            $dbLocation = new DbLocationRepository();
            $dbShop = new DbShopRepository();
            $dbClient = new DbClientRepository();
            $street =  $_GET['street'];
    
            $locations = $dbLocation->getLocationsByStreet($street); 
            if ($locations) {
                $response['error'] = false;
                $response['message'] = 'Locations received successfully';
                $response['data'] = array();
                foreach ($locations as $location) {
                    $shop = $dbShop->getShopById($location['shop_id']);
                    $client = $dbClient->getClientById($location['client_id']);
                    
                    $location['shop'] = $shop ? $shop : null;
                    $location['client'] = $client ? $client : null;
    
                    unset($location['shop_id'], $location['client_id']);
                    array_push($response['data'], $location);
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: street';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>