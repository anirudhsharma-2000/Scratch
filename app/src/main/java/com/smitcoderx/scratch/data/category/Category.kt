package com.smitcoderx.scratch.data.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val color: Long
)

enum class Categories(val title: String) {
    Notes("Notes"),
    Scribble("Scribble"),
    Encrypted("Encrypted"),
    Todo("TODO"),
    List("List")
}
