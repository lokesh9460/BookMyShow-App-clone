package com.example.bookmyshowapp.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookmyshowapp.MovieResponse
import com.example.bookmyshowapp.dao.MovieDao
import com.example.bookmyshowapp.typeConverter.MovieTypeConverter


@Database(entities = [MovieResponse::class],version = 1)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase:RoomDatabase()
{
    abstract fun movieDao():MovieDao
    companion object{
        private const val DATABASE_NAME="movie-app"

        //@volatile
        private var INSTANCE:MovieDatabase?=null

        fun getInstance(context:Context):MovieDatabase{
            synchronized(this)
            {
                var instance:MovieDatabase?= INSTANCE
                if(instance==null) {
                    instance = Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, DATABASE_NAME).build()
                    INSTANCE=instance

                }
                return instance
            }
        }

    }
}