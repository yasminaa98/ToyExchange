package com.example.toyexchange.Domain.model

data class Toy(
    val category: String,
    val description: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val price: Double

)
