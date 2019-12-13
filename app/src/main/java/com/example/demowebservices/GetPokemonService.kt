package com.example.demowebservices

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GetPokemonService {
    @GET("api/v2/pokemon/{pokemon}")
    fun buscarPokemon(@Path("pokemon")nomePokemon: String) :
            Call<Pokemon>
}