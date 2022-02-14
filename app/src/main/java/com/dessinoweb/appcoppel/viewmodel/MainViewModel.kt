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

class MainViewModel:ViewModel() {
    lateinit var api:Api
    lateinit var serviceMarvel:ServiceMarvel


    val charactersList=MutableLiveData<CharacterResponse<Data>>()
    val errorMessage = MutableLiveData<String>()

    fun getCharacters(limit:Int){
        api=Api()
        serviceMarvel=api.createService(ServiceMarvel::class.java)!!

        val call= serviceMarvel.getCharacters(limit)

        val callCharacters=object: Callback<CharacterResponse<Data>> {
            override fun onResponse(
                call: Call<CharacterResponse<Data>>,
                response: Response<CharacterResponse<Data>>
            ) {
                Log.d("SUCCESS_CHARACTERS", "{${response?.message()}} {${response.errorBody()}} {${response.code()}}")
                response.isSuccessful.let{
                    val characters=response?.body()

                    Log.d("CHARACTER_0","${characters?.data?.results?.get(0)?.name}")
                    charactersList.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<CharacterResponse<Data>>, t: Throwable) {
                Log.d("ERROR_CHARACTERS", "{${t?.message}}")
            }
        }
        call.enqueue(callCharacters)
    }
}