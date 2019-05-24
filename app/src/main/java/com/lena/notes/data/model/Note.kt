package com.lena.notes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "text") var text: String = "",
    @ColumnInfo(name = "date") var date: Long = 0
)