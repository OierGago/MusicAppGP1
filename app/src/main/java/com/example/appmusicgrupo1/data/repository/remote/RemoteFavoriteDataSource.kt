package com.example.appmusicgrupo1.data.repository.remote

import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.FavoriteRepository
import com.example.appmusicgrupo1.data.repository.SongRepository
import com.example.appmusicgrupo1.utils.Resource

class RemoteFavoriteDataSource  : BaseDataSource(), FavoriteRepository {
    override suspend fun getFavorites(id: Int)= getResult {
        RetrofitClient.apiInterface.getFavorites(id)
    }

    override suspend fun deleteFromFavorite(idUser: Int, idSong: Int)= getResult {
        RetrofitClient.apiInterface.deleteFromFavorite(idUser, idSong)
    }

    override suspend fun addFavorite(song: Song)= getResult {
        RetrofitClient.apiInterface.addFavorite(song)
    }
}