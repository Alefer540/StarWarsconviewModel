package com.example.starwarsconviewmodel

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsconviewmodel.databinding.ItemPlanetaBinding

class TextoAdapter(var listaPlaneta:List<Result>): RecyclerView.Adapter <TextoAdapter.TextoViewHolder>()  {

    class TextoViewHolder(var itemPlanetaBinding: ItemPlanetaBinding ) : RecyclerView.ViewHolder(itemPlanetaBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding = ItemPlanetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {
        holder.itemPlanetaBinding.textView1.text = listaPlaneta[position].name

        holder.itemPlanetaBinding.textView1.setOnClickListener() {

            val intent = Intent (holder.itemPlanetaBinding.root.context, SecondActivity::class.java)
            intent.putExtra("url",listaPlaneta[position].url)
            holder.itemPlanetaBinding.root.context.startActivity(intent)
        }
        //holder.itemUsuarioBinding.textView1.text=listaUsuarios[position].gender
    }
    override fun getItemCount(): Int {

        return listaPlaneta.size
    }

}

