package com.choi.takeoff.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Converters {

    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap?): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String?) = Gson().fromJson(value, Array<String>::class.java)?.toList()

    @TypeConverter
    fun localDateTimeToString(value: LocalDateTime?): String = value.toString()

    @TypeConverter
    fun stringToLocalDateTime(value: String?): LocalDateTime? = LocalDateTime.parse(value,
        DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @TypeConverter
    fun uriToString(value: Uri?): String = value.toString()

    @TypeConverter
    fun stringToUri(value: String?): Uri = Uri.parse(value)
}