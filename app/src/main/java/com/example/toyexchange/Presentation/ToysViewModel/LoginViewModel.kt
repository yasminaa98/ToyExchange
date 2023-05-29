package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Request
import com.example.toyexchange.Domain.model.UserLoginResponse
import com.example.toyexchange.data.Repository.AuthRepository
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository:AuthRepository): ViewModel() {

    private val _token = MutableLiveData<UserLoginResponse>()
    val token: LiveData<UserLoginResponse> = _token

    fun userLogin(request: Request){
        viewModelScope.launch {
            var result=authRepository.userLogin(request)
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