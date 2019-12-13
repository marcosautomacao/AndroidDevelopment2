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
import android.R.attr.data
import android.provider.MediaStore
import android.graphics.Bitmap
import android.provider.MediaStore.*
import android.provider.MediaStore.Images.*
import android.provider.MediaStore.Images.Media.*
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
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()



        val gitHubService = retrofit.create(GitHubService::class.java)
        gitHubService.buscarUsuario(etUsuario.text.toString())
            .enqueue(object : Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@MainActivity,
                        t.message,
                        Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if(response.isSuccessful) {
                        val usuario = response.body()
                        val imageUri = usuario?.imagem
                        Picasso.get().load(imageUri).into(idUsuario);
                        tvUsuario.text = usuario?.nome
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
