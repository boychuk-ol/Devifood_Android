<?php

require_once(dirname(__FILE__, 3) . '/repos/DbCourierRepository.php');

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['phone'])) {
        $dbCourier = new DbCourierRepository();
        $phone = $_GET['phone'];

        if ($couriers = $dbCourier->getCourierByPhone($phone)) {
            $response['error'] = false;
            $response['message'] = 'Courier received successfully';
            $response['data'] = $couriers;
        } else {
            $response['error'] = true;
            $response['message'] = 'Some error occurred, please try again';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Missing parameter: phone';
    }
} else {
    $response['error'] = true;
    $response['message'] = 'Invalid Request';
}

header('Content-Type: application/json');
echo json_encode($response);
exit;

?>
