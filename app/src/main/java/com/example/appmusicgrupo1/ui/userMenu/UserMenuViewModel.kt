package com.example.appmusicgrupo1.ui.userMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.appmusicgrupo1.data.PassChange
import com.example.appmusicgrupo1.data.Song
import com.example.appmusicgrupo1.data.repository.UserRepository
import com.example.appmusicgrupo1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserMenuViewModelFactory(
    private val userRepository: UserRepository,
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras : CreationExtras): T{
        return UserMenuViewModel(userRepository) as T
    }
}

class UserMenuViewModel(
    private val userRepository: UserRepository
) : ViewModel(){

    fun changeUserPass(passChange: PassChange) {
        viewModelScope.launch {
            changePass(passChange)
        }
    }
    suspend fun changePass(passChange: PassChange): Resource<Int> {
        return withContext(Dispatchers.IO){
            userRepository.changePass(passChange)
        }
    }
}
