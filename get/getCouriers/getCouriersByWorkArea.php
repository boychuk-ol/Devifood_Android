<?php

require_once(dirname(__FILE__, 3) . '/DbCourierRepository.php');

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['work_area'])) {
        $dbCourier = new DbCourierRepository();
        $work_area = $_GET['work_area'];

        if ($couriers = $dbCourier->getCouriersByWorkArea($work_area)) {
            $response['error'] = false;
            $response['message'] = 'Couriers received successfully';
            $response['data'] = $couriers;
        } else {
            $response['error'] = true;
            $response['message'] = 'Some error occurred, please try again';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Missing parameter: work_area';
    }
} else {
    $response['error'] = true;
    $response['message'] = 'Invalid Request';
}

header('Content-Type: application/json');
echo json_encode($response);
exit;

?>
