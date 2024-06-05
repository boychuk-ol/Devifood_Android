<?php

    class Category {
        // Properties
        public $category_id;
        public $c_name;
        public $subcat_name;
        public $subsubcat_name;
        public $entity_name;
        public $FK_image_id;

        // Constructor
        public function __construct($c_name, $subcat_name, $subsubcat_name, $entity_name, $FK_image_id) {
            $this->c_name = $c_name;
            $this->subcat_name = $subcat_name;
            $this->subsubcat_name = $subsubcat_name;
            $this->entity_name = $entity_name;
            $this->FK_image_id = $FK_image_id;
        }
    }

    require_once(dirname(__FILE__, 2).'/repos/DbCategoryRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbImageRepository.php');

    echo $_SERVER['REQUEST_METHOD'];
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $dbImage = new DbImageRepository();
        if(!isset($_POST['c_name'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter c_name';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['entity_name'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter entity_name';
            echo json_encode($response);
            exit;
        } elseif(!$dbImage->getImageById($_POST['FK_image_id'])) {
            $response['error'] = true;
            $response['message'] = 'Reference to non-existing image';
            echo json_encode($response);
            exit;
        } else {
            $dbCategory = new DbCategoryRepository();
            $category = new Category($_POST['c_name'], 
                                    $_POST['subcat_name'] ?? null, 
                                    $_POST['subsubcat_name'] ?? null, 
                                    $_POST['entity_name'], 
                                    $_POST['FK_image_id'] ?? null);
            $create_category = $dbCategory->createCategory($category);
            if($create_category) {
                $response['error'] = false;
                $response['message'] = 'Category created successfully';
                $response['data'] = $category;
            } else {
                $response['error'] = true;
                $response['message'] = 'Category not created';
            }
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }

    header('Content-Type: application/json');
    echo json_encode($category);
    exit;
?>