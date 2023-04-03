package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("homeAddress")
    val homeAddress:String,
    @SerializedName("email")
    val email:Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname:String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: Int,
    @SerializedName("avgResponseTime")
    val avgResponseTime: String,
    @SerializedName("description")
    val description: String
)
