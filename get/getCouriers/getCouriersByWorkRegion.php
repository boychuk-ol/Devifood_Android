<?php

require_once(dirname(__FILE__, 3) . '/repos/DbCourierRepository.php');

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['work_region'])) {
        $dbCourier = new DbCourierRepository();
        $work_region = $_GET['work_region'];

        if ($couriers = $dbCourier->getCouriersByWorkRegion($work_region)) {
            $response['error'] = false;
            $response['message'] = 'Couriers received successfully';
            $response['data'] = $couriers;
        } else {
            $response['error'] = true;
            $response['message'] = 'Some error occurred, please try again';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Missing parameter: work_region';
    }
} else {
    $response['error'] = true;
    $response['message'] = 'Invalid Request';
}

header('Content-Type: application/json');
echo json_encode($response);
exit;

?>
