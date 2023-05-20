package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("homeAddress")
    val homeAddress:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("id")
    var id: Long,
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
    @SerializedName("profilePicture")
    val profile_picture_path: String
   /* @SerializedName("description")
    val description: String */
)
