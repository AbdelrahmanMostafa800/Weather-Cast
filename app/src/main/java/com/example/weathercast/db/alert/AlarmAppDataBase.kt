package com.example.weathercast.db.alert



import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.Location
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


@Database(entities = [AlertItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class AlarmAppDataBase : RoomDatabase() {
    abstract fun getAlarmDAO(): AlarmDAO?

    companion object {
        @Volatile
        private var instance: AlarmAppDataBase? = null

        @Synchronized
        fun getInstance(context: Context): AlarmAppDataBase? {
            if (instance == null) {
                instance = databaseBuilder(
                    context.applicationContext,
                    AlarmAppDataBase::class.java, "alarm_database"
                ).build()
            }
            return instance
        }
    }
}
// Converters.kt
object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.let { it.toEpochSecond(ZoneOffset.UTC) }
    }
}