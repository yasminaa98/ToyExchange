package com.example.toyexchange.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.toyexchange.Domain.model.Toy
import com.example.toyexchange.Domain.model.ToysInformation

@Dao
interface ToyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToy(toy: ToysInformation)
    @Update
    suspend fun updateToy(toy: ToysInformation)
    @Delete
    suspend fun deleteToy(toy: ToysInformation)
    @Query("SELECT * FROM toysInformation")
   suspend fun getAllToys():List<ToysInformation>
}