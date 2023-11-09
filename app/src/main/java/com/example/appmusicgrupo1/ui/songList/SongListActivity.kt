package com.example.appmusicgrupo1.ui.songList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appmusicgrupo1.R
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.remote.RemoteFavoriteDataSource
import com.example.appmusicgrupo1.data.repository.remote.RemoteSongDataSource
import com.example.appmusicgrupo1.databinding.ActivityMusicListBinding
import com.example.appmusicgrupo1.ui.favorites.FavoriteListActivity
import com.example.appmusicgrupo1.utils.Resource


class SongListActivity : ComponentActivity() {

    private lateinit var songAdapter: SongAdapter
    private val songRepository = RemoteSongDataSource();
    private val favoriteRepository = RemoteFavoriteDataSource();

    private val viewModel: SongViewModel by viewModels { SongViewModelFactory(songRepository, favoriteRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMusicListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songAdapter = SongAdapter(::onYoutubeClickList, viewModel::onFavoriteClickList)
        binding.songView.adapter = songAdapter


        viewModel.items.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if  (!it.data.isNullOrEmpty()) {

                        songAdapter.submitList(it.data)
                        Log.i("Prueba", "Ha ocurrido un cambio en la lista de canciones")
                    }
                }
                Resource.Status.ERROR -> {
                    //Log.i("Prueba", "Ha ocurrido un error en la lista de canciones")
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                }
                Resource.Status.LOADING -> {

                }
            }
        })

        viewModel.created.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.i("Prueba", "Ha entrado")
                    //viewModel.updateSongList()
                    viewModel.getFavorites()
                    cambioImagenFav()
                }
                Resource.Status.ERROR -> {
                    Log.i("Prueba", "error _created")
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    // de momento
                }
            }
        })

        binding.btnFavoritos.setOnClickListener() {
            val intent = Intent(this, FavoriteListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun onYoutubeClickList(song: Song) {
        Log.i("Canción", "Canción")
        val webIntent: Intent = Uri.parse(song.url).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }



}



