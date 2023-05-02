package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Bid
import com.example.toyexchange.Domain.model.User
import com.example.toyexchange.data.Repository.AuctionRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuctionDetailsViewModel @Inject constructor(
    private val auctionRepository: AuctionRepository
): ViewModel()  {

    private val _price = MutableLiveData<JsonObject>()
    val price: LiveData<JsonObject> =_price


    private val _bids=MutableLiveData<List<Bid>>()
    val bids:LiveData<List<Bid>> = _bids

    private val _delete = MutableLiveData<JsonObject>()
    val delete: LiveData<JsonObject> =_delete


    private val _auctionOwner = MutableLiveData<User>()
    val auctionOwner: LiveData<User> =_auctionOwner



    fun getAuctionOwner(idAuction:Long){
        viewModelScope.launch {
            var result = auctionRepository.getAuctionOwner(idAuction)
            try {
                if (result.body() != null) {
                    _auctionOwner.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get auction owner", e)
            }
        }
    }




    fun getAuctionPrice(idAuction:Long,token: String) {
        viewModelScope.launch {

                var resultPrice = auctionRepository.getAuctionPrice(idAuction, token)
                Log.i("result price ", resultPrice.message().toString())
            while (true) {
                try {

                    if (resultPrice.body() != null) {
                        _price.setValue(resultPrice.body())

                    } else {
                        Log.i("body empty", resultPrice.message())
                    }
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Failed to get auction price", e)
                }
                delay(1000)
            }
        }
    }


    fun getAuctionBids(idAuction:Long,token: String) {
        viewModelScope.launch {
            var result = auctionRepository.getAuctionBids(idAuction,token)
            try {
                if (result.body() != null) {
                    _bids.postValue(result.body())
                    Log.i("bids", _bids.toString())



                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get bids", e)
            }
        }


    }

    fun deleteAuction(idAuction:Long) {
        viewModelScope.launch {
            var result = auctionRepository.deleteAuction(idAuction)
            try {
                if (result.body() != null) {
                    _delete.postValue(result.body())
                    Log.i("delete", _delete.toString())



                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to delete auction", e)
            }
        }


    }


}