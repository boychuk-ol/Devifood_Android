<?php

    class Location {
        // Properties
        public $location_id;
        public $full_address;
        public $city;
        public $neighborhood;
        public $street;
        public $street_number;
        public $shop_id;
        public $client_id;

        // Constructor
        public function __construct($full_address, $city, $neighborhood, $street, $street_number, $shop_id, $client_id) {
            $this->full_address = $full_address;
            $this->city = $city;
            $this->neighborhood = $neighborhood;
            $this->street = $street;
            $this->street_number = $street_number;
            $this->shop_id = $shop_id;
            $this->client_id = $client_id;
        }
    }

    require_once(dirname(__FILE__, 2).'/repos/DbLocationRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbClientRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbShopRepository.php');

    echo $_SERVER['REQUEST_METHOD'];
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $dbShop = new DbShopRepository();
        $dbClient = new DbClientRepository();
        if(!isset($_POST['full_address'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter full_address';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['city'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter city';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['neighborhood'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter neighborhood';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['street'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter street';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['street_number'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter street_number';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['shop_id']) && !isset($_POST['client_id'])) {
            $response['error'] = true;
            $response['message'] = 'shop_id or client_id must be initialized';
            echo json_encode($response);
            exit;
        } elseif(isset($_POST['shop_id']) && isset($_POST['client_id'])) {
            $response['error'] = true;
            $response['message'] = "Can't be initialized shop_id and client_id at the same time";
            echo json_encode($response);
            exit;
        } elseif(!$dbClient->getClientById($_POST['client_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing client";
            echo json_encode($response);
            exit;
        } elseif(!$dbShop->getShopById($_POST['shop_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing shop";
            echo json_encode($response);
            exit;
        } else {
            $dbLocation = new DbLocationRepository();
            $location = new Location($_POST['full_address'], 
                                    $_POST['city'], 
                                    $_POST['neighborhood'], 
                                    $_POST['street'],
                                    $_POST['street_number'], 
                                    $_POST['shop_id'] ?? null,
                                    $_POST['client_id'] ?? null);
            $create_location = $dbLocation->createLocation($location);
            if($create_location) {
                $response['error'] = false;
                $response['message'] = 'Location created successfully';
                $response['data'] = $location;
            } else {
                $response['error'] = true;
                $response['message'] = 'Location not created';
            }
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }

    header('Content-Type: application/json');
    echo json_encode($location);
    exit;
?>