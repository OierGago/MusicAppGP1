package com.example.appmusicgrupo1.data.repository

import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.utils.Resource

interface SongRepository {

    suspend fun getSongs(): Resource<List<Song>>
}