package com.dessinoweb.appcoppel.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dessinoweb.appcoppel.databinding.AdapterCharactersBinding

import com.dessinoweb.appcoppel.model.Character
import com.dessinoweb.appcoppel.view.HeroActivity


//class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>()  {
class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {

    var characters= mutableListOf<List<Character>>()

    var total=0

    fun setCharacterList(characters: List<Character>) {
        this.characters = mutableListOf(characters)
        total=characters.size
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCharactersBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)

    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Log.d("SIZERED","${characters.get(0).size}")

        holder.binding.id.text= characters.get(0).get(position).id.toString()
        holder.binding.names.text= characters.get(0).get(position).name

        Glide.with(holder.itemView.context).load(
            characters.get(0)?.get(position).thumbnail?.path+"."+characters.get(0)?.get(position).thumbnail?.extension
        ).into(holder.binding.imageview)

    }
    override fun getItemCount(): Int {
        return total
    }

}

class MainViewHolder(val binding: AdapterCharactersBinding):RecyclerView.ViewHolder(binding.root) {
    init{
        itemView.setOnClickListener{
            Log.d("CLICK_DATA","este")


            val activity = itemView.context as Activity
            val intent = Intent(activity, HeroActivity::class.java)

            //intent.putExtra("id", binding.id.toString())
            val bundle = Bundle()
            Log.d("PARA_ID","${binding.id.text.toString()}")

            bundle.putString("id",binding.id.text.toString())
            intent.putExtras(bundle)

            itemView.context.startActivity(intent)
           // startActivity(itemView.context,intent,bundle)

        }

    }
}


