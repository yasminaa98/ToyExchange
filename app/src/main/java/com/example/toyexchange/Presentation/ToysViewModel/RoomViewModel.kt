package com.example.toyexchange.Presentation.ToysViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.db.ToyDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


class RoomViewModel (val toyDatabase: ToyDatabase): ViewModel()  {

    private val _toysDetails = MutableLiveData<Toy>()
    val toysDetails: LiveData<Toy> =_toysDetails
    private val _toys = MutableLiveData<List<Toy>>()
    val toys: LiveData<List<Toy>> =_toys
    fun insertToy(toy: Toy){
        viewModelScope.launch {
            toyDatabase.toyDao().insertToy(toy)
        }
        Log.i("insertToy vm","hi")
    }
    fun deleteToy(toy:Toy){
        viewModelScope.launch {
            toyDatabase.toyDao().deleteToy(toy)
        }
        Log.i("deleteToy vm","hi")

    }
    fun getAllToys(){
        viewModelScope.launch {
            toyDatabase.toyDao().getAllToys()
        }
        Log.i("toys fetched vm","hi")
    }

}