<?php

    require_once(dirname(__FILE__, 2) . '/repos/DbProductRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbImageRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbShopRepository.php');
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

            $dbProduct = new DbProductRepository();
            $dbImage = new DbImageRepository();
            $dbShop = new DbShopRepository();
            $dbCategory = new DbCategoryRepository();

            try {
                if (strtoupper($new_value) === 'NULL') {
                    $new_value = null;
                } elseif($update_column == 'FK_image_id' && !$dbImage->getImageById($new_value)) {
                    $response['error'] = true;
                    $response['message'] = "Reference to non-existing image";
                    echo json_encode($response);
                    exit;
                } elseif($update_column == 'FK_shop_id' && !$dbShop->getShopById($new_value)) {
                    $response['error'] = true;
                    $response['message'] = "Reference to non-existing product";
                    echo json_encode($response);
                    exit;
                } elseif($update_column == 'FK_category_id' && !$dbCategory->getCategoryById($_POST['FK_category_id'])) {
                    $response['error'] = true;
                    $response['message'] = "Reference to non-existing category";
                    echo json_encode($response);
                    exit;
                } 
                $result = $dbProduct->updateProduct($update_column, $new_value, $condition_column, $condition_value, $condition_type, $condition_value2);

                if ($result) {
                    $response['error'] = false;
                    $response['message'] = 'Product updated successfully';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Product update failed';
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
