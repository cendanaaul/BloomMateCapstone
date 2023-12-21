package com.cencen.bloommatecapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cencen.bloommatecapstone.data.model.CartItem
import com.cencen.bloommatecapstone.databinding.ItemCartProductBinding

class CartAdapter(private val onDeleteClickListener: OnDeleteClickListener) :
    ListAdapter<CartItem, CartAdapter.CartViewHolder>(DIF_UTIL) {

    interface OnDeleteClickListener {
        fun onDeleteClick(productId: String)
    }

    inner class CartViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var productCount = 0

        fun bind(cartItem: CartItem) {
            binding.apply {
                tvTitleFlower.text = cartItem.name
                tvPriceProduct.text = "Rp ${cartItem.price}"
                tvCount.text = cartItem.quantity.toString()
            }
            Glide.with(binding.ivItemProduct.context)
                .load(cartItem.cover)
                .into(binding.ivItemProduct)

            binding.ibAdd.setOnClickListener {
                productCount += 1
                binding.tvCount.text = productCount.toString()
            }
            binding.ibMinus.setOnClickListener {
                if (productCount == 0) {
                    productCount = 0
                } else {
                    productCount -= 1
                }
                binding.tvCount.text = productCount.toString()
            }
            binding.ibDelete.setOnClickListener {
                onDeleteClickListener.onDeleteClick(cartItem.productId)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartProductBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        private val DIF_UTIL = object : DiffUtil.ItemCallback<CartItem>() {
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
