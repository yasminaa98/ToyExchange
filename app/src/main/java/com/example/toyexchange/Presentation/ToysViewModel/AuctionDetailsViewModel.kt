package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Bid
import com.example.toyexchange.data.Repository.AuctionRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuctionDetailsViewModel@Inject constructor(
    private val auctionRepository: AuctionRepository
): ViewModel()  {

    private val _price = MutableLiveData<JsonObject>()
    val price: LiveData<JsonObject> =_price


    private val _bids=MutableLiveData<List<Bid>>()
    val bids:LiveData<List<Bid>> = _bids



    fun getAuctionPrice(idAuction:Long,token: String) {
        viewModelScope.launch {
            var resultPrice = auctionRepository.getAuctionPrice(idAuction,token)
            Log.i("result price ", resultPrice.message().toString())

            try {
                if (resultPrice.body() != null) {
                    _price.postValue(resultPrice.body())

                } else {
                    Log.i("body empty", resultPrice.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get auction price", e)
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

}