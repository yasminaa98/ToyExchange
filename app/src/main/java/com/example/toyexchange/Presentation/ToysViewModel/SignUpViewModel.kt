package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.User
import com.example.toyexchange.Domain.model.UserLoginResponse
import com.example.toyexchange.data.Repository.AuthRepository
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class SignUpViewModel @Inject constructor(
    private val authRepository:AuthRepository): ViewModel() {

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg
    fun userSignUp(user: User){
        viewModelScope.launch {
            var result=authRepository.userSignUp(user)
            try{
                if(result.body()!=null){
                    _msg.postValue(result.body()) }
                else{
                    Log.i("body empty",result.message())

                }
            }
            catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to sign up", e)
            }

        }

        }

    }
