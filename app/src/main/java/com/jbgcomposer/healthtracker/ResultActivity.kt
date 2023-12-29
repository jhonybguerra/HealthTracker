package com.jbgcomposer.healthtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jbgcomposer.healthtracker.databinding.ActivityResultBinding
import com.jbgcomposer.healthtracker.databinding.ItemResultBinding
import com.jbgcomposer.healthtracker.model.Calc
import java.text.SimpleDateFormat
import java.util.*

class ResultActivity : AppCompatActivity(), OnListClickListener {

    private lateinit var adapter: ResultAdapter
    private lateinit var resultList: MutableList<Calc>

    private val binding: ActivityResultBinding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        resultList = mutableListOf<Calc>()
        adapter = ResultAdapter(resultList, this)

        binding.rvResult.layoutManager = LinearLayoutManager(this)
        binding.rvResult.adapter = adapter

        val typeReceivedFromIntent =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(typeReceivedFromIntent)
            val lastResult = response.last().res

            runOnUiThread {
                binding.tvResult.text = getString(R.string.last_result_response, lastResult)
                resultList.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()
    }

    override fun onClick(id: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("update", id)
        startActivity(intent)
        finish()
    }

    override fun onLongClick(position: Int, calc: Calc) {
        AlertDialog.Builder(this)
            .setMessage(R.string.delete_message)
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .setPositiveButton(android.R.string.ok) { _, _ ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()

                    val response = dao.delete(calc)

                    if (response > 0) {
                        runOnUiThread {
                            resultList.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        }
                    }
                }.start()
            }
            .create()
            .show()

    }

    private inner class ResultAdapter(
        private val listResultItems: List<Calc>,
        private val listener: OnListClickListener
    ) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemResultBinding.inflate(inflater, parent, false)
            return ResultViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return listResultItems.size
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            val currentItem = listResultItems[position]
            holder.bind(currentItem)
        }

        inner class ResultViewHolder(
            val binding: ItemResultBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Calc) {

                val response = item.res

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                val date = sdf.format(item.createdDate)

                binding.apply {
                    tvListResult.text = getString(R.string.list_response, response, date)
                    tvListResult.setOnClickListener {
                        listener.onClick(item.id)
                    }
                    tvListResult.setOnLongClickListener {
                        listener.onLongClick(adapterPosition, item)
                        true
                    }
                }
            }
        }
    }
}