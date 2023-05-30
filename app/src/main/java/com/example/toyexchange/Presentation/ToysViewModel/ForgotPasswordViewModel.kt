package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.ForgotPasswordResponse
import com.example.toyexchange.data.Repository.AuthRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _tokenMsg = MutableLiveData<ForgotPasswordResponse>()
    val tokenMsg: LiveData<ForgotPasswordResponse> = _tokenMsg


    fun forgotPassword(email:String){
        viewModelScope.launch {
            var result=authRepository.forgotPassword(email)
            Log.i("result",result.toString())
            try{
                if(result.body()!=null){
                    _tokenMsg.postValue(result.body()) }
                else{
                    _tokenMsg.postValue(result.body())
                    Log.i("error msg",result.message())

                }
            }
            catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to send reset password email", e)
            }

        }
    }
    fun resetPassword(token:String,newPassword:String){
        viewModelScope.launch {
            var result=authRepository.resetPassword(token,newPassword)
            Log.i("result",result.toString())
            try{

                    Log.i("changed",result.toString())


            }
            catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to send reset password email", e)
            }

        }
    }
}