package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.Exchange
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ExchangeApi {

    @GET("api/exchanges/{reciever}/getUserExchanges")
    suspend fun getUserExchanges(@Path("reciever") reciever:String):Response<List<Exchange>>

    @GET("api/exchanges/{sender}/getSenderRequests")
    suspend fun getSenderRequests(@Path("sender") sender:String):Response<List<Exchange>>
    @GET("api/exchanges/{id}/getExchangeById")
    suspend fun getExchangeById(@Path("id") id:Long):Response<Exchange>

    @POST("api/exchanges/addExchangeOffer")
    suspend fun addExchangeOffer(@Body exchange:Exchange,
                                 @Header("Authorization") token: String):Response<JsonObject>

    @PUT("api/exchanges/{id_exchange}/updateStatus")
    suspend fun updateStatus(
        @Path("id_exchange") idExchange: Long,
    @Query("newStatus") newStatus:String,
    @Header("Authorization") token: String):Response<JsonObject>
}