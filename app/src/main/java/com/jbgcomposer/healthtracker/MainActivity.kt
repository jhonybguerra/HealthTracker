package com.jbgcomposer.healthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jbgcomposer.healthtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainItems: MutableList<MainItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainItems = createMainItems()
        binding.rvMainActivity.adapter = setupAdapter()

    }

    private fun setupAdapter(): MainAdapter {
        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> {
                Log.i("teste", "cliquei no $id")
                }
                2 -> {
                Log.i("teste", "cliquei no $id")
                }
                3 -> {
                Log.i("teste", "cliquei no $id")
                }
            }
        }
        return adapter
    }

    private fun createMainItems() : MutableList<MainItem> {
        val items = mutableListOf<MainItem>()
        items.add(
            MainItem(
                id = 1,
                drawableRes = R.drawable.ic_imc,
                textStringId = R.string.label_imc
            )
        )
        items.add(
            MainItem(
                id = 2,
                drawableRes = R.drawable.ic_tmb,
                textStringId = R.string.label_tmb
            )
        )
        items.add(
            MainItem(
                id = 3,
                drawableRes = R.drawable.ic_water,
                textStringId = R.string.label_water
            )
        )
        return items
    }
}