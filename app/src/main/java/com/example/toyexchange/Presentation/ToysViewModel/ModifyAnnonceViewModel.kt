package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Domain.model.Request
import com.example.toyexchange.Domain.model.UserLoginResponse
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class ModifyAnnonceViewModel@Inject constructor(
    private val toysRepositoryImpl:ToysRepositoryImpl): ViewModel() {

    private val _msg = MutableLiveData<JsonObject>()
    val msg: LiveData<JsonObject> = _msg
    fun modifyAnnonce(token:String,idAnnonce:Long,annonce: Annonce){
        viewModelScope.launch {
            var result=toysRepositoryImpl.modifyAnnonce(token,idAnnonce,annonce)
            Log.i("result",result.toString())
            try{
                if(result.body()!=null){
                    _msg.postValue(result.body())
                    }
                else{
                    Log.i("error msg",result.message())
                }
            }
            catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to modify", e)
            }

        }
    }
}