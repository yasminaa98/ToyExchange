package com.example.toyexchange.data.remote

import com.example.toyexchange.Domain.model.AuctionResponse
import com.example.toyexchange.Domain.model.Bid
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface AuctionApi {

    @GET("api/auctions/getAllAuctions")
    suspend fun getAllAuctions():Response<List<AuctionResponse>>

    @GET("api/auctions/getUserAuctions")
    suspend fun getUserAuctions( @Header("Authorization") token: String):Response<List<AuctionResponse>>

    @GET("api/auctions/{id_auction}/getPrice")
    suspend fun getAuctionPrice(@Path("id_auction") idAuction: Long,
                                @Header("Authorization") token: String):Response<JsonObject>

    @GET("api/bids/{id_auction}/getBids")
    suspend fun getAuctionBids(@Path("id_auction") idAuction: Long,
                               @Header("Authorization") token: String):Response<List<Bid>>

    @POST("api/bids/{id_auction}/addBid")
    suspend fun addBid(@Body bid: Bid,
                        @Path("id_auction") idAuction: Long,
                        @Header("Authorization") token: String):Response<JsonObject>


    @GET("api/bids/{id_auction}/getUserBid")
    suspend fun getUserBid(@Path("id_auction") idAuction: Long,
                               @Header("Authorization") token: String):Response<Bid>


    @PUT("api/bids/{id_auction}/update_price")
    suspend fun updateBidPrice(
                               @Path("id_auction") idAuction: Long,
                               @Query("newPrice") newPrice:String,
                               @Header("Authorization") token: String):Response<JsonObject>


    @DELETE("api/auctions/{id_auction}/delete-auction")
    suspend fun deleteAuction(@Path("id_auction") idAuction: Long):Response<JsonObject>

}
