package com.example.appmusicgrupo1.data.repository

import com.example.appmusicgrupo1.data.Favorite
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.utils.Resource

interface FavoriteRepository {
    suspend fun getFavorites() : Resource<List<Song>>
}