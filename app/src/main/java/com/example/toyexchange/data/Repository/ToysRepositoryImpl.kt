package com.example.toyexchange.data.Repository

import android.content.SharedPreferences
import android.util.Log
import com.example.toyexchange.Domain.model.*
import com.example.toyexchange.data.remote.AuthApi
import com.example.toyexchange.data.remote.RetrofitClient
import com.example.toyexchange.data.remote.ToysApi
import com.google.gson.JsonObject
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class ToysRepositoryImpl {
    private val apiService = RetrofitClient.retrofitInstance.create(ToysApi::class.java)
    private val apiLoginService=RetrofitClient.retrofitInstanceLogin.create(AuthApi::class.java)
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
}