package com.example.appmusicgrupo1.ui.userMenu

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Toast
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

            if(binding.passwordInput.text.toString() == binding.repeatPasswordInput.text.toString() &&
                !(binding.passwordInput.text.toString().isNullOrEmpty()) &&
                binding.passwordInput.text.toString().length>=5 &&
                binding.oldPasswordInput.text.toString() == MyApp.userPreferences.fetchAuthPassword().toString()
                ){
                var user : PassChange = PassChange(
                    binding.oldPasswordInput.text.toString(),
                    binding.passwordInput.text.toString()
                )

                if (user != null) {
                    viewModel.changeUserPass(user)
                }
                val intent = Intent(this, SongListActivity::class.java)
                startActivity(intent)
                finish()
            } else if(binding.oldPasswordInput.text.toString() != MyApp.userPreferences.fetchAuthPassword().toString()){
                Toast.makeText(this, "Contraseña vieja incorrecta", Toast.LENGTH_SHORT).show()
            } else if(binding.passwordInput.text.toString().length<5){
                Toast.makeText(this, "Las contraseñas deben tener una longitud de 5 o más caracteres ", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "Las contraseñas deben estar iguales", Toast.LENGTH_SHORT).show()
            }

        }

        binding.imageView.setOnClickListener(){
            val intent = Intent(this, SongListActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}