package com.dessinoweb.appcoppel.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

const val HASH = "hash"
const val APIKEY = "apikey"
const val TS = "ts"
const val TS_VALUE = "1000"
const val  PRIVATE_API_KEY_VALUE="e9550ea6c4ff539300d5400a388736ce2caf68ea";
const val PUBLIC_API_KEY_VALUE="f10ab2e1b63b3281162e59171ff5ed0f"
const val MARVEL_BASE_URL="https://gateway.marvel.com/public/"


class Api {
    var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
        val defaultRequest = chain.request()
        val hashSignature = "$TS_VALUE${PRIVATE_API_KEY_VALUE}${PUBLIC_API_KEY_VALUE}".md5()
        val defaultHttpUrl = defaultRequest.url
        val httpUrl = defaultHttpUrl.newBuilder()
            .addQueryParameter(TS, TS_VALUE)
            .addQueryParameter(APIKEY, PUBLIC_API_KEY_VALUE)
            .addQueryParameter(HASH, hashSignature)
            .build()

        val requestBuilder = defaultRequest.newBuilder().url(httpUrl)

        chain.proceed(requestBuilder.build())
    }

    private val builder = Retrofit.Builder()
        .baseUrl(MARVEL_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()

        return retrofit.create(serviceClass)
    }
}