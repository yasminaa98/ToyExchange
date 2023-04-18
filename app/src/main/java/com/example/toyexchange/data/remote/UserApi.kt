package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.User
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("/accounts/getUser/{username}")
    suspend fun getUserByUsername(@Path("username") username:String ): Response<User>
// update user info
    @PUT("/accounts/{id_user}/update-firstname")
    suspend fun updateFirstname(
    @Path("id_user") idUser:Long,
    @Query("newFirstName") newFirstName:String):Response<JsonObject>

    @PUT("/accounts/{id_user}/update-lastname")
    suspend fun updateLastname(
        @Path("id_user") idUser:Long,
        @Query("newLastName") newLastName:String):Response<JsonObject>

    @PUT("/accounts/{id_user}/update-homeaddress")
    suspend fun updateHomeAddress(
        @Path("id_user") idUser:Long,
        @Query("newHomeAddress") newHomeAddress:String):Response<JsonObject>



}