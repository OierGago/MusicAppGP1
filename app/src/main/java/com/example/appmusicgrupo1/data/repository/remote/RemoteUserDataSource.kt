package com.example.appmusicgrupo1.data.repository.remote

import com.example.appmusicgrupo1.data.PassChange
import com.example.appmusicgrupo1.data.repository.UserRepository
import com.example.appmusicgrupo1.utils.Resource

class RemoteUserDataSource : BaseDataSource(), UserRepository {

    override suspend fun changePass(passChange: PassChange): Resource<Int> = getResult {
        RetrofitClient.apiInterface.changePass(passChange)
    }
}