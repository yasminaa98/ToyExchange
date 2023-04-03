package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Request
import com.example.toyexchange.Domain.model.UserLoginResponse
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val toysRepositoryImpl = ToysRepositoryImpl()

    private val _token = MutableLiveData<UserLoginResponse>()
    val token: LiveData<UserLoginResponse> = _token

    fun userLogin(request: Request){
        viewModelScope.launch {
            var result=toysRepositoryImpl.userLogin(request)
            try{
                if(result.body()!=null){
                    _token.postValue(result.body()) }
                else{
                    Log.i("error msg",result.message())

                }
        }
            catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to login", e)
            }

        }
    }
}