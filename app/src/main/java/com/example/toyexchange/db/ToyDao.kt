package com.example.toyexchange.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.toyexchange.Domain.model.Toy

@Dao
interface ToyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToy(toy: Toy)
    @Update
    suspend fun updateToy(toy: Toy)
    @Delete
    suspend fun deleteToy(toy: Toy)
    @Query("SELECT * FROM toysInformation")
    fun getAllToys():LiveData<List<Toy>>
}