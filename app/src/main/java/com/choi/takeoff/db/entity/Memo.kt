package com.choi.takeoff.db.entity

import android.graphics.Bitmap
import androidx.room.*

@Fts4
@Entity(tableName = "memo")
data class Memo(
    @PrimaryKey var id: Int,
    @ColumnInfo var content: String?,
    @ColumnInfo var picture: Bitmap?,
    @ColumnInfo var tags: List<String>?
)
