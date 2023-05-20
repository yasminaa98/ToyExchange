package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class UpdatePictureResponse (
    @SerializedName("profilePicture")
    val pictureName:String,
    @SerializedName("message")
    val message:String
)