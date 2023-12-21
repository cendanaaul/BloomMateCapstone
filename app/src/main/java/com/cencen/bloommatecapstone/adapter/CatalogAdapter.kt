package com.cencen.bloommatecapstone.adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cencen.bloommatecapstone.R
import com.cencen.bloommatecapstone.data.model.CatalogItem
import com.cencen.bloommatecapstone.data.model.ListCatalog
import com.cencen.bloommatecapstone.databinding.ItemProductListBinding
import com.cencen.bloommatecapstone.ui.detailproduct.DetailProductActivity
import java.text.NumberFormat
import java.util.Locale

class CatalogAdapter : PagingDataAdapter<CatalogItem, CatalogAdapter.CatalogViewHolder>(DIFF_CALL) {
    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
       val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    class CatalogViewHolder(private val binding: ItemProductListBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(catalog: CatalogItem) {
            Log.d("CatalogAdapter", "Binding CatalogItem: ${catalog.localName}")
            with(binding) {
                catalog.cover?.let { _ ->
                    Glide.with(itemView.context)
                        .load(catalog.cover)
                        .placeholder(R.drawable.ic_flower)
                        .error(R.drawable.ic_imgnotfound)
                        .centerCrop()
                        .into(binding.ivProduct)
                }

                val formattedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                    .format(catalog.price ?: 0)

                tvTitleFlowers.text = catalog.localName
                tvPrice.text = formattedPrice
            }
            itemView.setOnClickListener { 
                val intent = Intent(itemView.context,DetailProductActivity::class.java).apply { 
                    putExtra(DetailProductActivity.EXTRA_DETAIL, catalog)
                }
                val option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    androidx.core.util.Pair(binding.ivProduct, "cover"),
                    androidx.core.util.Pair(binding.tvTitleFlowers, "flowerName"),
                    androidx.core.util.Pair(binding.tvPrice, "price")
                )
                it.context.startActivity(intent, option.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatalogViewHolder {
        val ui = ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("CatalogAdapter", "onCreateViewHolder: INFLATED")
        return CatalogViewHolder(ui)
    }


    companion object {
        val DIFF_CALL = object : DiffUtil.ItemCallback<CatalogItem>() {
            override fun areItemsTheSame(oldItem: CatalogItem, newItem: CatalogItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CatalogItem, newItem: CatalogItem): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}