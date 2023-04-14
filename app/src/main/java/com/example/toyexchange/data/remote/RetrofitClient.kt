package com.example.toyexchange.data.remote

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.toyexchange.BuildConfig
import com.example.toyexchange.Common.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

object RetrofitClient {
    // private lateinit var sharedPreferences: SharedPreferences
    /*  fun initSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences("authToken", Context.MODE_PRIVATE)
    }

    val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${sharedPreferences.getString("authToken",null)}")
                .build()
            chain.proceed(request)
        }
        .build() */
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.client(httpClient)
            /*  .client(
                OkHttpClient.Builder()
                    .addInterceptor{
                        chain ->  chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Authorization","Bearer $authToken")
                    }.build())
                    }.also {
                        client ->
                        if(BuildConfig.DEBUG){
                            val logging=HttpLoggingInterceptor()
                            logging.setLevel((HttpLoggingInterceptor.Level.BODY))
                            client.addInterceptor(logging)
                        }
                    }.build()
            )*/
            .addConverterFactory(GsonConverterFactory.create())

            .build()
    }
}
       /*  val retrofitInstanceLogin: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
               .client(httpClient)
               .client(
                    OkHttpClient.Builder()
                        .addInterceptor{
                                chain ->  chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Authorization","Bearer $authToken")
                        }.build())
                        }.also {
                                client ->
                            if(BuildConfig.DEBUG){
                                val logging=HttpLoggingInterceptor()
                                logging.setLevel((HttpLoggingInterceptor.Level.BODY))
                                client.addInterceptor(logging)
                            }
                        }.build()
                )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    } */





