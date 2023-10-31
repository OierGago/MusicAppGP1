package com.example.appmusicgrupo1.data.repository

import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.utils.Resource

interface SongRepository {

    suspend fun getSongs() : Resource<List<Song>>
    suspend fun getSongsByAuthor(author:String): Resource<List<Song>>
    suspend fun getSongsByTitle(title:String): Resource<List<Song>>
    suspend fun createSong(song: Song): Resource<Integer>
    suspend fun deleteSong(id:Int): Resource<Integer>
}