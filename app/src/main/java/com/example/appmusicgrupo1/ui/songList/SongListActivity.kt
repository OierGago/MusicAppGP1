package com.example.appmusicgrupo1.ui.songList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.remote.RemoteSongDataSource
import com.example.appmusicgrupo1.databinding.ActivityMusicListBinding
import com.example.appmusicgrupo1.utils.Resource


class SongListActivity : ComponentActivity() {

    private lateinit var songAdapter: SongAdapter
    private val songRepository = RemoteSongDataSource();

    private val viewModel: SongViewModel by viewModels { SongViewModelFactory(
        songRepository

    )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMusicListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songAdapter = SongAdapter(::onYoutubeClickList, ::onFavoriteClickList)
        binding.songView.adapter = songAdapter
        Log.i("Prueba", "11")


        viewModel.items.observe(this, Observer {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if  (!it.data.isNullOrEmpty()) {
                        Log.i("Prueba", "Ha ocurrido un cambio en la lista de cancwdiones")
                        songAdapter.submitList(it.data)
                    }
                }
                Resource.Status.ERROR -> {
                    Log.i("Prueba", "Ha ocurrido un error en la lista de canciones")
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                }
                Resource.Status.LOADING -> {

                }
            }
        })

        viewModel.created.observe(this, Observer {
            Log.i("Prueba", "ha entrado aqui")
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.i("Prueba", "Ha entrado")
                    viewModel.updateSongList()
                }
                Resource.Status.ERROR -> {
                    Log.i("Prueba", "Ha ocurrido un error en la lista de canciones")
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    // de momento
                }
            }
        })
    }

    private fun onYoutubeClickList(song: Song) {
        Log.i("Canción", "Canción")
        val webIntent: Intent = Uri.parse(song.url).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }

    private fun onFavoriteClickList(song: Song) {
//        if() {
//            FavoriteviewModel.deleteFromFavorite(idUser, idSong)
//        }
//            else{
//            }
        Log.i("Favorito", "Favorito")
    }

}

//        binding.btnFavorito.setOnclickListener() {
//            val intent = Intent(this, FavoriteActivity::class.java)
//            finish()
//            startActivity(intent)
//        }

