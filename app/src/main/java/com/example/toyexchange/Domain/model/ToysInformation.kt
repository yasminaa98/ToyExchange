package com.example.toyexchange.Domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName="toysInformation")
data class ToysInformation(
@SerializedName("id")
@PrimaryKey
var id: Long,
@SerializedName("category")
val category: String,
@SerializedName("description")
val description: String,
@SerializedName("picture")
val picturePath: String,
@SerializedName("name")
val name: String,
@SerializedName("price")
val price: String,
@SerializedName("state")
val state: String,
@SerializedName("age_child")
val age_child: String,
@SerializedName("age_toy")
val age_toy: String,
@SerializedName("estArchive")
val estArchive:Boolean

)
