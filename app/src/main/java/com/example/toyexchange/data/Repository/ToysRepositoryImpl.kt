package com.example.toyexchange.data.Repository

import com.example.toyexchange.Domain.model.*
import com.example.toyexchange.data.remote.ToysApi
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject

class ToysRepositoryImpl @Inject constructor(
    private val apiService:ToysApi
) {
    suspend fun getToys(token: String): Response<List<Toy>> {
        return apiService.getToys(token)
}
    suspend fun searchToys(name:String): List<Toy>{
        return apiService.searchToys(name)
    }
    suspend fun searchToysByCategory(category:String): List<Toy>{
        return apiService.searchToysByCategory(category)
    }
   /* suspend fun getToydetailsById(id:Int):Toy{
        return apiService.getToyDetailsById(id)
    }*/

    suspend fun addToy(token:String,annonce: Annonce):Response<JsonObject>{
        return apiService.addToy(token,annonce)
    }
    suspend fun modifyAnnonce(token:String,idAnnonce:Long,annonce:Annonce):Response<JsonObject>{
        return apiService.modifyAnnonce(token,idAnnonce,annonce)
    }


    suspend fun getUserAnnonces(token: String):Response<List<Annonce>>{
        return apiService.getUserAnnonces(token)   }

    suspend fun getAnnonceOwner(idAnnonce:Long):Response<User>{
        return apiService.getAnnonceOwner(idAnnonce)
    }

    suspend fun getAnnonceById(idAnnonce: Long):Response<Annonce>{
        return apiService.getAnnonceById(idAnnonce)
    }

}