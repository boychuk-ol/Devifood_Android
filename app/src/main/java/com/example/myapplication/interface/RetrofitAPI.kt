package com.example.myapplication.`interface`

import com.example.myapplication.model.ClientResponse
import com.example.myapplication.model.ImageResponse
import com.example.myapplication.model.ImagesResponse
import com.example.myapplication.model.ShopResponse
import com.example.myapplication.model.ShopsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
//    @POST("create/createShop.php")
//    fun createShop(@Body shop: Shop): Call<Shop>
//
//    @POST("users")
//    fun createUser(): Call<String>
//
//    @GET("test.php")
//    fun test(): Call<String>

    // === SHOPS REPOSITORY ===
    @GET("getShops/getShops.php")
    fun getShops(): Call<ShopsResponse>

    @GET("getShops/getShopById.php")
    fun getShopById(@Query("shop_id") shopId: Int): Call<ShopResponse>

    @GET("getShops/getShopsByCategory.php")
    fun getShopsByCategory(@Query("category") category: String): Call<ShopsResponse>

    @GET("getShops/getShopByName.php")
    fun getShopByName(@Query("name") name: String): Call<ShopResponse>

    @GET("getShops/getShopsWithRatingBetween.php")
    fun getShopsWithRatingBetween(@Query("min_rating") minRating: Float, @Query("max_rating") maxRating: Float): Call<ShopsResponse>

    @GET("getShops/getShopsWithRatingLessThan.php")
    fun getShopsWithRatingLessThan(@Query("rating") rating: Float): Call<ShopsResponse>

    @GET("getShops/getShopsWithRatingMoreThan.php")
    fun getShopsWithRatingMoreThan(@Query("rating") rating: Float): Call<ShopsResponse>

    @GET("getShops/getShopsWithReviewsBetween.php")
    fun getShopsWithReviewsBetween(@Query("min_reviews") minReviews: Int, @Query("max_reviews") maxReviews: Int): Call<ShopsResponse>

    @GET("getShops/getShopsWithReviewsLessThan.php")
    fun getShopsWithReviewsLessThan(@Query("reviews") reviews: Int): Call<ShopsResponse>

    @GET("getShops/getShopsWithReviewsMoreThan.php")
    fun getShopsWithReviewsMoreThan(@Query("reviews") reviews: Int): Call<ShopsResponse>

    // === IMAGES REPOSITORY ===
    @GET("getImages/getImages.php")
    fun getImages(): Call<ImagesResponse>

    @GET("getImages/getImageById.php")
    fun getImageById(@Query("image_id") imageID: Int): Call<ImageResponse>

    @GET("getImages/getImagesByEntity.php")
    fun getImagesByEntity(@Query("entity_name") entity: String): Call<ImagesResponse>

    @GET("getImages/getImageByName.php")
    fun getImageByName(@Query("name") name: String): Call<ImageResponse>

    @GET("getImages/getImagesByExtension.php")
    fun getImagesByExtension(@Query("extension") extension: String): Call<ImagesResponse>

    @GET("getImages/getImageByFullLink.php")
    fun getImageByFullLink(@Query("full_link") fullLink: String): Call<ImageResponse>

    // === CLIENTS REPOSITORY ===
    @GET("getClients/getClients.php")
    fun getClients(): Call<ClientResponse>

    @GET("getClients/getClientById.php")
    fun getClientById(@Query("client_id") clientID: Int): Call<ClientResponse>

    @GET("getClients/getClientByPhone.php")
    fun getClientByPhone(@Query("phone_number") phone: String): Call<ClientResponse>

    @GET("getClients/getClientByEmail.php")
    fun getClientByEmail(@Query("email") email: String): Call<ClientResponse>

    @GET("getClients/getClientsByFullName.php")
    fun getClientsByFullName(@Query("full_name") fullName: String): Call<ClientResponse>



//    @POST("/createOrder.php")
//    fun createOrder(): Call<String>
//
//    @POST("/createOrderProducts.php")
//    fun createOrderProducts(): Call<String>

}
