package com.example.toyexchange.Data.remote

import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.ToysList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.QueryName

interface ToysApi {
   /* @POST("/toys.json?key=ae86fe50")
    suspend fun addToys(@Body toys: Toy): List<Toy> */
    @GET("/toys.json?key=ae86fe50")
    suspend fun getToys():List<Toy>
    @GET("/toys.json?key=ae86fe50")
    suspend fun searchToys(@Query("name") name:String) :List<Toy>
    @GET("/toys.json?key=ae86fe50")
    suspend fun getToyDetailsById(@Query("id") id:Int):Toy


}