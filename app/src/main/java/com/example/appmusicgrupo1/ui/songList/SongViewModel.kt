package com.example.appmusicgrupo1.ui.songList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicgrupo1.data.repository.SongRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
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
    }

    fun updateSongList() {
        viewModelScope.launch {
            val listOfSongs = _items.value?.data
            val repoResponse = getSongFromRepository();
            _items.value = repoResponse
            val repoResponseFav = getFavoriteList(2)
            _itemsFav.value = repoResponseFav
            for (itemFav in _itemsFav.value!!.data!!) {
                itemFav.favorito = true
                if (listOfSongs != null) {
                    for (itemSong in listOfSongs) {
                        if (itemFav.id == itemSong.id) {
                            if (itemSong != null) {
                                itemSong.favorito = true
                                Log.i("PruebaLista", itemSong.toString())
                            }
                        }
                    }
                }
            }

        }
    }

//    private fun favoriteList(id : Int){
//        viewModelScope.launch {
//            val repoResponseFav = getFavoriteList(id)
//            _itemsFav.value = repoResponseFav

//            for (item in _itemsFav.value!!.data!!){
//                for(item in _items.value!!.data!!){
//                    if(){
//                        val listOfSongs = _items.value?.data
//                        val song = listOfSongs?.get(0)
//                        if (song != null) {
//                            song.favorito = true
//                        }
//                    }
//                }
//            }

//            for (itemFav in _itemsFav.value!!.data!!){
//                itemFav.favorito = true
//                Log.i("PruebaSongFor", itemFav.toString())
////
//            }
//            Log.i("PruebaSongForNoFav", _items.value.toString())
//            for(itemSong in _items.value!!.data!!){
//                    Log.i("PruebaSongForNoFav", itemSong.toString())
            //                    if(itemFav.favorito != itemSong.favorito){
//                        itemSong.favorito=true
//                        Log.i("SongFav", itemSong.toString())
//                    }
//                 }
//        }
//    }

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

}