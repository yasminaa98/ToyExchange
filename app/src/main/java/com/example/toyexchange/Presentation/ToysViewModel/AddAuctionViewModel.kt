package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Domain.model.AuctionResponse
import com.example.toyexchange.data.Repository.AuctionRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAuctionViewModel  @Inject constructor(
    private val auctionRepository: AuctionRepository): ViewModel()  {

    private val _msg = MutableLiveData<JsonObject>()
    val msg: LiveData<JsonObject> = _msg

    //var token: String? = null
    fun addAuction(idAnnonce: Long, auction: AuctionResponse, token:String) {
        viewModelScope.launch {
            var result = auctionRepository.addAuction(idAnnonce,auction,token)
            try {
                if (result.body() != null) {
                    _msg.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to add this auction", e)
            }
        }
    }

    fun checkExistentAuction(idAnnonce: Long, token:String) {
        viewModelScope.launch {
            var result = auctionRepository.checkExistentAuction(idAnnonce,token)
            try {
                if (result.body() != null) {
                    _msg.postValue(result.body())
                } else {
                    _msg.postValue(result.body())
                    Log.i("not found", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed check this auction", e)
            }
        }
    }
}