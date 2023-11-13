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
import java.util.Locale

class SongAdapter (
    private val onYoutubeClickList: (Song) -> Unit,
    private val onFavoriteClickList: (Song) -> Unit,
): ListAdapter<Song, SongAdapter.SongViewHolder>(SongDiffCallback()){

    private var songListFull: List<Song> = emptyList()
    private var songListFiltered: List<Song> = emptyList()



    // Método para establecer la lista completa de canciones y actualizar la lista filtrada
    fun submitSongList(songs: List<Song>) {
        songListFull = songs
        filter("",  true) // Al recibir una nueva lista de canciones, mostramos todas las canciones
    }

    // Método para filtrar la lista de canciones
    fun filter(text: String, esTitulo : Boolean) {
        val searchText = text.trim().lowercase(Locale.getDefault())

        songListFiltered = if (searchText.isEmpty()) {
            songListFull // Restauramos la lista completa si el texto está vacío
        } else {
            songListFull.filter {
                if(esTitulo){
                    it.titulo.lowercase(Locale.getDefault()).contains(searchText)
                } else {
                    it.autor.lowercase(Locale.getDefault()).contains(searchText)
                }
                // Puedes agregar otros campos aquí para el filtrado
            }
        }

        submitList(songListFiltered) // Mostramos los resultados filtrados en el RecyclerView
    }
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
                    if(song.favorito) {
                        Log.i("PruebaCambio", "Cambiado a favorito")
                        cambioFavorito()
                    } else {
                        Log.i("PruebaCambio", "Cambiado a no favorito")
                        cambioNoFavorito()
                        //binding.songFavorite.setImageResource(R.drawable.nofavorite);

                    }
                }
        fun cambioFavorito(){
            binding.songFavorite.setImageResource(R.drawable.heart_coraz_n_svg);
        }
        fun cambioNoFavorito(){
            binding.songFavorite.setImageResource(R.drawable.nofavorite);
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


