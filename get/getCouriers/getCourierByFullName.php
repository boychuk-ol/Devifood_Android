<?php

require_once(dirname(__FILE__, 3) . '/DbCourierRepository.php');

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['full_name'])) {
        $dbCourier = new DbCourierRepository();
        $full_name = $_GET['full_name'];

        if ($couriers = $dbCourier->getCouriersByFullName($full_name)) {
            $response['error'] = false;
            $response['message'] = 'Courier received successfully';
            $response['data'] = $couriers;
        } else {
            $response['error'] = true;
            $response['message'] = 'Some error occurred, please try again';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Missing parameter: full_name';
    }
} else {
    $response['error'] = true;
    $response['message'] = 'Invalid Request';
}

header('Content-Type: application/json');
echo json_encode($response);
exit;

?>
