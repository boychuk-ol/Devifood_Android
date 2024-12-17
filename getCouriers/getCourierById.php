<?php

    require_once(dirname(__FILE__, 3).'/repos/DbCourierRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        if(isset($_GET['courier_id'])) {
            $dbCourier = new DbCourierRepository();
            $courier_id = $_GET['courier_id'];

            if ($dbCourier->getCourierById($courier_id)) {
                $response['error'] = false;
                $response['message'] = 'Courier received successfully';
                $response['data'] = $dbCourier->getCourierById($courier_id);
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occcurred please try again';
            }
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Missing parameter: courier_id';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>