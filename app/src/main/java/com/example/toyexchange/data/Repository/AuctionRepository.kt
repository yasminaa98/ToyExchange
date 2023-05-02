package com.example.toyexchange.data.Repository

import com.example.toyexchange.Domain.model.AuctionResponse
import com.example.toyexchange.Domain.model.Bid
import com.example.toyexchange.Domain.model.User
import com.example.toyexchange.data.remote.AuctionApi
import com.google.gson.JsonObject
import retrofit2.Response
import javax.inject.Inject

class AuctionRepository @Inject constructor(
    private val apiAuctionService: AuctionApi
) {
    suspend fun getAllAuctions(): Response<List<AuctionResponse>> {
        return apiAuctionService.getAllAuctions()
    }

    suspend fun getUserAuctions(token: String):Response<List<AuctionResponse>>{
        return apiAuctionService.getUserAuctions(token)
    }

    suspend fun getAuctionPrice(idAuction:Long,token:String):Response<JsonObject>{
        return apiAuctionService.getAuctionPrice(idAuction,token)
    }

    suspend fun getAuctionBids(idAuction:Long,token:String):Response<List<Bid>>{
        return apiAuctionService.getAuctionBids(idAuction,token)
    }
    suspend fun getUserBid(idAuction:Long,token:String):Response<Bid>{
        return apiAuctionService.getUserBid(idAuction,token)
    }
    suspend fun addBid(bid: Bid,idAuction:Long,token:String):Response<JsonObject>{
        return apiAuctionService.addBid(bid, idAuction,token)
    }
    suspend fun updateBidPrice(idAuction:Long,newPrice:String,token:String):Response<JsonObject>{
        return apiAuctionService.updateBidPrice(idAuction,newPrice,token)
    }
    suspend fun deleteAuction(idAuction: Long):Response<JsonObject>{
        return apiAuctionService.deleteAuction(idAuction)
}
    suspend fun addAuction(idAnnonce: Long,auction:AuctionResponse,token:String):Response<JsonObject>{
        return apiAuctionService.addAuction(idAnnonce,auction,token)
    }
    suspend fun checkExistentAuction(idAnnonce: Long,token:String):Response<JsonObject>{
        return apiAuctionService.checkExistentAuction(idAnnonce,token)
    }

    suspend fun getAuctionOwner(idAuction:Long):Response<User>{
        return apiAuctionService.getAuctionOwner(idAuction)
    }

}