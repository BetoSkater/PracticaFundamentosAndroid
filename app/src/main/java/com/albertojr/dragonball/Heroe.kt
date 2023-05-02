package com.albertojr.dragonball


data class Heroe(
    val photo: String,
    val id: String,
    val favorite: Boolean,
    val description: String,
    val name: String,
    var hitPoints : Int = 100
)