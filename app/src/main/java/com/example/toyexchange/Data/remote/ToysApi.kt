package com.example.toyexchange.Data.remote

import com.example.toyexchange.Domain.model.Toy
import retrofit2.http.GET
import retrofit2.http.Query

interface ToysApi {
   /* @POST("/toys.json?key=ae86fe50")
    suspend fun addToys(@Body toys: Toy): List<Toy> */
    @GET("/toys.json?key=ae86fe50")
    suspend fun getToys():List<Toy>
    @GET("/toys.json?key=ae86fe50")
    suspend fun searchToys(@Query("name") name:String) :List<Toy>
  /*  @GET("/toys.json?key=ae86fe50")
    suspend fun getToyDetailsById(@Query("id") id:Int):Toy*/












}