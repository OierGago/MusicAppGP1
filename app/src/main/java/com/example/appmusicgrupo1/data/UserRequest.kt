package com.example.appmusicgrupo1.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRequest(
    val login:String,
    val nombre:String,
    val apellido:String,
    val mail:String,
    val contrasenya:String,
) :Parcelable
