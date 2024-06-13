package com.example.myapplication.`interface`

import com.example.myapplication.model.CategoriesResponse
import com.example.myapplication.model.Category
import com.example.myapplication.model.CategoryResponse
import com.example.myapplication.model.Client
import com.example.myapplication.model.ClientResponse
import com.example.myapplication.model.ClientsResponse
import com.example.myapplication.model.Courier
import com.example.myapplication.model.CourierResponse
import com.example.myapplication.model.CouriersResponse
import com.example.myapplication.model.Image
import com.example.myapplication.model.ImageResponse
import com.example.myapplication.model.ImagesResponse
import com.example.myapplication.model.Location
import com.example.myapplication.model.LocationIdResponse
import com.example.myapplication.model.LocationResponse
import com.example.myapplication.model.LocationsResponse
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderResponse
import com.example.myapplication.model.OrdersResponse
import com.example.myapplication.model.Product
import com.example.myapplication.model.ProductResponse
import com.example.myapplication.model.ProductsResponse
import com.example.myapplication.model.Shop
import com.example.myapplication.model.ShopResponse
import com.example.myapplication.model.ShopsResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Date

interface RetrofitAPI {
    //      ===== GET =====

    //  === SHOPS REPOSITORY ===
    @GET("get/getShops/getShops.php")
    fun getShops(): Call<ShopsResponse>
    @GET("get/getShops/getShopById.php")
    fun getShopById(@Query("shop_id") shopId: Int): Call<ShopResponse>
    @GET("get/getShops/getShopsByCategory.php")
    fun getShopsByCategory(@Query("category_id") categoryID: Int): Call<ShopsResponse>
    @GET("get/getShops/getShopsByNeighborhood.php")
    fun getShopsByNeighborhood(@Query("neighborhood") neighborhood: String): Call<ShopsResponse>
    @GET("get/getShops/getShopsByStreet.php")
    fun getShopsByStreet(@Query("street") street: String): Call<ShopsResponse>
    @GET("get/getShops/getShopsByCity.php")
    fun getShopsByCity(@Query("city") city: String): Call<ShopsResponse>
    @GET("get/getShops/getShopsByRating.php")
    fun getShopsByRating(@Query("rating") rating: Float): Call<ShopsResponse>
    @GET("get/getShops/getShopsByReviews.php")
    fun getShopsByReviews(@Query("reviews") reviews: Int): Call<ShopsResponse>
    @GET("get/getShops/getShopsBySubcategory.php")
    fun getShopsBySubcategory(@Query("subcat_name") subcategory: String): Call<ShopsResponse>
    @GET("get/getShops/getShopsBySubsubcategory.php")
    fun getShopsBySubsubcategory(@Query("subsubcat_name") subsubcategory: String): Call<ShopsResponse>
    @GET("get/getShops/getShopByName.php")
    fun getShopByName(@Query("name") name: String): Call<ShopResponse>
    @GET("get/getShops/getShopByProduct.php")
    fun getShopByProduct(@Query("product_id") productID: String): Call<ShopResponse>
    @GET("get/getShops/getShopByAdress.php")
    fun getShopByAddress(@Query("address") address: String): Call<ShopResponse>
    @GET("get/getShops/getShopsWithRatingBetween.php")
    fun getShopsWithRatingBetween(@Query("min_rating") minRating: Float, @Query("max_rating") maxRating: Float): Call<ShopsResponse>
    @GET("get/getShops/getShopsWithRatingLessThan.php")
    fun getShopsWithRatingLessThan(@Query("rating") rating: Float): Call<ShopsResponse>
    @GET("get/getShops/getShopsWithRatingMoreThan.php")
    fun getShopsWithRatingMoreThan(@Query("rating") rating: Float): Call<ShopsResponse>
    @GET("get/getShops/getShopsWithReviewsBetween.php")
    fun getShopsWithReviewsBetween(@Query("min_reviews") minReviews: Int, @Query("max_reviews") maxReviews: Int): Call<ShopsResponse>
    @GET("get/getShops/getShopsWithReviewsLessThan.php")
    fun getShopsWithReviewsLessThan(@Query("reviews") reviews: Int): Call<ShopsResponse>
    @GET("get/getShops/getShopsWithReviewsMoreThan.php")
    fun getShopsWithReviewsMoreThan(@Query("reviews") reviews: Int): Call<ShopsResponse>

