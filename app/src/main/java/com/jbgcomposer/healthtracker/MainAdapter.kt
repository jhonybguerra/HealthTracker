package com.jbgcomposer.healthtracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jbgcomposer.healthtracker.databinding.ItemMainBinding

class MainAdapter(
    private val mainItems: List<MainItem>,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mainItems.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemCurrent = mainItems[position]
        holder.bind(itemCurrent)
    }

    inner class MainViewHolder(
        private val binding: ItemMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainItem) {
            binding.apply {
                icItem.setImageResource(item.drawableRes)
                txtItem.setText(item.textStringId)
                cardItem.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }
}