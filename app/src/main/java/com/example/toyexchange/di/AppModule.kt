package com.example.toyexchange.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.toyexchange.Common.Constants.BASE_URL
import com.example.toyexchange.data.remote.ToysApi
import com.example.toyexchange.db.ToyDao
import com.example.toyexchange.db.ToyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
