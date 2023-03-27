package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class Toy(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double

)
