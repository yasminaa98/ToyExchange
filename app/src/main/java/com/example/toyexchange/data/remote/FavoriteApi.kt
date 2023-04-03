package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.Favorite
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApi {
    @GET("/api/favorites/{id_user}/getAllFavorite")
    suspend fun getAllFavorites(@Path("id_user") id_user:Int ): List<Favorite>
    @POST("/api/favorites/{id_annonce}/add-to-favorites")
    suspend fun addToFavorites(@Path("id_annonce") id_annonce:Int)
    @DELETE("/api/favorites/{id_annonce}/remove-from-favorites")
    suspend fun removeFromFavorites(@Path("id_annonce") idAnnonce: Long)
}