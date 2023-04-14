package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.User
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import kotlinx.coroutines.launch

class GetUserInfoViewModel :ViewModel(){
    private val toysRepositoryImpl = ToysRepositoryImpl()

    private val _info = MutableLiveData<User>()
    val info: LiveData<User> = _info

    /* private val _toySelected = MutableLiveData<Toy>()
     val toySelected: LiveData<Toy> = _toySelected */


    // display toys List
    fun getUserInfo(username:String) {
        viewModelScope.launch {
            var result = toysRepositoryImpl.getUser(username)
            Log.i("result",result.toString())
            try {
                if (result.body() != null) {
                    _info.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to fetch toys", e)
            }
        }
    }
}