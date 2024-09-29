package com.example.weathercast.db.location



import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.weathercast.data.pojo.Location


@Database(entities = [Location::class], version = 1)
abstract class LocationAppDataBase : RoomDatabase() {
    abstract fun getLocationDAO(): LocationDAO?

    companion object {
        @Volatile
        private var instance: LocationAppDataBase? = null

        @Synchronized
        fun getInstance(context: Context): LocationAppDataBase? {
            if (instance == null) {
                instance = databaseBuilder(
                    context.applicationContext,
                    LocationAppDataBase::class.java, "location_database"
                ).build()
            }
            return instance
        }
    }
}