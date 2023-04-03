package com.example.toyexchange.Domain.model

import com.google.gson.annotations.SerializedName

class Favorite (
    @SerializedName("favorite_id")
    var favorite_id: Int,
    @SerializedName("user_id")
    var user:User,
    @SerializedName("annonce_id")
    var toy:Toy,

)