package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.Annonce
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.User
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ToysApi {
    @POST("api/annonces/add")
    suspend fun addToy(
        @Header("Authorization") token: String,
        @Body annonce: Annonce
    ): Response<JsonObject>

    @GET("api/annonces/getAll")
    suspend fun getToys(@Header("Authorization") token: String): Response<List<Toy>>

    @GET("api/annonces/getAll")
    suspend fun searchToys(@Query("name") name: String): List<Toy>

    @GET("api/annonces/getAll")
    suspend fun searchToysByCategory(@Query("category") category: String): List<Toy>

    @GET("api/annonces/getUserAnnonces")
    suspend fun getUserAnnonces(@Header("Authorization") token: String):Response<List<Annonce>>
    @PUT("api/annonces/{id_annonce}/modify")
    suspend fun modifyAnnonce(
        @Header("Authorization") token: String,
        @Path("id_annonce") idAnnonce:Long ,
    @Body annonce: Annonce):Response<JsonObject>

    @GET("api/annonces/{id_annonce}/AnnonceOwner")
    suspend fun getAnnonceOwner(@Path("id_annonce") idAnnonce: Long):Response<User>

    @GET("api/annonces/{id_annonce}/getAnnonceById")
    suspend fun getAnnonceById(@Path("id_annonce") idAnnonce: Long):Response<Annonce>
    @GET("api/annonces/{id_auction}/getAnnonceByAuction")
    suspend fun getAnnonceByAuction(@Path("id_auction") idAuction: Long):Response<Annonce>


}