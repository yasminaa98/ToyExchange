package com.example.toyexchange.data.Repository

import com.example.toyexchange.Domain.model.Request
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.UserLoginResponse
import com.example.toyexchange.data.remote.AuthApi
import com.example.toyexchange.data.remote.RetrofitClient
import com.example.toyexchange.data.remote.ToysApi
import retrofit2.Response

class ToysRepositoryImpl {
    private val apiService = RetrofitClient.retrofitInstance.create(ToysApi::class.javaObjectType)
    private val apiLoginService=RetrofitClient.retrofitInstanceLogin.create(AuthApi::class.javaObjectType)
    suspend fun getToys(): List<Toy> {
        return apiService.getToys()
}
    suspend fun searchToys(name:String): List<Toy>{
        return apiService.searchToys(name)
    }
   /* suspend fun getToydetailsById(id:Int):Toy{
        return apiService.getToyDetailsById(id)
    }*/
   suspend fun userLogin(request: Request): Response<UserLoginResponse> {
       return apiLoginService.userLogin(request)

   }
}