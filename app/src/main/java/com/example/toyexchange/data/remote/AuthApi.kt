package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.*
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
   @POST("api/auth/signin")
    suspend fun userLogin(@Body request: Request): Response<UserLoginResponse>
    @POST("api/auth/signup")
    suspend fun userSignUp(@Body user: User):Response<UserRegisterResponse>
    @POST("api/auth/forgot-password")
    suspend fun forgetPassword(@Query("email") email:String):Response<ForgotPasswordResponse>
    @POST("api/auth/reset-password")
    suspend fun resetPassword(@Query("token") token:String,
                              @Query("newPassword") newPassword:String)
    @GET("/accounts/getUserByUsername/{username}")
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

    @PUT("/accounts/{id_user}/update-picture")
    suspend fun updatePicture(
        @Path("id_user") idUser:Long,
        @Query("newPicture") newPicture:String):Response<JsonObject>

}