package com.example.appmusicgrupo1.data.repository.remote

import com.example.appmusicgrupo1.data.AuthenticationResponse
import com.example.appmusicgrupo1.data.repository.AuthenticationRepository
import com.example.appmusicgrupo1.data.repository.AuthenticationRequest
import com.example.appmusicgrupo1.utils.Resource

class RemoteAuthenticationRepository :  BaseDataSource(), AuthenticationRepository {
    override suspend fun login(authenticationRequest : AuthenticationRequest): Resource<AuthenticationResponse> = getResult {
        RetrofitClient.apiInterface.login(authenticationRequest)
    }
}
