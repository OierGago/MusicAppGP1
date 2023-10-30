package com.example.appmusicgrupo1.ui.songList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicgrupo1.R
import com.example.appmusicgrupo1.data.repository.remote.RemoteSongDataSource
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appmusicgrupo1.databinding.ActivityMusicListBinding
import com.example.appmusicgrupo1.utils.Resource

class SongListActivity : AppCompatActivity() {

    private lateinit var songAdapter: SongAdapter
    private val songRepository = RemoteSongDataSource()

    private val viewModel: SongViewModel by viewModels { SongViewModelFactory(
        songRepository
    )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMusicListBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_music_list)

        songAdapter = SongAdapter()
        binding.songView.adapter = songAdapter

        viewModel.items.observe(this, Observer {
            Log.i("Prueba", "Ha ocurrido un cambio en la lista de canciones")
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if(!it.data.isNullOrEmpty()) {
                        songAdapter.submitList(it.data)
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {

                }
            }
        })

//        binding.btnFavorito.setOnclickListener() {
//            val intent = Intent(this, FavoriteActivity::class.java)
//            finish()
//            startActivity(intent)
//        }


    }
}