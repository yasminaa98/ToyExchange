package com.example.toyexchange.Presentation.ToysViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.ToysInformation
import com.example.toyexchange.db.ToyDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class RoomViewModel (val toyDatabase: ToyDatabase): ViewModel()  {

    private val _toysDetails = MutableLiveData<ToysInformation>()
    val toysDetails: LiveData<ToysInformation> =_toysDetails
    private val _toys = MutableLiveData<List<ToysInformation>>()
    val toys: LiveData<List<ToysInformation>> =_toys
    fun insertToy(toy: ToysInformation){
        viewModelScope.launch {
            toyDatabase.toyDao().insertToy(toy)
        }
        Log.i("insertToy vm","hi")
    }
    fun deleteToy(toy:ToysInformation){
        viewModelScope.launch {
            toyDatabase.toyDao().deleteToy(toy)
        }
        Log.i("deleteToy vm","hi")

    }
    fun getAllToys(){
        viewModelScope.launch {
            val toysList=toyDatabase.toyDao().getAllToys()
            _toys.postValue(toysList)
            Log.i("toys fetched vm",toyDatabase.toyDao().getAllToys().toString())

        }
        Log.i("toys fetched vm","1")
    }

}