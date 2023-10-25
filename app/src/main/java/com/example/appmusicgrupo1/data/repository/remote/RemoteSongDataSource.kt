package com.example.appmusicgrupo1.data.repository.remote

import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.SongRepository
import com.example.appmusicgrupo1.data.repository.remote.RetrofitClient.retrofitClient
import com.example.appmusicgrupo1.utils.Resource

class RemoteSongDataSource : BaseDataSource(), SongRepository {

    override suspend fun getSongs()= getResult {
        RetrofitClient.apiInterface.getSongs()
    }

    override suspend fun getSongsByAuthor(author:String)= getResult {
        RetrofitClient.apiInterface.getSongsByAuthor(author)
    }

    override suspend fun getSongsByTitle(title:String)= getResult {
        RetrofitClient.apiInterface.getSongsByTitle(title)
    }

    override suspend fun createSong(song: Song)= getResult {
        RetrofitClient.apiInterface.createSong(song)
    }

    override suspend fun deleteSong(id:Int)= getResult {
        RetrofitClient.apiInterface.deleteSong(id)
    }
}