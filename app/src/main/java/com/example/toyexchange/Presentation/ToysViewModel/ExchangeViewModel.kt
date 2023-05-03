package com.example.toyexchange.Presentation.ToysViewModel

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

    fun getUserExchange(username:String) {
        viewModelScope.launch {
            var result = exchangeRepository.getUserExchanges(username)
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

}