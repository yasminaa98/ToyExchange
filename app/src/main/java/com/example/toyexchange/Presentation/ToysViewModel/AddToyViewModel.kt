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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
@HiltViewModel

class AddToyViewModel @Inject constructor(
    private val toysRepositoryImpl:ToysRepositoryImpl ): ViewModel() {

    private val _msg = MutableLiveData<JsonObject>()
    val adding_msg: LiveData<JsonObject> = _msg

    //var token: String? = null
    fun addToy(photo: MultipartBody.Part,name:RequestBody, price: RequestBody,
               state: RequestBody,
               ageChild: RequestBody,
               ageToy: RequestBody,
               category: RequestBody,
               description: RequestBody,token: String) {
        viewModelScope.launch {
            var result = toysRepositoryImpl.addToy(photo,name,price,state,ageChild,ageToy,category,description,"$token")
            Log.i("result", result.toString())
            Log.i("bearer token is ", token.toString())

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
