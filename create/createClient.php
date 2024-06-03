<?php

    class Client {
        // Properties
        public $client_id;
        public $phone_number;
        public $email;
        public $full_name;

        // Constructor
        public function __construct($phone_number, $email, $full_name) {
            $this->phone_number = $phone_number;
            $this->email = $email;
            $this->full_name = $full_name;
        }
    }

    require_once(dirname(__FILE__, 2).'/DbClientRepository.php');

    echo $_SERVER['REQUEST_METHOD'];
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        if(!isset($_POST['phone_number'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter phone_number';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['full_name'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter full_name';
            echo json_encode($response);
            exit;
        } else {
            $dbClient = new DbClientRepository();
            $client = new Client($_POST['phone_number'], 
                                    $_POST['email'] ?? null, 
                                    $_POST['full_name']);
            $create_client = $dbClient->createClient($client);
            if($create_client) {
                $response['error'] = false;
                $response['message'] = 'Client created successfully';
                $response['data'] = $client;
            } else {
                $response['error'] = true;
                $response['message'] = 'Client not created';
            }
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }

    header('Content-Type: application/json');
    echo json_encode($client);
    exit;
?>