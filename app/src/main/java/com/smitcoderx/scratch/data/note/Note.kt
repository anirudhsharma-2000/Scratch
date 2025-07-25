package com.smitcoderx.scratch.data.note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Note")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val title: String,
    val content: String,
    val categoryId: Int? = 0,
    val createdAt: Long = System.currentTimeMillis()
)
