package com.choi.takeoff.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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
    fun stringToLocalDateTime(value: String?): LocalDateTime? = LocalDateTime.parse(
        value,
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    )

    @TypeConverter
    fun uriToString(value: Uri?): String = value.toString()

    @TypeConverter
    fun stringToUri(value: String?): Uri = Uri.parse(value)

    companion object {
        fun uriToBitmap(uri: String, contentResolver: ContentResolver): Bitmap =
            MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(uri))

        fun uriToByteArray(uri: Uri, contentResolver: ContentResolver): ByteArray {
            val outputStream = ByteArrayOutputStream()
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
                ?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        }

        fun byteArrayToBitmap(bytes: ByteArray): Bitmap? {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        fun byteArrayToReducedBitmap(bytes: ByteArray, ratio: Int): Bitmap? {
            val reduce = BitmapFactory.Options()
            reduce.inSampleSize = ratio
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, reduce)
        }

        fun stringToLocalDate(string: String, formatter: DateTimeFormatter): LocalDate {
            return LocalDate.parse(string, formatter)
        }


        fun localDateToFormattedString(localDate: LocalDate): String {
            return localDate.format(DateTimeFormatter.ofPattern("MM월dd일 HH:mm")).toString()
        }

        fun stringToTimestampFloat(value: String): Float {
            return Timestamp.valueOf(value).time.toFloat()
        }

    }
}