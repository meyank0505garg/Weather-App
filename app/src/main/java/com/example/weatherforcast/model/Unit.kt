package com.example.weatherforcast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "settings_table")
data class Unit (
    @PrimaryKey
    @ColumnInfo(name = "Units")
    val unit : String
)