<?php

require_once(dirname(__FILE__, 3).'/DbOrderRepository.php');
require_once(dirname(__FILE__, 3).'/DbClientRepository.php');
require_once(dirname(__FILE__, 3).'/DbCourierRepository.php');
require_once(dirname(__FILE__, 3).'/DbLocationRepository.php');
require_once(dirname(__FILE__, 3).'/DbShopRepository.php');
require_once(dirname(__FILE__, 3).'/DbImageRepository.php');
require_once(dirname(__FILE__, 3).'/DbCategoryRepository.php');

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['delivery_time'])) {

        $dbOrder = new DbOrderRepository();
        $dbClient = new DbClientRepository();
        $dbCourier = new DbCourierRepository();
        $dbLocation = new DbLocationRepository();
        $dbShop = new DbShopRepository();
        $dbImage = new DbImageRepository();
        $dbCategory = new DbCategoryRepository();
        $delivery_time = $_GET['delivery_time'];
    
        $orders = $dbOrder->getOrdersByDeliveryTime($delivery_time);
        if ($orders) {
            $response['error'] = false;
            $response['message'] = 'Orders received successfully';
            $response['data'] = array();
            foreach ($orders as $order) {
                $courier = $dbCourier->getCourierById($order['FKcourier_id']);
                $client = $dbClient->getClientById($order['FKclient_id']);
                $shop_location = $dbLocation->getLocationById($order['shop_location_id']);
                $client_location = $dbLocation->getLocationById($order['client_location_id']);
                
                $order['courier'] = $courier ?? null;
                $order['client'] = $client ?? null;
                $order['shop_location'] = $shop_location ?? null;
                if ($order['shop_location'] && $order['shop_location']['shop_id']) {
                    $shop = $dbShop->getShopById($order['shop_location']['shop_id']);
                    if ($shop) {
                        $shop_image = $dbImage->getImageById($shop['FK_image_ID']);
                        $shop['image'] = $shop_image ?? null;
                        unset($shop['FK_image_ID']);
                        $shop_category = $dbCategory->getCategoryById($shop['FK_category_id']);
                        if ($shop_category) {
                            $category_image = $dbImage->getImageById($shop_category['FK_image_id']);
                            $shop_category['image'] = $category_image ?? null;
                            unset($shop_category['FK_image_id']);
                        }
                        $shop['category'] = $shop_category ?? null;
                        unset($shop['FK_category_id']);
                    }
                    $order['shop_location']['shop'] = $shop;
                    unset($order['shop_location']['shop_id'], $order['shop_location']['client_id']);
                }
                $order['client_location'] = $client_location ?? null;
                if ($order['client_location'] && $order['client_location']['client_id']) {
                    $client = $dbClient->getClientById($order['client_location']['client_id']);
                    $order['client_location']['client'] = $client;
                    unset($order['client_location']['client_id'], $order['client_location']['shop_id']);
                }
    
                unset($order['FKclient_id'], $order['FKcourier_id'], $order['shop_location_id'], $order['client_location_id']);
                array_push($response['data'], $order);
            }
        } else {
            $response['error'] = true;
            $response['message'] = 'Some error occurred, please try again';
        }
    } else {
        $response['error'] = true;
        $response['message'] = 'Missing parameter: delivery_time';
    }
} else {
    $response['error'] = true;
    $response['message'] = 'Invalid Request';
}

header('Content-Type: application/json');
echo json_encode($response);
exit;

?>
