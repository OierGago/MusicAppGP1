package com.example.appmusicgrupo1.ui.login


import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appmusicgrupo1.MyApp
import com.example.appmusicgrupo1.data.repository.remote.RemoteAuthenticationRepository
import com.example.appmusicgrupo1.databinding.ActivityLoginBinding
import com.example.appmusicgrupo1.ui.regitro.RegisterActivity
import com.example.appmusicgrupo1.ui.songList.SongListActivity
import com.example.appmusicgrupo1.utils.Resource




    class LoginActivity : ComponentActivity() {

        private val authenticationRepository = RemoteAuthenticationRepository();

        private val viewModel: LoginViewModel by viewModels {
            LoginViewModelFactory(
                authenticationRepository
            )
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // cargamos el XML en la actividad
            val binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val extras = intent.extras
            if (extras != null) {
                val dato1 = extras.getString("login")
                val dato2 = extras.getString("contrasenya")
                binding.Username.setText(dato1)
                binding.Password.setText(dato2)
            }
            // funcion intent
              val intent = intent
              if (intent != null) {
                  // Verificar si el Intent tiene datos extras
                  val extras = intent.extras
                  if (extras != null) {
                      // Obtener los datos específicos que necesitas
                      val dato1 = extras.getString("login")
                      val dato2 = extras.getInt("contrasenya")
                      binding.Username.setText(dato1)
                      binding.Password.setText(dato2)
                  }
              }

            // el listener del boton
            binding.Login.setOnClickListener() {

                viewModel.onLoginClick(
                    binding.Username.text.toString(),
                    binding.Password.text.toString()
                )
            }
            binding.Register.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java).apply {
                }
                startActivity(intent)
                finish()
            }

            // el cambio en login del VM cuando el server nos devuelva su respuesta
            viewModel.login.observe(this, Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        it.data?.let { data ->
                            Log.e("Antes de guardar" , "antes de guardar")
                            MyApp.userPreferences.saveAuthToken(
                                data.id,
                                data.contrasenya,
                                data.login,
                                data.accessToken
                            )
                            Log.e("Despues de guardar", "Despues de guardar")

                            // TODO podriamos guardar el nombre del usuario tambien e incluso la pass en el sharedPreferences... hacer sus funciones...
                            // TODO recordad que no esta cifrado esto es solo a modo prueba. Tampoco se recomienda guardar contraseñas...
                            Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
                            // TODO hacer lo que sea necesario en este caso cambiamos de actividad

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
