package com.example.appmusicgrupo1.data.repository

import com.example.appmusicgrupo1.data.Favorite
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.utils.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

interface FavoriteRepository {
    suspend fun getFavorites() : Resource<List<Song>>

    suspend fun deleteFromFavorite(idSong : Int): Resource<Integer>

    suspend fun addFavorite(favorite: Favorite): Resource<Integer>
}