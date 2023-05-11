package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("emailMessage")
    val emailMessage:String,
    @SerializedName("tokenMessage")
    val tokenMessage:String

)
