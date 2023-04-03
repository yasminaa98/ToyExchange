package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("username")
    val username:String,
    @SerializedName("password")
    val password:String
)
