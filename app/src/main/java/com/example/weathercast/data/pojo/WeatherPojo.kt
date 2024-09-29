package com.example.weathercast.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "favoritplaces")
data class Location(
    @PrimaryKey
    var address: String,
    var latitude: Double,
    var longitude: Double
)
class MainTypeConverter {
    @TypeConverter
    fun fromMain(main: Main): String {
        val gson = Gson()
        return gson.toJson(main)
    }

    @TypeConverter
    fun toMain(json: String): Main {
        val gson = Gson()
        val type = object : TypeToken<Main>() {}.type
        return gson.fromJson(json, type)
    }
}
@Entity(tableName = "forecast_weather_data",
    foreignKeys = [ForeignKey(entity = City::class, parentColumns = ["id"], childColumns = ["city_id"])])
@TypeConverters(ListConverter::class)
data class ForcastWeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    @SerializedName("cod") val cod: String?,
    @SerializedName("message") val message: Int?,
    @SerializedName("cnt") val cnt: Int?,
    @ColumnInfo(name = "city_id")
    @SerializedName("city_id") val cityId: Int,
    @ColumnInfo(name = "weather_data_list")
    @SerializedName("list") val list: List<WeatherData>
)
class ListConverter {
    @TypeConverter
    fun fromWeatherDataList(list: List<WeatherData>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toWeatherDataList(json: String): List<WeatherData> {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherData>>() {}.type
        return gson.fromJson(json, type)
    }
}
@Entity(tableName = "current_weather_data",
        foreignKeys = [ForeignKey(entity = City::class, parentColumns = ["id"], childColumns = ["city_id"])])
@TypeConverters(CoordConverter::class, ListWeatherConverter::class,MainTypeConverter::class,WindConverter::class,CloudsConverter::class,SysCurrentConverter::class)
data class CurrentWeatherData(

    @SerializedName("coord"      ) var coord: Coord?             ,
    @SerializedName("weather"    ) var weather: ArrayList<Weather>? ,
    @SerializedName("base"       ) var base: String?          ,
    @SerializedName("main"       ) var main: Main?              ,
    @SerializedName("visibility" ) var visibility: Int?               ,
    @SerializedName("wind"       ) var wind: Wind?              ,
    @SerializedName("clouds"     ) var clouds: Clouds?  ,
    @ColumnInfo(name = "city_id")
    @SerializedName("city_id")
    val cityId: Int,
    @SerializedName("dt"         ) var dt: Int? ,
    @SerializedName("sys"        ) var sys: SysCurrent? ,
    @SerializedName("timezone"   ) var timezone: Int?  ,
    @PrimaryKey @SerializedName("id"         ) var id: Int?,
    @SerializedName("name"       ) var name: String?,
    @SerializedName("cod"        ) var cod: Int?

)



data class SysCurrent (

    @SerializedName("type"    ) var type    : Int?    ,
    @SerializedName("id"      ) var id      : Int?   ,
    @SerializedName("country" ) var country : String? ,
    @SerializedName("sunrise" ) var sunrise : Int?    ,
    @SerializedName("sunset"  ) var sunset  : Int?

)
class ListWeatherConverter {
    @TypeConverter
    fun fromWeatherList(list: List<Weather>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun toWeatherList(json: String): ArrayList<Weather> {
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {}.type
        return ArrayList(gson.fromJson(json, type) as Collection<Weather>)
    }

}
class WindConverter {
    @TypeConverter
    fun fromWind(wind: Wind): String {
        val gson = Gson()
        return gson.toJson(wind)
    }

    @TypeConverter
    fun toWind(json: String): Wind {
        val gson = Gson()
        val type = object : TypeToken<Wind>() {}.type
        return gson.fromJson(json, type)
    }
}
class CloudsConverter {
    @TypeConverter
    fun fromClouds(clouds: Clouds): String {
        val gson = Gson()
        return gson.toJson(clouds)
    }

    @TypeConverter
    fun toClouds(json: String): Clouds {
        val gson = Gson()
        val type = object : TypeToken<Clouds>() {}.type
        return gson.fromJson(json, type)
    }
}
class SysCurrentConverter {
    @TypeConverter
    fun fromSys(sys: SysCurrent): String {
        val gson = Gson()
        return gson.toJson(sys)
    }

    @TypeConverter
    fun toSys(json: String): SysCurrent {
        val gson = Gson()
        val type = object : TypeToken<SysCurrent>() {}.type
        return gson.fromJson(json, type)
    }
}
class SysConverter {
    @TypeConverter
    fun fromSys(sys: Sys): String {
        val gson = Gson()
        return gson.toJson(sys)
    }

    @TypeConverter
    fun toSys(json: String): Sys {
        val gson = Gson()
        val type = object : TypeToken<Sys>() {}.type
        return gson.fromJson(json, type)
    }
}
@TypeConverters(MainTypeConverter::class, ListWeatherConverter::class, CloudsConverter::class, WindConverter::class,SysConverter::class)

@Entity(tableName = "weather_data",
        foreignKeys = [ForeignKey(entity = ForcastWeatherData::class, parentColumns = ["id"], childColumns = ["forecast_id"])])

data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "forecast_id")
    val forecastId: Int?=null,
    @SerializedName("dt") val dt: Int?,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("visibility") val visibility: Int?,
    @SerializedName("pop") val pop: Double?,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("dt_txt") val dtTxt: String?
)
class CoordConverter {
    @TypeConverter
    fun fromCoord(coord: Coord): String {
        val gson = Gson()
        return gson.toJson(coord)
    }

    @TypeConverter
    fun toCoord(json: String): Coord {
        val gson = Gson()
        val type = object : TypeToken<Coord>() {}.type
        return gson.fromJson(json, type)
    }
}
data class Main(
    @SerializedName("temp") val temp: Double?,
    @SerializedName("feels_like") val feelsLike: Double?,
    @SerializedName("temp_min") val tempMin: Double?,
    @SerializedName("temp_max") val tempMax: Double?,
    @SerializedName("pressure") val pressure: Int?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("sea_level") val seaLevel: Int?,
    @SerializedName("grnd_level") val grndLevel: Int?,
    @SerializedName("temp_kf") val tempKf: Double?
)

data class Weather(
    @SerializedName("id") val id: Int?,
    @SerializedName("main") val main: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?
)

data class Clouds(
    @SerializedName("all") val all: Int?
)

data class Wind(
    @SerializedName("speed") val speed: Double?,
    @SerializedName("deg") val deg: Int?,
    @SerializedName("gust") val gust: Double?
)

data class Sys(
    @SerializedName("pod") val pod: String?
)

data class Rain(
    @SerializedName("3h") val threeHour: Double?
)

data class Snow(
    @SerializedName("3h") val threeHour: Double?
)

data class Coord(
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?
)
@Entity(tableName = "cities")
@TypeConverters(CoordConverter::class)
data class City(
    @PrimaryKey
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("coord") val coord: Coord,
    @SerializedName("country") val country: String?,
    @SerializedName("population") val population: Int?,
    @SerializedName("timezone") val timezone: Int?,
    @SerializedName("sunrise") val sunrise: Int?,
    @SerializedName("sunset") val sunset: Int?
)
