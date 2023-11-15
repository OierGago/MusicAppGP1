package com.example.appmusicgrupo1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.appmusicgrupo1.data.AuthenticationResponse
import com.example.appmusicgrupo1.data.repository.AuthenticationRepository
import com.example.appmusicgrupo1.utils.Resource


class MainViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {


    private val _main = MutableLiveData<Resource<AuthenticationResponse>>()
    val main : LiveData<Resource<AuthenticationResponse>> get() = _main


}

class LoginViewModelFactory(
    private val authenticationRepository: AuthenticationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MainViewModel(authenticationRepository) as T
    }
}