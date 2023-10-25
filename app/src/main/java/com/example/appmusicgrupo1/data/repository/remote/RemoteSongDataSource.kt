package com.example.appmusicgrupo1.data.repository.remote

import com.example.appmusicgrupo1.data.repository.SongRepository
import com.example.appmusicgrupo1.data.repository.remote.RetrofitClient.retrofitClient

class RemoteSongDataSource : BaseDataSource(), SongRepository {

    override suspend fun getSongs()= getResult {
        RetrofitClient.apiInterface.getSongs()
    }
}