    //  === IMAGES REPOSITORY ===
    @GET("get/getImages/getImages.php")
    fun getImages(): Call<ImagesResponse>
    @GET("get/getImages/getImageById.php")
    fun getImageById(@Query("image_id") imageID: Int): Call<ImageResponse>
    @GET("get/getImages/getImagesByEntity.php")
    fun getImagesByEntity(@Query("entity_name") entity: String): Call<ImagesResponse>
    @GET("get/getImages/getImageByName.php")
    fun getImageByName(@Query("name") name: String): Call<ImageResponse>
    @GET("get/getImages/getImagesByExtension.php")
    fun getImagesByExtension(@Query("extension") extension: String): Call<ImagesResponse>
    @GET("get/getImages/getImageByFullLink.php")
    fun getImageByFullLink(@Query("full_link") fullLink: String): Call<ImageResponse>

    //  === CLIENTS REPOSITORY ===
    @GET("get/getClients/getClients.php")
    fun getClients(): Call<ClientsResponse>
    @GET("get/getClients/getClientById.php")
    fun getClientById(@Query("client_id") clientID: Int): Call<ClientResponse>
    @GET("get/getClients/getClientByPhone.php")
    fun getClientByPhone(@Query("phone_number") phone: String): Call<ClientResponse>
    @GET("get/getClients/getClientByEmail.php")
    fun getClientByEmail(@Query("email") email: String): Call<ClientResponse>
    @GET("get/getClients/getClientsByFullName.php")
    fun getClientsByFullName(@Query("full_name") fullName: String): Call<ClientsResponse>
    @GET("get/getClients/getClientsByNeighborhood.php")
    fun getClientsByNeighborhood(@Query("neighborhood") neighborhood: String): Call<ClientsResponse>
    @GET("get/getClients/getClientsByAddress.php")
    fun getClientsByAddress(@Query("address") address: String): Call<ClientsResponse>
    @GET("get/getClients/getClientsByCity.php")
    fun getClientsByCity(@Query("city") city: String): Call<ClientsResponse>
    @GET("get/getClients/getClientsByStreet.php")
    fun getClientsByStreet(@Query("street") street: String): Call<ClientsResponse>

    //  === CATEGORIES REPOSITORY ===
    @GET("get/getCategories/getCategories.php")
    fun getCategories(): Call<CategoriesResponse>
    @GET("get/getCategories/getCategoriesByShop.php")
    fun getCategoriesByShop(@Query("shop_id") shopID: Int): Call<CategoriesResponse>
    @GET("get/getCategories/getCategoriesByEntity.php")
    fun getCategoriesByEntity(@Query("entity_name") entityName: String): Call<CategoriesResponse>
    @GET("get/getCategories/getCategoriesByName.php")
    fun getCategoriesByName(@Query("c_name") name: String): Call<CategoriesResponse>
    @GET("get/getCategories/getCategoryById.php")
    fun getCategoryById(@Query("category_id") categoryID: Int): Call<CategoryResponse>
    @GET("get/getCategories/getCategoriesBySubcategory.php")
    fun getCategoriesBySubcategory(@Query("subcat_name") subcategory: String): Call<CategoriesResponse>
    @GET("get/getCategories/getCategoryBySubsubcategory.php")
    fun getCategoryBySubsubcategory(@Query("subsubcat_name") subsubcategory: String): Call<CategoryResponse>

    //  === COURIERS REPOSITORY ===
    @GET("get/getCouriers/getCouriers.php")
    fun getCouriers(): Call<CouriersResponse>
    @GET("get/getCouriers/getCourierByEmail.php")
    fun getCourierByEmail(@Query("email") email: String): Call<CourierResponse>
    @GET("get/getCouriers/getCourierByFullName.php")
    fun getCourierByFullName(@Query("full_name") phone: String): Call<CourierResponse>
    @GET("get/getCouriers/getCourierById.php")
    fun getCourierById(@Query("courier_id") courierID: Int): Call<CourierResponse>
    @GET("get/getCouriers/getCourierByPhone.php")
    fun getCourierByPhone(@Query("phone") phone: String): Call<CourierResponse>
    @GET("get/getCouriers/getCouriersByWorkArea.php")
    fun getCouriersByWorkArea(@Query("work_area") workArea: String): Call<CouriersResponse>
    @GET("get/getCouriers/getCouriersByWorkRegion.php")
    fun getCouriersByWorkRegion(@Query("work_region") workRegion: String): Call<CouriersResponse>

