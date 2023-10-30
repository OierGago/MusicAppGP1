package com.example.appmusicgrupo1.ui.songList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicgrupo1.data.repository.SongRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongViewModelFactory(
    private val songRepository: SongRepository
):ViewModelProvider.Factory{
    override fun <T :  ViewModel> create(modelClass: Class<T>, extras : CreationExtras): T{
        return SongViewModel(songRepository) as T
    }
}

class SongViewModel(
    private val songRepository: SongRepository
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
        }
    }

    suspend fun getSongFromRepository() : Resource<List<Song>>{
        return withContext(Dispatchers.IO){
            songRepository.getSongs()
        }
    }

}