package com.example.appmusicgrupo1.ui.songList

import android.os.Bundle
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicgrupo1.R

class SongListActivity : AppCompatActivity() {

    private lateinit var songAdapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_list)

    }
}