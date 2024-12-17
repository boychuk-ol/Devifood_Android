<?php

    class Order {
        // Properties
        public $order_id;
        public $created;
        public $done;
        public $o_status;
        public $delivery_price;
        public $delivery_time;
        public $total_price;
        public $payment_method;
        public $FKclient_id;
        public $FKcourier_id;
        public $shop_location_id;
        public $client_location_id;

        // Constructor
        public function __construct($created, $done, $o_status, $delivery_price, $delivery_time, $total_price, $payment_method, $FKclient_id, $FKcourier_id, $shop_location_id, $client_location_id) {
            $this->created = $created;
            $this->done = $done;
            $this->o_status = $o_status;
            $this->delivery_price = $delivery_price;
            $this->delivery_time = $delivery_time;
            $this->total_price = $total_price;
            $this->payment_method = $payment_method;
            $this->FKclient_id = $FKclient_id;
            $this->FKcourier_id = $FKcourier_id;
            $this->shop_location_id = $shop_location_id;
            $this->client_location_id = $client_location_id;
        }
    }

    require_once(dirname(__FILE__, 2).'/repos/DbOrderRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbClientRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbCourierRepository.php');
    require_once(dirname(__FILE__, 2).'/repos/DbLocationRepository.php');

    echo $_SERVER['REQUEST_METHOD'];
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $dbCourier = new DbCourierRepository();
        $dbClient = new DbClientRepository();
        $dbLocation = new DbLocationRepository();
        if(!isset($_POST['created'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter created';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['o_status'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter o_status';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['delivery_price'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter delivery_price';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['delivery_time'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter delivery_time';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['total_price'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter total_price';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['payment_method'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter payment_method';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['FKclient_id'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter FKclient_id';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['FKcourier_id'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter FKcourier_id';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['shop_location_id'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter shop_location_id';
            echo json_encode($response);
            exit;
        } elseif(!isset($_POST['client_location_id'])) {
            $response['error'] = true;
            $response['message'] = 'Missing parameter client_location_id';
            echo json_encode($response);
            exit;
        } elseif($_POST['client_location_id'] == $_POST['shop_location_id']) {
            $response['error'] = true;
            $response['message'] = "client_location_id and shop_location_id can't refer to the same location";
            echo json_encode($response);
            exit;
        } elseif(!$dbClient->getClientById($_POST['FKclient_id']) && isset($_POST['FKclient_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing client";
            echo json_encode($response);
            exit;
        } elseif(!$dbCourier->getCourierById($_POST['FKcourier_id']) && isset($_POST['FKcourier_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing courier";
            echo json_encode($response);
            exit;
        } elseif(!$dbCourier->getCourierById($_POST['shop_location_id']) && isset($_POST['shop_location_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing shop location";
            echo json_encode($response);
            exit;
        } elseif(!$dbCourier->getCourierById($_POST['client_location_id']) && isset($_POST['client_location_id'])) {
            $response['error'] = true;
            $response['message'] = "Reference to non-existing client location";
            echo json_encode($response);
            exit;
        } else {
            $dbOrder = new DbOrderRepository();
            $order = new Order($_POST['created'], 
                                    $_POST['done'] ?? null, 
                                    $_POST['o_status'], 
                                    $_POST['delivery_price'],
                                    $_POST['delivery_time'], 
                                    $_POST['total_price'],
                                    $_POST['payment_method'],
                                    $_POST['FKclient_id'],
                                    $_POST['FKcourier_id'],
                                    $_POST['shop_location_id'],
                                    $_POST['client_location_id']);
            $create_order = $dbOrder->createOrder($order);
            if($create_order) {
                $response['error'] = false;
                $response['message'] = 'Order created successfully';
                $response['data'] = $order;
            } else {
                $response['error'] = true;
                $response['message'] = 'Order not created';
            }
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Wrong request method. Expected POST';
    }

    header('Content-Type: application/json');
    echo json_encode($order);
    exit;
?>