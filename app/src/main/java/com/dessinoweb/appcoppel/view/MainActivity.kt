package com.dessinoweb.appcoppel.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.dessinoweb.appcoppel.R
import com.dessinoweb.appcoppel.adapter.MainAdapter
import com.dessinoweb.appcoppel.databinding.ActivityMainBinding
import com.dessinoweb.appcoppel.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel
     var cont:Int = 0

    val adapter=MainAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.recyclerview.adapter = adapter


        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    Log.d("SCROLL_A","ABAJO${recyclerView.adapter?.itemCount}")
                    cont= recyclerView.adapter?.itemCount!! +10

                    lifecycleScope.launch{
                        withContext(Dispatchers.IO){
                            viewModel.getCharacters(cont)
                        }
                    }

                } else if (!recyclerView.canScrollVertically(-1) && dy < 0) {
                    Log.d("SCROLL_B","ARRIBA")
                }
            }
        })

        viewModel.charactersList.observe(this, Observer {
            it.data?.let { it1 -> adapter.setCharacterList(it1.results) }
        })

        viewModel.errorMessage.observe(this, Observer{
            Log.d("ERROR_OBSERVE","On Observe $it")
        })

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                viewModel.getCharacters(20)
            }
        }


        //serviceMarvel=api.createService(ServiceMarvel::class.java)

    }


}