package com.dessinoweb.appcoppel.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dessinoweb.appcoppel.R
import com.dessinoweb.appcoppel.databinding.ActivityHeroBinding
import com.dessinoweb.appcoppel.databinding.ActivityMainBinding
import com.dessinoweb.appcoppel.model.Character
import com.dessinoweb.appcoppel.viewmodel.HeroViewModel
import com.dessinoweb.appcoppel.viewmodel.MainViewModel

class HeroActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityHeroBinding
    lateinit var viewModel: HeroViewModel

    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero)
        binding = ActivityHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        if (extras == null) {
            id=null
        } else {
            id = extras.getString("id")
        }

        binding.name.text
        binding.description.text
        binding.imageview


        Log.d("PARA_ID","${id}")

        viewModel = ViewModelProvider(this).get(HeroViewModel::class.java)

        viewModel.heroDetail.observe(this,{
            it.data?.let { it1 -> setDetailHero(it1.results) }
        })

        viewModel.getDetailHero(id!!.toInt())

    }
    fun setDetailHero(hero:List<Character>){
        Glide.with(this).load(
            hero.get(0)?.thumbnail?.path+"."+hero.get(0)?.thumbnail?.extension
        ).into(binding.imageview)


        binding.name.text=hero.get(0).name
        binding.description.text=hero.get(0).description
    }
}
