package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Update
import com.example.toyexchange.Domain.model.Bid
import com.example.toyexchange.data.Repository.AuctionRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddBidViewModel @Inject constructor(
    private val auctionRepository: AuctionRepository
): ViewModel()   {


    private val _response = MutableLiveData<JsonObject>()
    val response: LiveData<JsonObject> =_response

    private val _bid = MutableLiveData<Bid>()
    val bid: LiveData<Bid> =_bid

    private val _priceUpdated = MutableLiveData<JsonObject>()
    val priceUpdated: LiveData<JsonObject> =_priceUpdated

    fun addBid(bid: Bid, idAuction:Long, token: String) {
        viewModelScope.launch {
            var result = auctionRepository.addBid(bid,idAuction,token)
            Log.i("result of adding bid", result.message().toString())

            try {
                if (result.body() != null) {
                    _response.postValue(result.body())


                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get auction price", e)
            }
        }
    }


    fun getUserBid(idAuction:Long, token: String) {
        viewModelScope.launch {
            var result = auctionRepository.getUserBid(idAuction,token)
            Log.i("result of adding bid", result.message().toString())

            try {
                if (result.body() != null) {
                    _bid.postValue(result.body())


                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get user bid", e)
            }
        }
    }
    fun updateBidPrice(idAuction:Long,newPrice:String, token: String) {
        viewModelScope.launch {
            var result = auctionRepository.updateBidPrice(idAuction,newPrice,token)
            Log.i("result of updating price", result.message().toString())

            try {
                if (result.body() != null) {
                    _priceUpdated.postValue(result.body())


                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to update price", e)
            }
        }
    }
}