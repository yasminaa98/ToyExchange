package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.Exchange
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ExchangeApi {

    @GET("api/exchanges/{username}")
    suspend fun getUserExchanges(@Path("username") username:String):Response<List<Exchange>>

    @POST("api/exchanges/addExchangeOffer")
    suspend fun addExchangeOffer(@Body exchange:Exchange,
                                 @Header("Authorization") token: String):Response<JsonObject>
}