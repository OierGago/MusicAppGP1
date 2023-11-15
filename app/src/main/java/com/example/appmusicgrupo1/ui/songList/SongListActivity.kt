package com.example.appmusicgrupo1.ui.songList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.appmusicgrupo1.MyApp
import com.example.appmusicgrupo1.UserPreferences
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.remote.RemoteFavoriteDataSource
import com.example.appmusicgrupo1.data.repository.remote.RemoteSongDataSource
import com.example.appmusicgrupo1.databinding.ActivityMusicListBinding
import com.example.appmusicgrupo1.ui.favorites.FavoriteListActivity
import com.example.appmusicgrupo1.ui.login.LoginActivity
import com.example.appmusicgrupo1.utils.Resource


class SongListActivity : ComponentActivity() {

    private lateinit var songAdapter: SongAdapter
    private val songRepository = RemoteSongDataSource();
    private val favoriteRepository = RemoteFavoriteDataSource();
    private var esTitulo : Boolean = true

    private val viewModel: SongViewModel by viewModels { SongViewModelFactory(songRepository, favoriteRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMusicListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songAdapter = SongAdapter(::onYoutubeClickList, viewModel::onFavoriteClickList)
        binding.songView.adapter = songAdapter

        binding.btnAutor.setOnClickListener{
            esTitulo = false
            songAdapter.filter(binding.search.text.toString(), esTitulo)
        }


        binding.byTitle.setOnClickListener{
            esTitulo = true
            songAdapter.filter(binding.search.text.toString(), esTitulo)
        }

        binding.search.addTextChangedListener(){
            songAdapter.filter(binding.search.text.toString(), esTitulo)
        }

        binding.imageView6.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



        viewModel.items.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if  (!it.data.isNullOrEmpty()) {
                        val data = it.data
//                         songAdapter.submitList(it.data)
                        songAdapter.submitList(it.data)
                        Log.i("Prueba", "Ha ocurrido un cambio en la lista de canciones")
                        songAdapter.submitSongList(data)
                        songAdapter.filter(binding.search.text.toString(), esTitulo)
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
                     viewModel.updateSongList()
                     viewModel.getFavorites()
                    songAdapter.filter(binding.search.text.toString(), esTitulo)
                    FavoriteClickList()
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

    private fun FavoriteClickList(){
        Log.i("PruebaList", "Favorito")
        val intent = Intent(this, SongListActivity::class.java)
        startActivity(intent)
        finish()
    }



}



