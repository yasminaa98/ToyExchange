package com.example.toyexchange.data.Repository

import com.example.toyexchange.Domain.model.Exchange
import com.example.toyexchange.data.remote.ExchangeApi
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class ExchangeRepository @Inject constructor(
    private val apiExchangeService: ExchangeApi
) {


    suspend fun getUserExchanges(reciever:String):Response<List<Exchange>>{
        return apiExchangeService.getUserExchanges(reciever)
    }
    suspend fun getSenderRequests(sender:String):Response<List<Exchange>>{
        return apiExchangeService.getSenderRequests(sender)
    }


    suspend fun addExchangeOffer(exchange:Exchange,token: String):Response<JsonObject>{
        return apiExchangeService.addExchangeOffer(exchange,token)
    }
    suspend fun getExchangeById(id:Long):Response<Exchange>{
        return apiExchangeService.getExchangeById(id)
    }
    suspend fun updateStatus(
        idExchange: Long,
        newStatus:String,
        token: String):Response<JsonObject>{
        return apiExchangeService.updateStatus(idExchange,newStatus,token)}

}