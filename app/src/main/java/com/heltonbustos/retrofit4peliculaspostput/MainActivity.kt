package com.heltonbustos.retrofit4peliculaspostput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.heltonbustos.retrofit3peliculas.retrofit.Pelicula
import com.heltonbustos.retrofit3peliculas.retrofit.PeliculaAPIService
import com.heltonbustos.retrofit3peliculas.retrofit.PeliculaItem
import com.heltonbustos.retrofit3peliculas.retrofit.RestEngine
import com.heltonbustos.retrofit4peliculaspostput.databinding.ActivityMainBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnObtener.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            obtenerRegistros()
        }

        binding.btnGuardar.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                val x: Int = async {
                    cantidadRegistros()
                }.await() //espera a carga de imágenes

                guardarRegistro(x, PeliculaItem(
                    binding.txtAvaluo.text.toString(),
                    binding.txtFechaLanzamiento.text.toString(),
                    binding.txtLugarEstreno.text.toString(),
                    binding.txtNombre.text.toString()))
            }
        }
    }

    private fun guardarRegistro(id:Int, peliculaItem: PeliculaItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada: PeliculaAPIService =
                RestEngine.getRestEngine().create(PeliculaAPIService::class.java)
            val resultado: Call<PeliculaItem> = llamada.agregarPelicula(id, peliculaItem)
            val p:PeliculaItem? = resultado.execute().body()

            if (p != null){
                runOnUiThread {
                    binding.txtId.text = "Elemento agregado: \n ${Gson().toJson(p)}"
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun cantidadRegistros(): Int{
        val llamada: PeliculaAPIService =
            RestEngine.getRestEngine().create(PeliculaAPIService::class.java)
        val resultado: Call<Pelicula> = llamada.obtenerPeliculas("bd.json")
        val p:Pelicula? = resultado.execute().body()
        return p!!.size
    }

    private fun obtenerRegistros() {
        CoroutineScope(Dispatchers.IO).launch {
            val llamada: PeliculaAPIService =
                RestEngine.getRestEngine().create(PeliculaAPIService::class.java)
            val resultado: Call<Pelicula> = llamada.obtenerPeliculas("bd.json")
            val p:Pelicula? = resultado.execute().body()

            if(p != null){
                runOnUiThread {
                    binding.txtRegistros.text = "Películas encontradas: \n\n"
                    for(i in p){
                        binding.txtRegistros.append(i.nombre + "\n")
                    }

                    /*
                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .resize(600, 600)
                        .into(binding.imageView)
                     */

                    /*
                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .resize(600, 600)
                        .centerInside()
                        .into(binding.imageView)
                     */

                    /*
                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .resize(600, 600)
                        .centerInside()
                        .into(binding.imageView)
                     */

                    /*
                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .rotate(180f)
                        .into(binding.imageView, object : Callback{
                            override fun onSuccess() {
                                binding.progressBar.visibility = View.GONE
                            }
                            override fun onError() {
                                binding.progressBar.visibility = View.GONE
                            }

                        })
                     */


                    Picasso.with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .rotate(180f)
                        //.placeholder(R.drawable.cargando)
                        .into(binding.imageView, object : Callback{
                            override fun onSuccess() {
                                binding.progressBar.visibility = View.GONE
                            }
                            override fun onError() {
                                binding.progressBar.visibility = View.GONE
                            }

                        })



                    /*
                    Glide
                        .with(applicationContext)
                        .load("https://i.imgur.com/ytrkU2H.jpeg")
                        .placeholder(R.drawable.cargando)
                        .into(binding.imageView);
                    binding.progressBar.visibility = View.GONE
                     */

                }
            }
        }
    }
}