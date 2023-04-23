package com.example.toyexchange.data.Repository

import android.content.SharedPreferences
import android.util.Log
import com.example.toyexchange.Domain.model.*
import com.example.toyexchange.data.remote.AuthApi
import com.example.toyexchange.data.remote.RetrofitClient
import com.example.toyexchange.data.remote.ToysApi
import com.example.toyexchange.data.remote.UserApi
import com.google.gson.JsonObject
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class ToysRepositoryImpl @Inject constructor(
    private val apiService:ToysApi
) {
    private val apiLoginService=RetrofitClient.retrofitInstance.create(AuthApi::class.java)
    private val apiAccountService=RetrofitClient.retrofitInstance.create(UserApi::class.java)
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
   suspend fun userLogin(request: Request): Response<UserLoginResponse> {
       return apiLoginService.userLogin(request)

   }
    suspend fun userSignUp(user: User):Response<String>{
        return apiLoginService.userSignUp(user)
    }
    suspend fun addToy(token:String,annonce: Annonce):Response<JsonObject>{
        return apiService.addToy(token,annonce)
    }
    suspend fun modifyAnnonce(token:String,idAnnonce:Long,annonce:Annonce):Response<JsonObject>{
        return apiService.modifyAnnonce(token,idAnnonce,annonce)
    }
    suspend fun getUser(username:String):Response<User>{
        return apiAccountService.getUserByUsername(username)
    }
    suspend fun updateFirstname(
        idUser:Long,
        newFirstName:String):Response<JsonObject>{
        return apiAccountService.updateFirstname(idUser,newFirstName)
    }
    suspend fun updateLastname(
        idUser:Long,
        newLastName:String):Response<JsonObject>{
        return apiAccountService.updateLastname(idUser,newLastName)
    }
    suspend fun updateHomeAddress(
        idUser:Long,
        newHomeAddress:String):Response<JsonObject>{
        return apiAccountService.updateHomeAddress(idUser,newHomeAddress)
    }

    suspend fun getUserAnnonces(token: String):Response<List<Annonce>>{
        return apiService.getUserAnnonces(token)   }
}