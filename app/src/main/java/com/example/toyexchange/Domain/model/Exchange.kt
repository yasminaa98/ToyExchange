package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

data class Exchange(
    @SerializedName("id")
    var id: Long,
    @SerializedName("username")
    val username: String,
    @SerializedName("id_HisAnnonce")
    val id_HisAnnonce: Long,
    @SerializedName("id_AnnonceToExchange")
    val id_AnnonceToExchange: Long,
    @SerializedName("status")
    val status: Boolean
    )

