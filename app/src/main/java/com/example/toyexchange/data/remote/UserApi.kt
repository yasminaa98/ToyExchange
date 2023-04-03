package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("/accounts/getUser/{username}")
    suspend fun getUserByUsername(@Path("username") username:String ) : User
// update user info

}