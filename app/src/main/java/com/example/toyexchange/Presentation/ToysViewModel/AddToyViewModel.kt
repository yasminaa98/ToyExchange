package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.*
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class AddToyViewModel : ViewModel() {
    private val toysRepositoryImpl = ToysRepositoryImpl()

    private val _msg = MutableLiveData<JsonObject>()
    val adding_msg: LiveData<JsonObject> = _msg

    //var token: String? = null
    fun addToy(token: String, annonce: Annonce) {
        viewModelScope.launch {
            var result = toysRepositoryImpl.addToy("$token", annonce)
            Log.i("result", result.toString())
            Log.i("bearer token is ", token.toString())
            Log.i("annonce is", annonce.age_toy)

            try {
                if (result.body() != null) {
                    _msg.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to add this toy", e)
            }
        }


    }
}
