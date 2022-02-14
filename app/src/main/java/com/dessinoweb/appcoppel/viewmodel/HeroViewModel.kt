package com.dessinoweb.appcoppel.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dessinoweb.appcoppel.api.Api
import com.dessinoweb.appcoppel.model.CharacterResponse
import com.dessinoweb.appcoppel.model.Data
import com.dessinoweb.appcoppel.service.ServiceMarvel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroViewModel: ViewModel()  {

    lateinit var api: Api
    lateinit var serviceMarvel: ServiceMarvel

    val heroDetail= MutableLiveData<CharacterResponse<Data>>()
    val errorMessage = MutableLiveData<String>()

    fun getDetailHero(id:Int){
        api=Api()
        serviceMarvel=api.createService(ServiceMarvel::class.java)!!

        val call=serviceMarvel.getHero(id)

        val callHero= object: Callback<CharacterResponse<Data>> {
            override fun onResponse(
                call: Call<CharacterResponse<Data>>,
                response: Response<CharacterResponse<Data>>
            ) {
                response.isSuccessful.let{
                    Log.d("SUCCESS_HERO","{${response.message()}}")
                    heroDetail.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<CharacterResponse<Data>>, t: Throwable) {
                Log.d("ERROR_HEROE", "{${t?.message}}")

            }
        }
        call.enqueue(callHero)
    }
}