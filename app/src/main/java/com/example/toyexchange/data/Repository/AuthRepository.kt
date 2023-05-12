package com.example.toyexchange.data.Repository

import com.example.toyexchange.Domain.model.ForgotPasswordResponse
import com.example.toyexchange.Domain.model.Request
import com.example.toyexchange.Domain.model.User
import com.example.toyexchange.Domain.model.UserLoginResponse
import com.example.toyexchange.data.remote.AuthApi
import com.google.gson.JsonObject
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiLoginService: AuthApi
) {

    suspend fun userLogin(request: Request): Response<UserLoginResponse> {
        return apiLoginService.userLogin(request)

    }
    suspend fun userSignUp(user: User): Response<String> {
        return apiLoginService.userSignUp(user)
    }
    suspend fun forgotPassword(email:String):Response<ForgotPasswordResponse>{
        return apiLoginService.forgetPassword(email)
    }
    suspend fun resetPassword(token:String,newPassword:String){
        return apiLoginService.resetPassword(token,newPassword)
    }


    suspend fun getUserByUsername(username:String):Response<User>{
        return apiLoginService.getUserByUsername(username)
    }
    suspend fun updateFirstname(
        idUser:Long,
        newFirstName:String):Response<JsonObject>{
        return apiLoginService.updateFirstname(idUser,newFirstName)
    }
    suspend fun updateLastname(
        idUser:Long,
        newLastName:String):Response<JsonObject>{
        return apiLoginService.updateLastname(idUser,newLastName)
    }
    suspend fun updateHomeAddress(
        idUser:Long,
        newHomeAddress:String):Response<JsonObject>{
        return apiLoginService.updateHomeAddress(idUser,newHomeAddress)
    }
    suspend fun updatePicture(
        idUser:Long,
        newPicture:String):Response<JsonObject>{
        return apiLoginService.updatePicture(idUser,newPicture)
    }
}