package com.example.appmusicgrupo1.ui.songList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.databinding.ItemSongBinding
import com.squareup.picasso.Picasso

class SongAdapter (): ListAdapter<Song, SongAdapter.SongViewHolder>(SongDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song)
    }

        inner class SongViewHolder(private val binding: ItemSongBinding) :  RecyclerView.ViewHolder(binding.root) {
                fun bind(song: Song){
                    binding.songTitle.text = song.titulo
                    binding.songAuthor.text = song.autor
                    val thumbnailUrl = song.imagen
                    Log.i("Prueba", "" + thumbnailUrl)
                    Picasso
                        .get()
                        .load(thumbnailUrl)
                        .into(binding.songImage)
                }
        }
    class SongDiffCallback: DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.titulo == newItem.titulo
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }
}


