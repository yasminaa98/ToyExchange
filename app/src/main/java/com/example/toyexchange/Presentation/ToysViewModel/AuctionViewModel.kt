package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.AuctionResponse
import com.example.toyexchange.data.Repository.AuctionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuctionViewModel@Inject constructor(
    private val auctionRepository: AuctionRepository
) : ViewModel() {

    private val _auctions = MutableLiveData<List<AuctionResponse>>()
    val auctions: LiveData<List<AuctionResponse>> = _auctions


    fun getAllAuctions() {
        viewModelScope.launch {
            var result = auctionRepository.getAllAuctions()
            try {
                if (result.body() != null) {
                    _auctions.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get auctions", e)
            }
        }
    }
}