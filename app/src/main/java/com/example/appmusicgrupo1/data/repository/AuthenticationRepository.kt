package com.example.appmusicgrupo1.data.repository


import com.example.appmusicgrupo1.data.AuthenticationResponse
import com.example.appmusicgrupo1.utils.Resource

interface AuthenticationRepository {
    suspend fun login(authenticationRequest: AuthenticationRequest) : Resource<AuthenticationResponse>



    // TODOregistro
}