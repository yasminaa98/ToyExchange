package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("id")
    val id:Long,
    @SerializedName("username")
    val username:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("token")
    val auth_token:String

)
