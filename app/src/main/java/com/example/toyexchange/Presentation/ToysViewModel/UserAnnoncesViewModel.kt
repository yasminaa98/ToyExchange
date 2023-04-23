package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UserAnnoncesViewModel@Inject constructor(
    private val toysRepositoryImpl: ToysRepositoryImpl
) : ViewModel() {

    private val _annonces = MutableLiveData<List<Annonce>>()
    val annonces: LiveData<List<Annonce>> = _annonces

    /* private val _toySelected = MutableLiveData<Toy>()
     val toySelected: LiveData<Toy> = _toySelected */


    // display toys List
    fun getUserAnnonces(token:String) {
        viewModelScope.launch {
            var result = toysRepositoryImpl.getUserAnnonces("$token")
            try {
                if (result.body() != null) {
                    _annonces.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to fetch annonces", e)
            }
        }
    }
}