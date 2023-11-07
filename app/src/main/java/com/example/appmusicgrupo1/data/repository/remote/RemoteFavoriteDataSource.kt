package com.example.appmusicgrupo1.data.repository.remote

import com.example.appmusicgrupo1.data.repository.FavoriteRepository
import com.example.appmusicgrupo1.data.repository.SongRepository

class RemoteFavoriteDataSource  : BaseDataSource(), FavoriteRepository {
    override suspend fun getFavorites()= getResult {
        RetrofitClient.apiInterface.getFavorites()
    }
}