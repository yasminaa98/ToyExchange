package com.example.toyexchange.di

import com.example.toyexchange.Common.Constants.BASE_URL
import com.example.toyexchange.data.remote.AuctionApi
import com.example.toyexchange.data.remote.AuthApi
import com.example.toyexchange.data.remote.ToysApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides // create functions that creates our dependencies
    @Singleton // make sur we have single instance of whatever the function returns
    fun provideToysApi(): ToysApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ToysApi::class.java)
    }

    @Provides // create functions that creates our dependencies
    @Singleton // make sur we have single instance of whatever the function returns
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides // create functions that creates our dependencies
    @Singleton // make sur we have single instance of whatever the function returns
    fun provideAuctionApi(): AuctionApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuctionApi::class.java)
    }
  /*  @Provides // create functions that creates our dependencies
    fun provideToyDatabase(@ApplicationContext context: Context): ToyDatabase {
        return Room.databaseBuilder(
            context,
            ToyDatabase::class.java,
            "toy.db"
        ).build()
    }

    fun toyDao(db:ToyDatabase):ToyDao{
        return db.toyDao()
    } */
}
