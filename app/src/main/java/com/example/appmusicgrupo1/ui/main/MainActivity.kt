package com.example.appmusicgrupo1.ui.main
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.appmusicgrupo1.R
import com.example.appmusicgrupo1.data.repository.remote.RemoteAuthenticationRepository
import com.example.appmusicgrupo1.databinding.ActivityLoginBinding
import com.example.appmusicgrupo1.databinding.ActivityMainBinding
import com.example.appmusicgrupo1.ui.login.LoginActivity
import com.example.appmusicgrupo1.ui.login.LoginViewModel
import com.example.appmusicgrupo1.ui.login.LoginViewModelFactory
import com.example.appmusicgrupo1.ui.regitro.RegisterActivity

class MainActivity : AppCompatActivity() {

    private val authenticationRepository = RemoteAuthenticationRepository();


    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            authenticationRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    binding.Login.setOnClickListener() {
        val intent = Intent(this, LoginActivity::class.java).apply {
        }
        startActivity(intent)
        finish()
    }
        binding.register.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java).apply {
            }
            startActivity(intent)
            finish()
        }


    }
}