<?php

    require_once(dirname(__FILE__, 2) . '/repos/DbClientRepository.php');

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        if (isset($_POST['update_column'], $_POST['new_value'], $_POST['condition_column'], $_POST['condition_value'], $_POST['condition_type'])) {
            $update_column = $_POST['update_column'];
            $new_value = $_POST['new_value'];
            $condition_column = $_POST['condition_column'];
            $condition_value = $_POST['condition_value'];
            $condition_type = $_POST['condition_type'];
            $condition_value2 = isset($_POST['condition_value2']) ? $_POST['condition_value2'] : null;

            $dbClient = new DbClientRepository();

            try {
                if (strtoupper($new_value) === 'NULL') {
                    $new_value = null;
                }
                $result = $dbClient->updateClient($update_column, $new_value, $condition_column, $condition_value, $condition_type, $condition_value2);

                if ($result) {
                    $response['error'] = false;
                    $response['message'] = 'Client updated successfully';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Client update failed';
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
