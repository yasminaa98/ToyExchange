package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class UserRegisterResponse(
    @SerializedName("id")
    val id:Long,
    @SerializedName("message")
    val message:String,
    @SerializedName("token")
    val auth_token:String)
