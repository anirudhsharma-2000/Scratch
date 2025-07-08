package com.smitcoderx.scratch.data.home

data class Category(
    val id: Int,
    val name: String,
    val type: String,
    val color: ULong
)

enum class Categories(val title: String) {
    Notes("Notes"),
    Scribble("Scribble"),
    Encrytped("Encrypted"),
    Todo("TODO"),
    List("List")
}
