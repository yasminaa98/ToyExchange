package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class Exchange(
    @SerializedName("id")
    var id: Long,
    @SerializedName("sender")
    val sender: String,
    @SerializedName("receiver")
    val receiver: String,
    @SerializedName("id_receiver_annonce")
    val id_receiver_annonce: Long,
    @SerializedName("id_sender_annonce")
    val id_sender_annonce: Long,
    @SerializedName("status")
    val status: String
    )

