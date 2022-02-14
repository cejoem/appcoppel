package com.dessinoweb.appcoppel.service

import com.dessinoweb.appcoppel.model.CharacterResponse
import com.dessinoweb.appcoppel.model.Data
import retrofit2.Call
import retrofit2.http.*

interface ServiceMarvel {
    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("limit") limit: Int?
    ): Call<CharacterResponse<Data>>



    @GET("/v1/public/characters/{id}")
    fun getHero(
        @Path("id") id: Int?
    ): Call<CharacterResponse<Data>>
}