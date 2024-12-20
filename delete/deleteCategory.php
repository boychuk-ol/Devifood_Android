<?php

require_once(dirname(__FILE__, 2) . '/repos/DbCategoryRepository.php');

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $column_name = $_POST['column_name'];
    $value = $_POST['value'];
    $condition_type = $_POST['condition_type'] ?? '=';
    $value2 = $_POST['value2'] ?? null;

    if (!isset($column_name) || !isset($value)) {
        $response['error'] = true;
        $response['message'] = 'Missing parameters';
        echo json_encode($response);
        exit;
    }

    if ($condition_type == 'BETWEEN' && $value2 === null) {
        $response['error'] = true;
        $response['message'] = 'Two values are required for BETWEEN condition';
        echo json_encode($response);
        exit;
    }

    $dbCategory = new DbCategoryRepository();

    try {
        $delete_category = $dbCategory->deleteCategory($column_name, $value, $condition_type, $value2);
        if ($delete_category) {
            $response['error'] = false;
            $response['message'] = 'Category deleted successfully';
        } else {
            $response['error'] = true;
            $response['message'] = 'Category not deleted or does not exist';
        }
    } catch (Exception $e) {
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }
} else {
    $response['error'] = true;
    $response['message'] = 'Wrong request method. Expected POST';
}

header('Content-Type: application/json');
echo json_encode($response);
exit;

?>
