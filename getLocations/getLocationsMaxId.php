<?php

    require_once(dirname(__FILE__, 3).'/repos/DbLocationRepository.php');

    $response = array();

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        $dbLocation = new DbLocationRepository();

        $location_id = $dbLocation->getLocationsMaxId(); 
        if ($location_id !== null) {
            $response['error'] = false;
            $response['message'] = 'Locations max id received successfully';
            $response['location_id'] = $location_id;
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