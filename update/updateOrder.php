<?php

    require_once(dirname(__FILE__, 2) . '/repos/DbOrderRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbClientRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbCourierRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbLocationRepository.php');

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        if (isset($_POST['update_column'], $_POST['new_value'], $_POST['condition_column'], $_POST['condition_value'], $_POST['condition_type'])) {
            $update_column = $_POST['update_column'];
            $new_value = $_POST['new_value'];
            $condition_column = $_POST['condition_column'];
            $condition_value = $_POST['condition_value'];
            $condition_type = $_POST['condition_type'];
            $condition_value2 = isset($_POST['condition_value2']) ? $_POST['condition_value2'] : null;

            $dbOrder = new DbOrderRepository();
            $dbCourier = new DbCourierRepository();
            $dbClient = new DbClientRepository();
            $dbLocation = new DbLocationRepository();

            try {
                if (strtoupper($new_value) === 'NULL') {
                    $new_value = null;
                } elseif ($update_column == 'FKcourier_id' && !$dbCourier->getCourierById($new_value)) {
                    $response['error'] = true;
                    $response['message'] = 'Reference to non-existing courier';
                    echo json_encode($response);
                    exit;
                } elseif ($update_column == 'FKclient_id' && !$dbClient->getClientById($new_value)) {
                    $response['error'] = true;
                    $response['message'] = 'Reference to non-existing client';
                    echo json_encode($response);
                    exit;
                } elseif ($update_column == 'shop_location_id' && !$dbLocation->getLocationById($new_value)) {
                    $response['error'] = true;
                    $response['message'] = 'Reference to non-existing shop location';
                    echo json_encode($response);
                    exit;
                } elseif ($update_column == 'client_location_id' && !$dbLocation->getLocationById($new_value)) {
                    $response['error'] = true;
                    $response['message'] = 'Reference to non-existing client location';
                    echo json_encode($response);
                    exit;
                }
                
                $order = $dbOrder->getOrderByCondition($condition_column, $condition_value, $condition_type, $condition_value2);
                if (!$order) {
                    $response['error'] = true;
                    $response['message'] = 'Order not found';
                    echo json_encode($response);
                    exit;
                }
                $shop_location_id = $order['shop_location_id'];
                $client_location_id = $order['client_location_id'];

                if (($update_column == 'client_location_id' && $new_value == $shop_location_id) || ($update_column == 'shop_location_id' && $new_value == $client_location_id)) {
                    $response['error'] = true;
                    $response['message'] = "client_location_id and shop_location_id can't refer to the same location";
                    echo json_encode($response);
                    exit;
                }

                $result = $dbOrder->updateOrder($update_column, $new_value, $condition_column, $condition_value, $condition_type, $condition_value2);

                if ($result) {
                    $response['error'] = false;
                    $response['message'] = 'Order updated successfully';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Order update failed';
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
