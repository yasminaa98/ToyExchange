package com.example.toyexchange.data.Repository

import com.example.toyexchange.Domain.model.Exchange
import com.example.toyexchange.data.remote.ExchangeApi
import com.google.gson.JsonObject
import retrofit2.Response
import javax.inject.Inject

class ExchangeRepository @Inject constructor(
    private val apiExchangeService: ExchangeApi
) {


    suspend fun getUserExchanges(username:String):Response<List<Exchange>>{
        return apiExchangeService.getUserExchanges(username)
    }


    suspend fun addExchangeOffer(exchange:Exchange,token: String):Response<JsonObject>{
        return apiExchangeService.addExchangeOffer(exchange,token)
    }


}