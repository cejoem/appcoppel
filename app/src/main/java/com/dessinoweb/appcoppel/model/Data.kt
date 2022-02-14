package com.dessinoweb.appcoppel.model

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("results")
    val results:List<Character>,
    val offset: Int,
    val total: Int
)
