package com.fuad.dicoding_event.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventEntity (

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "mediaCover")
    var mediaCover: String = "",
)