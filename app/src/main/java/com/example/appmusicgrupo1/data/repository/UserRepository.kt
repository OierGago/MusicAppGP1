package com.example.appmusicgrupo1.data.repository

import com.example.appmusicgrupo1.data.PassChange
import com.example.appmusicgrupo1.data.UserRequest
import com.example.appmusicgrupo1.utils.Resource

interface UserRepository {
    suspend fun changePass(passChange: PassChange) : Resource<Int>
}