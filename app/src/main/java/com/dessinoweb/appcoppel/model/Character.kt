package com.dessinoweb.appcoppel.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    @Expose
    var id:Int?=null,
    @SerializedName("name")
    @Expose
    var name:String?=null,
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: Thumbnail?=null,
    @SerializedName("description")
    @Expose
    var description:String?=null,
)
