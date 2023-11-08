package com.example.appmusicgrupo1.ui.songList

import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicgrupo1.R
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.databinding.ItemSongBinding
import com.squareup.picasso.Picasso

class SongAdapter (
    private val onYoutubeClickList: (Song) -> Unit,
    private val onFavoriteClickList: (Song) -> Unit,
): ListAdapter<Song, SongAdapter.SongViewHolder>(SongDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song)
        holder.itemView.findViewById<ImageView>(R.id.youtube).setOnClickListener {
            onYoutubeClickList(song)
        }
        holder.itemView.findViewById<ImageView>(R.id.songFavorite).setOnClickListener {
            onFavoriteClickList(song)

        }
    }

    inner class SongViewHolder(private val binding: ItemSongBinding) :  RecyclerView.ViewHolder(binding.root) {
                fun bind(song: Song){
                    binding.songTitle.text = song.titulo
                    binding.songAuthor.text = song.autor
                    val thumbnailUrl = song.imagen
                    Picasso
                        .get()
                        .load(thumbnailUrl)
                        .into(binding.songImage)
                    Log.i("PruebaFav", song.toString())
                    if(song.favorito) {
                        binding.songFavorite.setImageResource(R.drawable.heart_coraz_n_svg);
                    } else {
                        binding.songFavorite.setImageResource(R.drawable.nofavorite);
                    }
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


