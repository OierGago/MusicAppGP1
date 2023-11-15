package com.example.appmusicgrupo1.ui.regitro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.appmusicgrupo1.data.repository.AuthenticationRepository
import com.example.appmusicgrupo1.data.UserRequest
import com.example.appmusicgrupo1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(  private val authenticationRepository: AuthenticationRepository
) : ViewModel() {


    private val _register = MutableLiveData<Resource<Int>>()
    val register : LiveData<Resource<Int>> get() = _register

    fun onRegisterClick(login:String,nombre:String, apellido:String, email: String, contrasenya: String) {
        val userRequest = UserRequest(login,nombre,apellido,email, contrasenya)
        viewModelScope.launch {
            _register.value = register(userRequest)
        }
    }

    private suspend fun register(userRequest: UserRequest): Resource<Int> {
        return withContext(Dispatchers.IO) {
            authenticationRepository.signup(userRequest)
        }
    }


}

class RegisterViewModelFactory(
    private val authenticationRepository: AuthenticationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return RegisterViewModel(authenticationRepository) as T
    }
}
