package com.example.toyexchange.Data.remote

import com.example.toyexchange.Common.Constants
import com.example.toyexchange.Common.Constants.BASE_URL
import com.example.toyexchange.Domain.model.Toy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object RetrofitClient {
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}