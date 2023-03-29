package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("location")
    val location:String,
    @SerializedName("age")
    val age:Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname:String,
    @SerializedName("phone")
    val phone: Int,
)
