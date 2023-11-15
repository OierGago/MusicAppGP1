package com.example.appmusicgrupo1.ui.userMenu

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appmusicgrupo1.MyApp
import com.example.appmusicgrupo1.UserPreferences
import com.example.appmusicgrupo1.data.PassChange
import com.example.appmusicgrupo1.data.User
import com.example.appmusicgrupo1.data.repository.remote.RemoteAuthenticationRepository
import com.example.appmusicgrupo1.data.repository.remote.RemoteUserDataSource
import com.example.appmusicgrupo1.databinding.ActivityMainBinding
import com.example.appmusicgrupo1.databinding.ActivityUserMenuBinding
import com.example.appmusicgrupo1.ui.login.LoginActivity
import com.example.appmusicgrupo1.ui.login.LoginViewModel
import com.example.appmusicgrupo1.ui.main.LoginViewModelFactory
import com.example.appmusicgrupo1.ui.regitro.RegisterActivity
import com.example.appmusicgrupo1.ui.songList.SongListActivity

class UserMenuActivity : AppCompatActivity() {

    private val userSongRepository = RemoteUserDataSource();


    private val viewModel: UserMenuViewModel by viewModels {
        UserMenuViewModelFactory(
            userSongRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.userName.setText(MyApp.userPreferences.fetchAuthLogin())

        binding.changePassword.setOnClickListener(){

            if(binding.passwordInput.text.toString() == binding.repeatPasswordInput.text.toString()){
                Log.i("pruebacontraseña","4185")
                var user : PassChange = PassChange(
                    binding.oldPasswordInput.text.toString(),
                    binding.passwordInput.text.toString()
                )

                    Log.i("pruebacontraseña","cojones")

                if (user != null) {
                    Log.i("pruebacontraseña","coqwejones")
                    viewModel.changeUserPass(user)
                }
            }
        }

        binding.imageView.setOnClickListener(){
            val intent = Intent(this, SongListActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}