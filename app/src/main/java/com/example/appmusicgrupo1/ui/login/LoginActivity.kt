package com.example.appmusicgrupo1.ui.login


import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
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

    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory(
        authenticationRepository
    ) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // cargamos el XML en la actividad
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // el listener del boton
        binding.Login.setOnClickListener() {

            viewModel.onLoginClick(
                binding.Username.text.toString(),
                binding.Password.text.toString()
            )
        }
        binding.Register.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java).apply {
                // putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
            // si queremos quitar esta actividad...
            finish()
        }

        // el cambio en login del VM cuando el server nos devuelva su respuesta
        viewModel.login.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { data ->
                        MyApp.userPreferences.saveAuthToken(data.accessToken,data.id,data.login)
                        // MyApp.userPreferences.saveAuthId(MyApp.userPreferences.getUserId(data.accessToken))
                        // TODO podriamos guardar el nombre del usuario tambien e incluso la pass en el sharedPreferences... hacer sus funciones...
                        // TODO recordad que no esta cifrado esto es solo a modo prueba. Tampoco se recomienda guardar contraseñas...
                        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
                        // TODO hacer lo que sea necesario en este caso cambiamos de actividad
                        val intent = Intent(this, SongListActivity::class.java).apply {
                           // putExtra(EXTRA_MESSAGE, message)
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