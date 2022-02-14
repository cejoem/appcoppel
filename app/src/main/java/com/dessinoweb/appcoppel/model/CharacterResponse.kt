package com.dessinoweb.appcoppel.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse<T>(
    val copyright:String,
    @SerializedName("data")
    val data: Data?=null
)
