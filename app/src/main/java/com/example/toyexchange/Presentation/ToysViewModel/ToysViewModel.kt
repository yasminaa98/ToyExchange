package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Common.Resource
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import com.example.toyexchange.Domain.model.Toy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToysViewModel @Inject constructor(
    private val toysRepositoryImpl:ToysRepositoryImpl
) : ViewModel() {

    private val _toys = MutableLiveData<List<Toy>>()
    val toys: LiveData<List<Toy>> = _toys

   /* private val _toySelected = MutableLiveData<Toy>()
    val toySelected: LiveData<Toy> = _toySelected */


    // display toys List
    fun fetchToys(token:String) {
        viewModelScope.launch {
            var result = toysRepositoryImpl.getToys("$token")
            try {
                if (result.body() != null) {
                    _toys.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to fetch toys", e)
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
    fun searchToysByCategory(category:String)=viewModelScope.launch{
        try {
            _toys.value = toysRepositoryImpl.searchToys(category).filter { it.category.contains(category, ignoreCase = true)}

        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Failed to search for this category", e)
        }
    }


}

