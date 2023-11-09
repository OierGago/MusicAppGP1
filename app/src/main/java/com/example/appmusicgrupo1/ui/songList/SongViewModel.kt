package com.example.appmusicgrupo1.ui.songList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicgrupo1.data.repository.SongRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.appmusicgrupo1.R
import com.example.appmusicgrupo1.data.Favorite
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.FavoriteRepository
import com.example.appmusicgrupo1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongViewModelFactory(
    private val songRepository: SongRepository,
    private val favoriteRespository: FavoriteRepository
):ViewModelProvider.Factory{
    override fun <T :  ViewModel> create(modelClass: Class<T>, extras : CreationExtras): T{
        return SongViewModel(songRepository, favoriteRespository) as T
    }
}

class SongViewModel(
    private val songRepository: SongRepository,
    private val favoriteRespository: FavoriteRepository
) : ViewModel(){
    private val _items = MutableLiveData<Resource<List<Song>>>()

    val items : LiveData<Resource<List<Song>>> get() = _items

    private val _itemsFav = MutableLiveData<Resource<List<Song>>>()

    val itemsFav : LiveData<Resource<List<Song>>> get() = _itemsFav

    private val _created = MutableLiveData<Resource<Integer>>();

    val created : LiveData<Resource<Integer>> get() = _created;

    init {
        updateSongList()
        getFavorites()
    }

    fun obtenerFavoritos() {
        if (_items.value != null && _itemsFav.value != null) {
            val nuevaLista = arrayListOf<Song>()
            for (itemsList in _items.value!!.data!!){
                nuevaLista.add(itemsList)
            }
            Log.i("PruebaArray", nuevaLista.toString())
            for (itemFav in _itemsFav.value!!.data!!) {
                itemFav.favorito = true
                for (itemSong in _items.value!!.data!!) {
                    if (itemFav.id == itemSong.id) {
//                        Log.i("Prueba", "va2")
                        itemSong.favorito = true
                    }
                }
            }
        }

    }
    fun getFavorites() {
        viewModelScope.launch {
            val repoResponseFav = getFavoriteList(2)
            _itemsFav.value = repoResponseFav

            obtenerFavoritos()
        }
    }

    fun updateSongList() {
        viewModelScope.launch {
            val repoResponse = getSongFromRepository();
            _items.value = repoResponse

            obtenerFavoritos()
        }
    }
    fun onFavoriteClickList(song: Song) {
        viewModelScope.launch {
            if (song.favorito) {
                _created.value = deleteFromFavorite(2, song.id)
                song.favorito = false
                Log.i("PRueba", "Cancion " + song.id + " borrada")
            } else {
                var favorite = Favorite(2, song.id)
                _created.value = addToFavorite(favorite)
                song.favorito = true
                Log.i("PRueba", "Cancion " + song.id + " añadida")
            }
        }
    }

    suspend fun getFavoriteList(id: Int): Resource<List<Song>> {
        return withContext(Dispatchers.IO){
            favoriteRespository.getFavorites(id)
        }
    }

    suspend fun getSongFromRepository() : Resource<List<Song>>{
        return withContext(Dispatchers.IO){
            songRepository.getSongs()

        }
    }

    suspend fun deleteFromFavorite(idUser: Int, idSong : Int) : Resource<Integer> {
        return withContext(Dispatchers.IO){
            favoriteRespository.deleteFromFavorite(idUser, idSong)
        }
    }

    suspend fun addToFavorite(favorite: Favorite) : Resource<Integer>{
        return withContext(Dispatchers.IO){
            favoriteRespository.addFavorite(favorite)
        }
    }

}