<?php

    class Courier {
        // Properties
        public $courier_id;
        public $full_name;
        public $phone;
        public $email;
        public $work_region;
        public $work_area;

        // Constructor
        public function __construct($full_name, $phone, $email, $work_region, $work_area) {
            $this->full_name = $full_name;
            $this->phone = $phone;
            $this->email = $email;
            $this->work_region = $work_region;
            $this->work_area = $work_area;
        }
    }

    require_once(dirname(__FILE__, 2).'/DbCourierRepository.php');

    echo $_SERVER['REQUEST_METHOD'];
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        if(!isset($_POST['full_name'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter full_name';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['phone'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter phone';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['work_region'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter work_region';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['work_area'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter work_area';
            echo json_encode($response);
            exit;
        } else {
            $dbCourier = new DbCourierRepository();
            $courier = new Courier($_POST['full_name'], 
                                    $_POST['phone'], 
                                    $_POST['email'], 
                                    $_POST['work_region'], 
                                    $_POST['work_area']);
            $create_courier = $dbCourier->createCourier($courier);
            if($create_courier) {
                $response['error'] = false;
                $response['message'] = 'Courier created successfully';
                $response['data'] = $courier;
            } else {
                $response['error'] = true;
                $response['message'] = 'Courier not created';
            }
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }

    header('Content-Type: application/json');
    echo json_encode($courier);
    exit;
?>