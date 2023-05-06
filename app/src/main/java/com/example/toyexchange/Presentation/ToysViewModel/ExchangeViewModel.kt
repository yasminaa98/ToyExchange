package com.example.toyexchange.Presentation.ToysViewModel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Exchange
import com.example.toyexchange.data.Repository.ExchangeRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExchangeViewModel @Inject constructor (
private val exchangeRepository: ExchangeRepository) : ViewModel() {

    private val _exchange = MutableLiveData<List<Exchange>>()
    val exchange: LiveData<List<Exchange>> = _exchange

    private val _msg = MutableLiveData<JsonObject>()
    val msg: LiveData<JsonObject> = _msg

    private val _exchangeById = MutableLiveData<Exchange>()
    val exchangeById: LiveData<Exchange> = _exchangeById

    private val _update = MutableLiveData<JsonObject>()
    val update: LiveData<JsonObject> = _update

    private val _sender = MutableLiveData<List<Exchange>>()
    val sender: LiveData<List<Exchange>> = _sender

    fun getUserExchanges(reciever:String) {
        viewModelScope.launch {
            var result = exchangeRepository.getUserExchanges(reciever)
            Log.i("reciever request",result.toString())


            try {
                if (result.body() != null) {
                    _exchange.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get exchanges", e)
            }
        }
    }
    fun getSenderRequests(sender:String) {
        viewModelScope.launch {
            var result = exchangeRepository.getSenderRequests(sender)
            try {
                if (result.body() != null) {
                    _sender.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get exchanges requests", e)
            }
        }
    }

    fun addExchangeOffer(exchange: Exchange,token:String) {
        viewModelScope.launch {
            var result = exchangeRepository.addExchangeOffer(exchange,token)
            try {
                if (result.body() != null) {
                    _msg.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to add exchange", e)
            }
        }
    }
    fun getExchangeById(id:Long) {
        viewModelScope.launch {
            var result = exchangeRepository.getExchangeById(id)
            Log.i("status result",result.toString())
            try {
                if (result.body() != null) {
                    _exchangeById.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to update status", e)
            }
        }
    }
    fun updateStatus(idExchange:Long,newStatus:String,token:String) {
        viewModelScope.launch {
            var result = exchangeRepository.updateStatus(idExchange,newStatus,token)
            Log.i("status result",result.toString())
            try {
                if (result.body() != null) {
                    _update.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to update status", e)
            }
        }
    }



}