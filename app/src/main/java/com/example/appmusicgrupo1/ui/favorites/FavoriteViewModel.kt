package com.example.appmusicgrupo1.ui.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicgrupo1.data.repository.SongRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.appmusicgrupo1.MyApp
import com.example.appmusicgrupo1.data.Favorite
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.FavoriteRepository
import com.example.appmusicgrupo1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModelFactory(
    private val favoriteRepository: FavoriteRepository

):ViewModelProvider.Factory{
    override fun <T :  ViewModel> create(modelClass: Class<T>, extras : CreationExtras): T{
        return FavoriteViewModel(favoriteRepository) as T
    }
}

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel(){
    private val _items = MutableLiveData<Resource<List<Song>>>()

    val items : LiveData<Resource<List<Song>>> get() = _items

    private val _created = MutableLiveData<Resource<Integer>>();

    val created : LiveData<Resource<Integer>> get() = _created;

    init {
        updateSongList()
    }

    fun updateSongList(){
        viewModelScope.launch {
            val repoResponse = getSongFromRepository();
            _items.value = repoResponse
            Log.i("pruebas", _items.value.toString())
        }
    }

    suspend fun getSongFromRepository() : Resource<List<Song>>?{
        return withContext(Dispatchers.IO){
            favoriteRepository.getFavorites()
        }
    }

    fun onFavoriteClickList(song: Song) : Boolean{
        viewModelScope.launch {

                _created.value =deleteFromFavorite(song.id)

                Log.i("PRueba", "Cancion " + song.id + " borrada")

        }
        return song.favorito
    }

    suspend fun getFavoriteList(): Resource<List<Song>> {
        return withContext(Dispatchers.IO){
            favoriteRepository.getFavorites()
        }
    }


    suspend fun deleteFromFavorite(idSong : Int) : Resource<Integer> {
        return withContext(Dispatchers.IO){
            favoriteRepository.deleteFromFavorite(idSong)
        }
    }


}
