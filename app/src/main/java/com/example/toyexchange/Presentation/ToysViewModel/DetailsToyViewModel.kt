package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.User
import com.example.toyexchange.data.Repository.ToysRepositoryImpl
import com.example.toyexchange.db.ToyDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsToyViewModel @Inject constructor(private val toysRepositoryImpl: ToysRepositoryImpl): ViewModel() {


    private val _annonceOwner = MutableLiveData<User>()
    val annonceOwner: LiveData<User> =_annonceOwner



    fun getAnnonceOwner(idAnnone:Long){
        viewModelScope.launch {
            var result = toysRepositoryImpl.getAnnonceOwner(idAnnone)
            try {
                if (result.body() != null) {
                    _annonceOwner.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get annonce owner", e)
            }
        }
    }



  // or like this  get()= _toysDetails
  /*  fun getToyDetailsById(id:Int)=viewModelScope.launch{
        try {
            _toysDetails.value = toysRepositoryImpl.getToydetailsById(id)

        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Failed to accessed for this toy", e)
        }
    }*/

}

