package com.cencen.bloommatecapstone.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cencen.bloommatecapstone.R
import com.cencen.bloommatecapstone.adapter.CatalogAdapter.Companion.DIFF_CALL
import com.cencen.bloommatecapstone.data.model.FlowerItem
import com.cencen.bloommatecapstone.databinding.ItemDictionaryListBinding
import com.cencen.bloommatecapstone.ui.detaildictionary.DetailDictionaryActivity

class FlowerAdapter : PagingDataAdapter<FlowerItem, FlowerAdapter.FlowerViewHolder>(DIFF_CALL) {
    class FlowerViewHolder(private val binding: ItemDictionaryListBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(flower: FlowerItem) {
            Log.d("FlowerAdapter", "Binding FlowerItem: ${flower.flowerName}")
            with(binding) {
                flower.cover?.let { coverUrl ->
                    Glide.with(itemView.context)
                        .load(flower.cover)
                        .placeholder(R.drawable.ic_flower)
                        .error(R.drawable.ic_imgnotfound)
                        .centerCrop()
                        .circleCrop()
                        .into(binding.ivItemDictionary)
                }

                tvTitleFlower.text = flower.localName
                tvNameFlower.text = flower.flowerName
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailDictionaryActivity::class.java).apply {
                    putExtra(DetailDictionaryActivity.EXTRA_DETAIL, flower)
                }
                val option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    androidx.core.util.Pair(binding.ivItemDictionary, "cover"),
                    androidx.core.util.Pair(binding.tvTitleFlower, "localName"),
                    androidx.core.util.Pair(binding.tvNameFlower, "flowerName")
                )
                it.context.startActivity(intent, option.toBundle())
            }
        }
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
       val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlowerViewHolder {
        val ui = ItemDictionaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("FlowerAdapter", "onCreateViewHolder: INFLATED")
        return FlowerViewHolder(ui)
    }

    companion object {
        val DIFF_CALL = object : DiffUtil.ItemCallback<FlowerItem>() {
            override fun areItemsTheSame(oldItem: FlowerItem, newItem: FlowerItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FlowerItem, newItem: FlowerItem): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}