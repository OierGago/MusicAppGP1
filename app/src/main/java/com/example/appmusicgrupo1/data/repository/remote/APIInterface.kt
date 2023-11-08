package com.example.appmusicgrupo1.data.repository.remote

import com.example.appmusicgrupo1.data.Song
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIInterface {

    @GET("songs")
    suspend fun getSongs(): Response<List<Song>>

    @GET("song/author/{author}")
    suspend fun getSongsByAuthor(@Path("author") author : String): Response<List<Song>>

    @GET("song/title/{title}")
    suspend fun getSongsByTitle(@Path("title") title : String): Response<List<Song>>

    @POST("song")
    suspend fun createSong(@Body song : Song): Response<Integer>

    @DELETE("song/{id}")
    suspend fun deleteSong(@Path("id") id : Int): Response<Integer>

    @GET("favorite/{id}")
    suspend fun getFavorites(@Path("id") id : Int): Response<List<Song>>

    @DELETE("favorite/{idUser}/{idSong}")
    suspend fun deleteFromFavorite(@Path("idUser") idUser: Int,@Path("idSong") idSong : Int): Response<Integer>

    @POST("favorite/")
    suspend fun addFavorite(@Body song: Song): Response<Integer>

}