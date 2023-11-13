package com.example.appmusicgrupo1.ui.regitro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.appmusicgrupo1.data.repository.AuthenticationRepository
import com.example.appmusicgrupo1.data.repository.UserRequest
import com.example.appmusicgrupo1.ui.login.LoginViewModel
import com.example.appmusicgrupo1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class RegisterViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    private val _register = MutableLiveData<Resource<Integer>>()
    val register : LiveData<Resource<Integer>> get() = _register

    fun onRegisterClick(login : String,nombre:String,apellido:String,mail:String,contrasenya:String) {
        val userRequest = UserRequest(login,nombre,apellido,mail,contrasenya)
        Log.e("Pruebla", "Datos del usuario: login= ${userRequest.login} nombre= ${userRequest.nombre} apellido= ${userRequest.apellido} mail= ${userRequest.mail}");
        viewModelScope.launch {
            _register.value = signIn(userRequest)
        }
    }

     private suspend fun signIn(userRequest: UserRequest) : Resource<Integer>{
         return withContext(Dispatchers.IO) {
             authenticationRepository.signIn(userRequest)
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