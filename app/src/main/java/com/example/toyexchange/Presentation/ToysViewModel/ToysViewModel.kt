package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Data.Repository.ToysRepositoryImpl
import com.example.toyexchange.Domain.model.Toy
import kotlinx.coroutines.launch

class ToysViewModel() : ViewModel() {
    private val toysRepositoryImpl = ToysRepositoryImpl()

    private val _toys = MutableLiveData<List<Toy>>()
    val toys: LiveData<List<Toy>> = _toys

    private val _toySelected = MutableLiveData<Toy>()
    val toySelected: LiveData<Toy> = _toySelected


    // display toys List
    fun fetchToys() {
        viewModelScope.launch {
            try {
                _toys.value = toysRepositoryImpl.getToys()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch toys", e)
            }
        }
    }
    // search for a toy
    fun searchToys(name:String)=viewModelScope.launch{
        try {
            _toys.value = toysRepositoryImpl.searchToys(name).filter { it.name.contains(name, ignoreCase = true)}

        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Failed to search for this toy", e)
        }
    }


}

