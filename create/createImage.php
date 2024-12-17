<?php

    class Image {
        // Properties
        public $image_id;
        public $entity_name;
        public $i_name;
        public $extension;
        public $full_link;

        // Constructor
        public function __construct($entity_name, $i_name, $extension, $full_link) {
            $this->entity_name = $entity_name;
            $this->i_name = $i_name;
            $this->extension = $extension;
            $this->full_link = $full_link;
        }
    }

    require_once(dirname(__FILE__, 2).'/repos/DbImageRepository.php');

    echo $_SERVER['REQUEST_METHOD'];
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        if(!isset($_POST['entity_name'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter entity_name';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['i_name'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter i_name';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['extension'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter extension';
            echo json_encode($response);
            exit;
        } else {
            $dbImage = new DbImageRepository();
            $image = new Image($_POST['entity_name'], 
                                    $_POST['i_name'], 
                                    $_POST['extension'], 
                                    $_POST['full_link'] ?? null);
            $create_image = $dbImage->createImage($image);
            if($create_image) {
                $response['error'] = false;
                $response['message'] = 'Image created successfully';
                $response['data'] = $image;
            } else {
                $response['error'] = true;
                $response['message'] = 'Image not created';
            }
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }

    header('Content-Type: application/json');
    echo json_encode($image);
    exit;
?>