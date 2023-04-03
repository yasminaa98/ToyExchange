package com.example.toyexchange.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.toyexchange.Domain.model.Toy

@Database(entities=[Toy::class], version = 1, exportSchema = false)
abstract class ToyDatabase : RoomDatabase(){
    //create instance of dao interface by an abstract fun
    // because we used abstract here and in the class we don't need to
    //implement the methods in the dao interface room will write the required methods for us

    abstract fun toyDao():ToyDao
    // now we will create a fun to return an instance of this database class
    // we will write that fun in companion object so we can call this fun by the name of this class
    companion object{
        @Volatile // any changes on this instance from any thread will be visible by any other thread
        var INSTANCE:ToyDatabase?=null
        @Synchronized // only one thread can have instance of this room db
        fun getInstance(context: Context):ToyDatabase{
            if (INSTANCE==null){
                INSTANCE= Room.databaseBuilder(
                    context,
                    ToyDatabase::class.java,
                    "toy.db" )
                    .build()
                }
            return INSTANCE as ToyDatabase

        }


        }
}
