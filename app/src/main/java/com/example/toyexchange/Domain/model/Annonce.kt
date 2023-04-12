package com.example.toyexchange.Domain.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Annonce(
   // @SerializedName("id")
   // var id: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
  //  @SerializedName("picture")
  //  val image_url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("ageChild")
    val age_child: String,
    @SerializedName("ageToy")
    val age_toy: String,
    /* @SerializedName("owner")
     val owner:User */

)