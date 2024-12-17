<?php

    require_once(dirname(__FILE__, 3).'/repos/DbCourierRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        $dbCourier = new DbCourierRepository();

        if ($dbCourier->getCouriers()) {
            $response['error'] = false;
            $response['message'] = 'Couriers received successfully';
            $response['data'] = $dbCourier->getCouriers();
        } else {
            $response['error'] = true;
            $response['message'] = 'Some error occcurred please try again';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid Request';
    }

    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>