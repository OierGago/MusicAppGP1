package com.example.appmusicgrupo1.ui.favorites


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.appmusicgrupo1.R
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.remote.RemoteFavoriteDataSource
import com.example.appmusicgrupo1.data.repository.remote.RemoteSongDataSource
import com.example.appmusicgrupo1.databinding.ActivityFavoriteListBinding
import com.example.appmusicgrupo1.databinding.ActivityMusicListBinding
import com.example.appmusicgrupo1.ui.songList.SongAdapter
import com.example.appmusicgrupo1.ui.songList.SongListActivity
import com.example.appmusicgrupo1.ui.songList.SongViewModel
import com.example.appmusicgrupo1.ui.songList.SongViewModelFactory
import com.example.appmusicgrupo1.utils.Resource

class FavoriteListActivity : ComponentActivity() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private val songRepository = RemoteSongDataSource();
    private val favoriteRepository = RemoteFavoriteDataSource();
    private var esTitulo : Boolean = true

    private val viewModel: FavoriteViewModel by viewModels { FavoriteViewModelFactory(
        favoriteRepository

    )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val binding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAdapter = FavoriteAdapter(::onYoutubeClickList, viewModel::onFavoriteClickList)
        binding.songView.adapter = favoriteAdapter

        binding.btnAutor.setOnClickListener{
            esTitulo = false
            favoriteAdapter.filter(binding.search.text.toString(), esTitulo)
        }

        binding.byTitle.setOnClickListener{
            esTitulo = true
            favoriteAdapter.filter(binding.search.text.toString(), esTitulo)
        }

        binding.search.addTextChangedListener(){
            favoriteAdapter.filter(binding.search.text.toString(), esTitulo)
        }


        viewModel.items.observe(this, Observer {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if  (!it.data.isNullOrEmpty()) {
                        Log.i("Prueba", "Ha ocurrido un cambio en la lista de cancwdiones")
                        favoriteAdapter.submitList(it.data)
                        favoriteAdapter.submitSongList(it.data)
                    } else if (it.data.isNullOrEmpty()){
                        favoriteAdapter.submitList(it.data)
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

        binding.btnCanciones.setOnClickListener() {
            val intent = Intent(this, SongListActivity::class.java)
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


