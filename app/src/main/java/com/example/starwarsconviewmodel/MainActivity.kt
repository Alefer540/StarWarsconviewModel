package com.example.starwarsconviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsconviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : TextoAdapter
    private val viewModel:MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.infoplanetas()


        initObsever()
    }

    fun initObsever() {
        viewModel.responsePlaneta.observe(this){planetaResponse ->

            // adapter= TextoAdapter(usuariosResponse.results.filter { it.gender.equals("female") })
            adapter= TextoAdapter(planetaResponse.results)
            binding.recyclerView.adapter=adapter

        }


    }

}