    //  === LOCATIONS REPOSITORY ===
    @GET("get/getLocations/getLocationsMaxId.php")
    fun getLocationsMaxId(): Call<LocationIdResponse>
    @GET("get/getLocations/getLocationByFullAddress.php")
    fun getLocationByFullAddress(@Query("full_address") fullAddress: String): Call<LocationResponse>
    @GET("get/getLocations/getLocationById.php")
    fun getLocationById(@Query("location_id") locationID: Int): Call<LocationResponse>
    @GET("get/getLocations/getLocations.php")
    fun getLocations(): Call<LocationsResponse>
    @GET("get/getLocations/getLocationsByCity.php")
    fun getLocationsByCity(@Query("city") city: String): Call<LocationsResponse>
    @GET("get/getLocations/getLocationsByNeighborhood.php")
    fun getLocationsByNeighborhood(@Query("neighborhood") neighborhood: String): Call<LocationsResponse>
    @GET("get/getLocations/getLocationsByStreet.php")
    fun getLocationsByStreet(@Query("street") street: String): Call<LocationsResponse>
    @GET("get/getLocations/getLocationsByStreetNumber.php")
    fun getLocationsByStreetNumber(@Query("street_number") streetNumber: String): Call<LocationsResponse>

    //  === ORDERS REPOSITORY ===
    @GET("get/getOrders/getOrderById.php")
    fun getOrderById(@Query("order_id") orderID: Int): Call<OrderResponse>
    @GET("get/getOrders/getOrders.php")
    fun getOrders(): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByClient.php")
    fun getOrdersByClient(@Query("client_id") clientID: Int): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByCourier.php")
    fun getOrdersByCourier(@Query("courier_id") courierID: Int): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByCreatedTime.php")
    fun getOrdersByCreatedTime(@Query("created_time") createdTime: Date): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByCreatedTimePeriod.php")
    fun getOrdersByCreatedTimePeriod(@Query("from_time") fromTime: Date, @Query("to_time") toTime: Date): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByDeliveryPrice.php")
    fun getOrdersByDeliveryPrice(@Query("delivery_price") deliveryPrice: Float): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByDeliveryTime.php")
    fun getOrdersByDeliveryTime(@Query("delivery_time") deliveryTime: Int): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByDoneTime.php")
    fun getOrdersByDoneTime(@Query("done_time") doneTime: Date): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByDoneTimePeriod.php")
    fun getOrdersByDoneTimePeriod(@Query("from_time") fromTime: Date, @Query("to_time") toTime: Date): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByPaymentMethod.php")
    fun getOrdersByPaymentMethod(@Query("payment_method") paymentMethod: String): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByStatus.php")
    fun getOrdersByStatus(@Query("status") status: String): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersByTotalPrice.php")
    fun getOrdersByTotalPrice(@Query("total_price") totalPrice: Float): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithDeliveryPriceBetween.php")
    fun getOrdersWithDeliveryPriceBetween(@Query("min_price") minPrice: Float, @Query("max_price") maxPrice: Float): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithDeliveryPriceLessThan.php")
    fun getOrdersWithDeliveryPriceLessThan(@Query("delivery_price") deliveryPrice: Float): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithDeliveryPriceMoreThan.php")
    fun getOrdersWithDeliveryPriceMoreThan(@Query("delivery_price") deliveryPrice: Float): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithDeliveryTimeBetween.php")
    fun getOrdersWithDeliveryTimeBetween(@Query("min_time") minTime: Int, @Query("max_time") maxTime: Int): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithDeliveryTimeLessThan.php")
    fun getOrdersWithDeliveryTimeLessThan(@Query("delivery_time") deliveryTime: Int): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithDeliveryTimeMoreThan.php")
    fun getOrdersWithDeliveryTimeMoreThan(@Query("delivery_time") deliveryTime: Int): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithTotalPriceBetween.php")
    fun getOrdersWithTotalPriceBetween(@Query("min_price") minPrice: Float, @Query("max_price") maxPrice: Float): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithTotalPriceLessThan.php")
    fun getOrdersWithTotalPriceLessThan(@Query("total_price") totalPrice: Float): Call<OrdersResponse>
    @GET("get/getOrders/getOrdersWithTotalPriceMoreThan.php")
    fun getOrdersWithTotalPriceMoreThan(@Query("total_price") totalPrice: Float): Call<OrdersResponse>

