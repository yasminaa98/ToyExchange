package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Data.Repository.ToysRepositoryImpl
import com.example.toyexchange.Data.remote.ToysApi
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.ToysList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.lang.reflect.Type

class DetailsToyViewModel: ViewModel() {
    private val toysRepositoryImpl = ToysRepositoryImpl()
    private val _toysDetails = MutableLiveData<Toy>()
    val toysDetails: LiveData<Toy> =_toysDetails


  // or like this  get()= _toysDetails
    fun getToyDetailsById(id:Int)=viewModelScope.launch{
        try {
            _toysDetails.value = toysRepositoryImpl.getToydetailsById(id)

        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Failed to accessed for this toy", e)
        }
    }

}

