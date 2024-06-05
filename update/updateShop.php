<?php

    require_once(dirname(__FILE__, 2) . '/repos/DbShopRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbImageRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbCategoryRepository.php');


    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        if (isset($_POST['update_column'], $_POST['new_value'], $_POST['condition_column'], $_POST['condition_value'], $_POST['condition_type'])) {
            $update_column = $_POST['update_column'];
            $new_value = $_POST['new_value'];
            $condition_column = $_POST['condition_column'];
            $condition_value = $_POST['condition_value'];
            $condition_type = $_POST['condition_type'];
            $condition_value2 = isset($_POST['condition_value2']) ? $_POST['condition_value2'] : null;

            $dbImage = new DbImageRepository();
            $dbShop = new DbShopRepository();
            $dbCategory = new DbCategoryRepository();

            try {
                if (strtoupper($new_value) === 'NULL') {
                    $new_value = null;
                } elseif(!$dbImage->getImageById($_POST['FK_image_id'])) {
                    $response['error'] = true;
                    $response['message'] = "Reference to non-existing image";
                    echo json_encode($response);
                    exit;
                } elseif(!$dbCategory->getCategoryById($_POST['FK_category_id'])) {
                    $response['error'] = true;
                    $response['message'] = "Reference to non-existing category";
                    echo json_encode($response);
                    exit;
                }
                $result = $dbShop->updateShop($update_column, $new_value, $condition_column, $condition_value, $condition_type, $condition_value2);

                if ($result) {
                    $response['error'] = false;
                    $response['message'] = 'Shop updated successfully';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Shop update failed';
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
