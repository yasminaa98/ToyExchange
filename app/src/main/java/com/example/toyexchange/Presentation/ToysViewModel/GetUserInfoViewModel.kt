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
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class GetUserInfoViewModel @Inject constructor(
    private val toysRepositoryImpl:ToysRepositoryImpl) :ViewModel(){

    private val _info = MutableLiveData<User>()
    val info: LiveData<User> = _info

    private val _msg = MutableLiveData<JsonObject>()
    val msg : LiveData<JsonObject> = _msg

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
    fun updateFirstName(iduser:Long,newFirstName:String){
        viewModelScope.launch {
            var result=toysRepositoryImpl.updateFirstname(iduser,newFirstName)
            Log.i("result",result.toString())
            try {
                if (result.body() != null) {
                    _msg.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to update firstname", e)
            }
        }
    }
    private val _msgLastName = MutableLiveData<JsonObject>()
    val msgLastName : LiveData<JsonObject> = _msgLastName
    fun updateLastName(iduser:Long,newLastName:String){
        viewModelScope.launch {
            var result=toysRepositoryImpl.updateLastname(iduser,newLastName)
            Log.i("result",result.toString())
            try {
                if (result.body() != null) {
                    _msgLastName.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to update lastname", e)
            }
        }
    }
    private val _msgHomeAdd = MutableLiveData<JsonObject>()
    val msgHomeAdd : LiveData<JsonObject> = _msgHomeAdd
    fun updateHomeAddress(iduser:Long,newHomeAddress:String){
        viewModelScope.launch {
            var result=toysRepositoryImpl.updateHomeAddress(iduser,newHomeAddress)
            Log.i("result",result.toString())
            try {
                if (result.body() != null) {
                    _msgHomeAdd.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to update home address", e)
            }
        }
    }
}