<?php

    require_once(dirname(__FILE__, 2) . '/repos/DbOrderProductsRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbOrderRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbProductRepository.php');

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        if (isset($_POST['update_column'], $_POST['new_value'], $_POST['condition_column'], $_POST['condition_value'], $_POST['condition_type'])) {
            $update_column = $_POST['update_column'];
            $new_value = $_POST['new_value'];
            $condition_column = $_POST['condition_column'];
            $condition_value = $_POST['condition_value'];
            $condition_type = $_POST['condition_type'];
            $condition_value2 = isset($_POST['condition_value2']) ? $_POST['condition_value2'] : null;

            $dbOrderProducts = new DbOrderProductsRepository();
            $dbOrder = new DbOrderRepository();
            $dbProduct = new DbProductRepository();

            try {
                if (strtoupper($new_value) === 'NULL') {
                    $new_value = null;
                } elseif($update_column == 'order_id' && !$dbOrder->getOrderById($new_value)) {
                    $response['error'] = true;
                    $response['message'] = "Reference to non-existing order";
                    echo json_encode($response);
                    exit;
                } elseif($update_column == 'product_id' && !$dbProduct->getProductById($new_value)) {
                    $response['error'] = true;
                    $response['message'] = "Reference to non-existing product";
                    echo json_encode($response);
                    exit;
                } 
                $result = $dbOrderProducts->updateOrderProducts($update_column, $new_value, $condition_column, $condition_value, $condition_type, $condition_value2);

                if ($result) {
                    $response['error'] = false;
                    $response['message'] = 'OrderProducts updated successfully';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'OrderProducts update failed';
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
