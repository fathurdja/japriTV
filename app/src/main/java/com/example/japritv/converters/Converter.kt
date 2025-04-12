package com.example.japritv.converters

import androidx.room.TypeConverter
import com.example.japritv.model.BankList
import com.example.japritv.model.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converter {

    @TypeConverter
    fun fromVideoListToJson(videos: List<Video>): String {
        return Json.encodeToString(videos)  // Correctly serialize List<Video> to JSON string
    }

    // Convert JSON string back to List<Video>
    @TypeConverter
    fun fromJsonToVideoList(videosJson: String): List<Video> {
        return Json.decodeFromString(videosJson)  // Correctly deserialize JSON string to List<Video>
    }

    @TypeConverter
    fun fromBankInfoList(value: List<BankList>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toBankInfoList(value: String): List<BankList> {
        val listType = object : TypeToken<List<BankList>>() {}.type
        return Gson().fromJson(value, listType)
    }
}