package com.example.appmusicgrupo1.ui.regitro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appmusicgrupo1.MyApp
import com.example.appmusicgrupo1.R
import com.example.appmusicgrupo1.data.repository.remote.RemoteAuthenticationRepository
import com.example.appmusicgrupo1.databinding.ActivityLoginBinding
import com.example.appmusicgrupo1.databinding.ActivityRegisterBinding
import com.example.appmusicgrupo1.ui.login.LoginActivity
import com.example.appmusicgrupo1.ui.login.LoginViewModel
import com.example.appmusicgrupo1.ui.login.LoginViewModelFactory
import com.example.appmusicgrupo1.ui.songList.SongListActivity
import com.example.appmusicgrupo1.utils.Resource


class RegisterActivity : ComponentActivity(){

    private val authenticationRepository = RemoteAuthenticationRepository();

    private val viewModel: RegisterViewModel by viewModels { RegisterViewModelFactory(
        authenticationRepository
    ) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // cargamos el XML en la actividad
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // el listener del boton
        binding.btnRegister.setOnClickListener() {
            if (binding.PasswordText.text.toString() == binding.RepeatPasswordText.text.toString()) {
                viewModel.onRegisterClick(
                    binding.UsernameText.text.toString(),
                    binding.NameText.text.toString(),
                    binding.lastnametext.text.toString(),
                    binding.mailtext.text.toString(),
                    binding.PasswordText.text.toString()
                )
            } else {
                Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show()
                binding.PasswordText.setText(" ")
                binding.RepeatPasswordText.setText(" ")
            }
        }
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                // putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
            // si queremos quitar esta actividad...
            finish()
        }

        // el cambio en login del VM cuando el server nos devuelva su respuesta
        viewModel.register.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let {
                        // TODO podriamos guardar el nombre del usuario tambien e incluso la pass en el sharedPreferences... hacer sus funciones...
                        // TODO recordad que no esta cifrado esto es solo a modo prueba. Tampoco se recomienda guardar contraseñas...
                        //Toast.makeText(this, "registrado", Toast.LENGTH_SHORT).show()
                        // TODO hacer lo que sea necesario en este caso cambiamos de actividad
                        val intent = Intent(this, LoginActivity::class.java).apply {
                        // putExtra(EXTRA_MESSAGE, message)
                            Log.e("Prueba","a")
                            var login = binding.UsernameText.text.toString()
                            var contrasenya = binding.PasswordText.text.toString()
                            putExtra("login", login)
                            putExtra("contrasenya", contrasenya)

                        }
                        startActivity(intent)
                        // si queremos quitar esta actividad...
                        finish()
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    // de momento
                }
            }
        })



    }

}