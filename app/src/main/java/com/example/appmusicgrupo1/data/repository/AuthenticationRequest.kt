package com.example.appmusicgrupo1.data.repository


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthenticationRequest (
    val login: String,
    val contrasenya: String
): Parcelable