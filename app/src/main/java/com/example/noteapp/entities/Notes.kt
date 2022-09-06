package com.example.noteapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Notes")
class Notes : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "subTitle")
    var subTitle: String? = null

    @ColumnInfo(name = "dateTime")
    var dateTime: String? = null

    @ColumnInfo(name = "statusNote")
    var statusNote: Boolean? = false

    @ColumnInfo(name = "noteText")
    var noteText: String? = null

    @ColumnInfo(name = "color")
    var color: String? = null

    override fun toString(): String {
        return "$title : $subTitle : $statusNote"
    }
}
