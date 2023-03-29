package com.example.toyexchange.Data.Repository

import com.example.toyexchange.Data.remote.RetrofitClient
import com.example.toyexchange.Data.remote.ToysApi
import com.example.toyexchange.Domain.model.Toy

class ToysRepositoryImpl {
    private val apiService = RetrofitClient.retrofitInstance.create(ToysApi::class.java)
    suspend fun getToys(): List<Toy> {
        return apiService.getToys()
}
    suspend fun searchToys(name:String): List<Toy>{
        return apiService.searchToys(name)
    }
   /* suspend fun getToydetailsById(id:Int):Toy{
        return apiService.getToyDetailsById(id)
    }*/

}