package com.example.demowebservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btPesquisar.setOnClickListener{pesquisar()}
    }

    private fun pesquisar() {
        val okhttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()



        val GetPokemonService = retrofit.create(GetPokemonService::class.java)
        GetPokemonService.buscarPokemon(etPokemon.text.toString())
            .enqueue(object : Callback<Pokemon> {
                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Toast.makeText(this@MainActivity,
                        t.message,
                        Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if(response.isSuccessful) {
                        val Pokemon = response.body()
                        Picasso.get().load(Pokemon?.sprites?.frontDefault).into(idPokemon);
                        tvPokemon.text = Pokemon?.name
                    } else {
                        Toast.makeText(this@MainActivity,
                            "Deu ruim",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }
            })
    }
}
