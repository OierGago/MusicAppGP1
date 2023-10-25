package com.example.appmusicgrupo1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val login:String,
    val name:String,
    val surname:String,
    val mail:String,
    val password:String,
    val activo:Boolean
) :Parcelable