    //  === PRODUCTS REPOSITORY ===
    @GET("get/getOrders/getProductById.php")
    fun getProductById(@Query("product_id") productID: Int): Call<ProductResponse>
    @GET("get/getProducts/getProducts.php")
    fun getProducts(): Call<ProductsResponse>
    @GET("get/getProducts/getProductByCode.php")
    fun getProductByCode(@Query("product_code") productCode: String): Call<ProductResponse>
    @GET("get/getProducts/getProductsByCaloriesTotal.php")
    fun getProductsByCaloriesTotal(@Query("calories_total") caloriesTotal: Int): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByCategory.php")
    fun getProductsByCategory(@Query("category_id") categoryID: Int): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByCountry.php")
    fun getProductsByCountry(@Query("country") country: String): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByOrganic.php")
    fun getProductsByOrganic(@Query("organic") organic: Boolean): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByPrice.php")
    fun getProductsByPrice(@Query("price") price: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByProducer.php")
    fun getProductsByProducer(@Query("producer") producer: String): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByRating.php")
    fun getProductsByRating(@Query("rating") rating: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByShop.php")
    fun getProductsByShop(@Query("shop_id") shopID: Int): Call<ProductsResponse>
    @GET("get/getProducts/getProductsBySubcategory.php")
    fun getProductsBySubcategory(@Query("subcategory") subcategory: String): Call<ProductsResponse>
    @GET("get/getProducts/getProductsBySubsubcategory.php")
    fun getProductsBySubsubcategory(@Query("subsubcategory") subsubcategory: String): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByVegan.php")
    fun getProductsByVegan(@Query("for_vegans") forVegans: Boolean): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByVegetarian.php")
    fun getProductsByVegetarian(@Query("for_vegetarians") forVegetarians: Boolean): Call<ProductsResponse>
    @GET("get/getProducts/getProductsByWeight.php")
    fun getProductsByWeight(@Query("weight") weight: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithCaloriesTotalBetween.php")
    fun getProductsWithCaloriesTotalBetween(@Query("min_calories") minCalories: Int, @Query("max_calories") maxCalories: Int): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithCaloriesTotalLessThan.php")
    fun getProductsWithCaloriesTotalLessThan(@Query("calories_total") caloriesTotal: Int): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithCaloriesTotalMoreThan.php")
    fun getProductsWithCaloriesTotalMoreThan(@Query("calories_total") caloriesTotal: Int): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithPriceBetween.php")
    fun getProductsWithPriceBetween(@Query("min_price") minPrice: Float, @Query("max_price") maxPrice: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithPriceLessThan.php")
    fun getProductsWithPriceLessThan(@Query("price") price: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithPriceMoreThan.php")
    fun getProductsWithPriceMoreThan(@Query("price") price: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithRatingBetween.php")
    fun getProductsWithRatingBetween(@Query("min_rating") minRating: Float, @Query("max_rating") maxRating: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithRatingLessThan.php")
    fun getProductsWithRatingLessThan(@Query("rating") rating: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithRatingMoreThan.php")
    fun getProductsWithRatingMoreThan(@Query("rating") rating: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithWeightBetween.php")
    fun getProductsWithWeightBetween(@Query("min_weight") minWeight: Float, @Query("max_weight") maxWeight: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithWeightLessThan.php")
    fun getProductsWithWeightLessThan(@Query("weight") weight: Float): Call<ProductsResponse>
    @GET("get/getProducts/getProductsWithWeightMoreThan.php")
    fun getProductsWithWeightMoreThan(@Query("weight") weight: Float): Call<ProductsResponse>


    //      ===== POST =====

    //  === SHOPS REPOSITORY ===
    @POST("create/createShop.php")
    fun createShop(@Body shop: Shop): Call<ResponseBody>
    @POST("update/updateShop.php")
    fun updateShop(@Body updateColumn: String, newValue: String, conditionColumn: String, conditionValue: String, conditionType: String, conditionValue2: String? = null): Call<ResponseBody>
    @POST("delete/deleteShop.php")
    fun deleteShop(@Body columnName: String, value: String, conditionType: String, value2: String? = null): Call<ResponseBody>

    //  === IMAGES REPOSITORY ===
    @POST("create/createImage.php")
    fun createImage(@Body image: Image): Call<ResponseBody>
    @POST("update/updateImage.php")
    fun updateImage(@Body updateColumn: String, newValue: String, conditionColumn: String, conditionValue: String, conditionType: String, conditionValue2: String? = null): Call<ResponseBody>
    @POST("delete/deleteImage.php")
    fun deleteImage(@Body columnName: String, value: String, conditionType: String, value2: String? = null): Call<ResponseBody>

    //  === CLIENTS REPOSITORY ===
    @POST("create/createClient.php")
    fun createClient(@Body client: Client): Call<ResponseBody>
    @POST("update/updateClient.php")
    fun updateClient(@Body updateColumn: String, newValue: String, conditionColumn: String, conditionValue: String, conditionType: String, conditionValue2: String? = null): Call<ResponseBody>
    @POST("delete/deleteClient.php")
    fun deleteClient(@Body columnName: String, value: String, conditionType: String, value2: String? = null): Call<ResponseBody>

    //  === CATEGORIES REPOSITORY ===
    @POST("create/createCategory.php")
    fun createCategory(@Body category: Category): Call<ResponseBody>
    @POST("update/updateCategory.php")
    fun updateCategory(@Body updateColumn: String, newValue: String, conditionColumn: String, conditionValue: String, conditionType: String, conditionValue2: String? = null): Call<ResponseBody>
    @POST("delete/deleteCategory.php")
    fun deleteCategory(@Body columnName: String, value: String, conditionType: String, value2: String? = null): Call<ResponseBody>

    //  === COURIERS REPOSITORY ===
    @POST("create/createCourier.php")
    fun createCourier(@Body courier: Courier): Call<ResponseBody>
    @POST("update/updateCourier.php")
    fun updateCourier(@Body updateColumn: String, newValue: String, conditionColumn: String, conditionValue: String, conditionType: String, conditionValue2: String? = null): Call<ResponseBody>
    @POST("delete/deleteCourier.php")
    fun deleteCourier(@Body columnName: String, value: String, conditionType: String, value2: String? = null): Call<ResponseBody>

    //  === LOCATIONS REPOSITORY ===
    @POST("create/createLocation.php")
    fun createLocation(@Body location: Location): Call<ResponseBody>
    @POST("update/updateLocation.php")
    fun updateLocation(@Body updateColumn: String, newValue: String, conditionColumn: String, conditionValue: String, conditionType: String, conditionValue2: String? = null): Call<ResponseBody>
    @POST("delete/deleteLocation.php")
    fun deleteLocation(@Body columnName: String, value: String, conditionType: String, value2: String? = null): Call<ResponseBody>

    //  === ORDERS REPOSITORY ===
    @POST("create/createOrders.php")
    fun createOrders(@Body order: Order): Call<ResponseBody>
    @POST("update/updateOrders.php")
    fun updateOrders(@Body updateColumn: String, newValue: String, conditionColumn: String, conditionValue: String, conditionType: String, conditionValue2: String? = null): Call<ResponseBody>
    @POST("delete/deleteOrders.php")
    fun deleteOrders(@Body columnName: String, value: String, conditionType: String, value2: String? = null): Call<ResponseBody>

    //  === PRODUCTS REPOSITORY ===
    @POST("create/createProduct.php")
    fun createProduct(@Body product: Product): Call<ResponseBody>
    @POST("update/updateProduct.php")
    fun updateProduct(@Body updateColumn: String, newValue: String, conditionColumn: String, conditionValue: String, conditionType: String, conditionValue2: String? = null): Call<ResponseBody>
    @POST("delete/deleteProduct.php")
    fun deleteProduct(@Body columnName: String, value: String, conditionType: String, value2: String? = null): Call<ResponseBody>
}
