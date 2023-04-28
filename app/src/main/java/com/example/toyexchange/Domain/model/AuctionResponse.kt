package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class AuctionResponse(
    @SerializedName("id")
    var id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("initial_price")
    val initial_price: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("picturePath")
    val image_url: String,
    @SerializedName("start_dateTime")
    val start_datetime: String,
    @SerializedName("end_dateTime")
    val end_dateTime: String

)
