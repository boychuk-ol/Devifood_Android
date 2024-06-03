<?php

    class OrderProducts {
        // Properties
        public $order_id;
        public $product_id;

        // Constructor
        public function __construct($order_id, $product_id) {
            $this->order_id = $order_id;
            $this->product_id = $product_id;
        }
    }

    require_once(dirname(__FILE__, 2).'/DbOrderProductsRepository.php');
    require_once(dirname(__FILE__, 2).'/DbOrderRepository.php');
    require_once(dirname(__FILE__, 2).'/DbProductRepository.php');

    $response = array();
    echo $_SERVER['REQUEST_METHOD'];
    if ($_SERVER['REQUEST_METHOD'] == 'POST'){
        if (isset($_POST['order_id']) && isset($_POST['product_id'])) {
            $dbOrder = new DbOrderRepository();
            $dbProduct = new DbProductRepository();
            if(!$dbOrder->getOrderById($_POST['order_id'])) {
                $response['error'] = true;
                $response['message'] = "Reference to non-existing order";
                echo json_encode($response);
                exit;
            } elseif(!$dbProduct->getProductById($_POST['product_id'])) {
                $response['error'] = true;
                $response['message'] = "Reference to non-existing product";
                echo json_encode($response);
                exit;
            } 
            $dbOrderProducts = new DbOrderProductsRepository();
            $orderProducts = new OrderProducts($_POST['order_id'], $_POST['product_id']);
            $create_order_products = $dbOrderProducts->createOrderProducts($orderProducts);
    
            if ($create_order_products) {
                $response['error'] = false;
                $response['message'] = 'OrderProducts created successfully';
                $response['data'] = $orderProducts;
            } else {
                $response['error'] = true;
                $response['message'] = 'OrderProducts not created';
            }
        } else {
            $response['error'] = true;
            $response['message'] = 'Order ID and Product ID are required';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }
    
    header('Content-Type: application/json');
    echo json_encode($response);
    exit;
?>