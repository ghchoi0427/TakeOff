package com.choi.takeoff.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4
@Entity(tableName = "memo")
data class Memo(
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "picture") var picture: String?,
    @ColumnInfo(name = "time") var time: String?,
    @ColumnInfo(name = "mood") var mood: Int?,
    @ColumnInfo(name = "tags") var tags: List<String>?,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    var rowid: Int = 0
}
