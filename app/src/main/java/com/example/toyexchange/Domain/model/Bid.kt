package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class Bid(

    @SerializedName("id")
    var id: Long,
    @SerializedName("note")
    val note: String,
    @SerializedName("price_proposed")
    val price_proposed: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("auction_id")
    val auctionId: Long,
    @SerializedName("profilePicture")
    val profile_picture_path: String

)
