package com.example.appmusicgrupo1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthenticationResponse (
    val id :Int,
    val contrasenya:String,
    val login: String,
    val accessToken: String,
): Parcelable