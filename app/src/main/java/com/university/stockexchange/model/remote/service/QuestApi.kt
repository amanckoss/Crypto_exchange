package com.university.stockexchange.model.remote.service

import com.university.stockexchange.model.remote.service.model.AuthData
import com.university.stockexchange.model.remote.service.model.OrderData
import com.university.stockexchange.model.remote.service.model.RegistrationData
import io.reactivex.Single
import retrofit2.http.*

interface ServiceApi
{
    @GET("/api/data_wallet")
    @Headers("Content-Type: application/json")
    fun getDataWallet(@Query("api_token") api_token: String?): Single<UserWallet>

    @GET("/api/data_sell")
    @Headers("Content-Type: application/json")
    fun getDataOrderSell(
        @Query("api_token") api_token: String?,
        @Query("stock_name") stock_name: String?
    ): Single<ServiceOrderSell>

    @GET("/api/data_market")
    @Headers("Content-Type: application/json")
    fun getDataMarket(@Query("api_token") api_token: String?): Single<ServiceListMarket>

    @GET("/api/my_orders")
    @Headers("Content-Type: application/json")
    fun getMyOrders(@Query("api_token") api_token: String?): Single<MyOrdersData>

    @GET("/api/data_account")
    @Headers("Content-Type: application/json")
    fun getDataAccount(@Query("api_token") api_token: String?): Single<AccountData>

    @POST("/api/market_order")
    @Headers("Content-Type: application/json")
    fun postCloseOrder(@Query("stock_name") stock_name: String,
                       @Query("api_token") api_token: String,
                       @Query("operation") operation: String,
                       @Query("amount") amount:String,
                       @Query("price") price:String): Single<OrderData>

    @POST("/api/cancel_orders")
    @Headers("Content-Type: application/json")
    fun cancelOrder(@Query("api_token") api_token: String,
                       @Query("stock_id") stock_id: Int): Single<OrderData>

    @POST("/api/limit_order")
    @Headers("Content-Type: application/json")
    fun postSellOrder(
        @Query("api_token") api_token: String,
        @Query("stock_id") stock_id: Int,
        @Query("amount") amount: String,
        @Query("price") price: String
    ):Single<OrderData>

    @POST("/api/register")
    @Headers("Content-Type: application/json")
    fun postRegistration(@Query("first_name") first_name: String?,
                         @Query("surname") surname: String?,
                         @Query("email") email: String?,
                         @Query("password") password:String?,
                         @Query("password_confirmation") password_confirmation:String?
    ) : Single<RegistrationData>

    @POST("/api/login")
    @Headers("Content-Type: application/json")
    fun postLogin(@Query("email") email: String?):Single<AuthData>

    @POST("/api/password")
    @Headers("Content-Type: application/json")
    fun postPassword(@Query("password") password: String?,
                     @Query("api_token") api_token: String?): Single<AuthData>
}