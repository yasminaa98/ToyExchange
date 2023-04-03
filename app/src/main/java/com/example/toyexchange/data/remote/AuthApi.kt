package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.Request
import com.example.toyexchange.Domain.model.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
   @POST("api/auth/signin")
    suspend fun userLogin(@Body request: Request): Response<UserLoginResponse>
}