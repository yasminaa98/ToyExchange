package com.example.toyexchange.Presentation.ToysViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toyexchange.Domain.model.Annonce
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

    private val _annonce1 = MutableLiveData<Annonce>()
    val annonce1: LiveData<Annonce> =_annonce1
    private val _annonce2 = MutableLiveData<Annonce>()
    val annonce2: LiveData<Annonce> =_annonce2
    private val _annonce = MutableLiveData<Annonce>()
    val annonce: LiveData<Annonce> =_annonce


    fun getAnnonceOwner(idAnnonce:Long){
        viewModelScope.launch {
            var result = toysRepositoryImpl.getAnnonceOwner(idAnnonce)
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
    fun getAnnonceByAuction(idAuction:Long){
        viewModelScope.launch {
            var result = toysRepositoryImpl.getAnnonceByAuction(idAuction)
            try {
                if (result.body() != null) {
                    _annonce.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get annonce by auction", e)
            }
        }
    }

    fun getAnnonce1ById(idAnnonce:Long){
        viewModelScope.launch {
            var result = toysRepositoryImpl.getAnnonceById(idAnnonce)
            Log.i("the result annonce",result.toString())
            try {
                if (result.body() != null) {
                    _annonce1.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get annonce ", e)
            }
        }
    }
    fun getAnnonce2ById(idAnnonce:Long){
        viewModelScope.launch {
            var result = toysRepositoryImpl.getAnnonceById(idAnnonce)
            Log.i("the result annonce",result.toString())
            try {
                if (result.body() != null) {
                    _annonce2.postValue(result.body())
                } else {
                    Log.i("body empty", result.message())
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Failed to get annonce ", e)
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

