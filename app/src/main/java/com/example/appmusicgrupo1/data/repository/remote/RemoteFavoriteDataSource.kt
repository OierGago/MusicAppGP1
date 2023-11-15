package com.example.appmusicgrupo1.data.repository.remote

import com.example.appmusicgrupo1.data.Favorite
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.FavoriteRepository
import com.example.appmusicgrupo1.data.repository.SongRepository
import com.example.appmusicgrupo1.utils.Resource

class RemoteFavoriteDataSource  : BaseDataSource(), FavoriteRepository {
    override suspend fun getFavorites()= getResult {
        RetrofitClient.apiInterface.getFavorites()
    }

    override suspend fun deleteFromFavorite(idSong: Int)= getResult {
        RetrofitClient.apiInterface.deleteFromFavorite(idSong)
    }

    override suspend fun addFavorite(favorite: Favorite)= getResult {
        RetrofitClient.apiInterface.addFavorite(favorite)
    }
}