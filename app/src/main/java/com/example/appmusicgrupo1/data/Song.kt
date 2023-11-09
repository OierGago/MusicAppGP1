package com.example.appmusicgrupo1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val id: Int,
    val titulo: String,
    val autor: String,
    val url: String,
    val imagen:String,
    var favorito:Boolean = false,
): Parcelable
