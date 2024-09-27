package com.example.mvvm.db



import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.weathercast.data.pojo.Location


@Database(entities = [Location::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getLocationDAO(): LocationDAO?

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        @Synchronized
        fun getInstance(context: Context): AppDataBase? {
            if (instance == null) {
                instance = databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java, "product_database"
                ).build()
            }
            return instance
        }
    }
}