package com.choi.takeoff.db.entity

import android.graphics.Bitmap
import androidx.room.*
import java.util.*

@Fts4
@Entity(tableName = "memo")
data class Memo(
    @PrimaryKey @ColumnInfo(name = "rowid") var rowid: Int,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "picture") var picture: Bitmap?,
    @ColumnInfo(name = "time") var time: Date?,
    @ColumnInfo(name = "mood") var mood: Int?,
    @ColumnInfo(name = "tags") var tags: List<String>?,
)
