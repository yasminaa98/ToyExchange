package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("id")
    val id:Int,
    @SerializedName("username")
    val username:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("accessToken")
    val auth_token:String

)
