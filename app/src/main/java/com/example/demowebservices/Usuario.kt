package com.example.demowebservices

import com.google.gson.annotations.SerializedName

data class Usuario (
    @SerializedName("name") val nome : String,
    @SerializedName("avatar_url") val imagem : String
)