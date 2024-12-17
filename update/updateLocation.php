<?php

    require_once(dirname(__FILE__, 2) . '/repos/DbLocationRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbClientRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbShopRepository.php');

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        if (isset($_POST['update_column'], $_POST['new_value'], $_POST['condition_column'], $_POST['condition_value'], $_POST['condition_type'])) {
            $update_column = $_POST['update_column'];
            $new_value = $_POST['new_value'];
            $condition_column = $_POST['condition_column'];
            $condition_value = $_POST['condition_value'];
            $condition_type = $_POST['condition_type'];
            $condition_value2 = isset($_POST['condition_value2']) ? $_POST['condition_value2'] : null;

            $dbLocation = new DbLocationRepository();
            $dbShop = new DbShopRepository();
            $dbClient = new DbClientRepository();

            try {
                if (strtoupper($new_value) === 'NULL') {
                    $new_value = null;
                } elseif ($update_column == 'shop_id' && !$dbShop->getShopById($new_value)){
                    $response['error'] = true;
                    $response['message'] = 'Reference to non-existing shop';
                    echo json_encode($response);
                    exit;
                } elseif ($update_column == 'client_id' && !$dbClient->getClientById($new_value)){
                    $response['error'] = true;
                    $response['message'] = 'Reference to non-existing client';
                    echo json_encode($response);
                    exit;
                } 
                
                $location = $dbLocation->getLocationByCondition($condition_column, $condition_value, $condition_type, $condition_value2);
                if (!$location) {
                    $response['error'] = true;
                    $response['message'] = 'Location not found';
                    echo json_encode($response);
                    exit;
                }
                $shop_location_id = $location['shop_id'];
                $client_location_id = $location['client_id'];

                if (($update_column == 'shop_id' && $new_value != 'NULL' && $client_location_id != null) || ($update_column == 'client_id' && $new_value != 'NULL' && $shop_location_id != null)) {
                    $response['error'] = true;
                    $response['message'] = "shop_id and client_id can't be initialized at the same time";
                    echo json_encode($response);
                    exit;
                }
                $result = $dbLocation->updateLocation($update_column, $new_value, $condition_column, $condition_value, $condition_type, $condition_value2);

                if ($result) {
                    $response['error'] = false;
                    $response['message'] = 'Location updated successfully';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Location update failed';
                }
            } catch (Exception $e) {
                $response['error'] = true;
                $response['message'] = 'Error: ' . $e->getMessage();
            }
        } else {
            $response['error'] = true;
            $response['message'] = 'Required parameters are missing';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Invalid request method. Expected POST';
    }

    header('Content-Type: application/json');
    echo json_encode($response);

?>
