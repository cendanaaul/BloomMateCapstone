package com.cencen.bloommatecapstone.ui.detailproduct

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cencen.bloommatecapstone.R
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.data.call.AddCartRequest
import com.cencen.bloommatecapstone.data.model.CatalogItem
import com.cencen.bloommatecapstone.databinding.ActivityDetailProductBinding
import com.cencen.bloommatecapstone.ui.cart.CartFragment
import com.cencen.bloommatecapstone.ui.cart.CartViewModel
import com.cencen.bloommatecapstone.ui.cart.CartViewModelFactory

class DetailProductActivity : AppCompatActivity() {

    private val viewModel: CartViewModel by viewModels<CartViewModel> {
        CartViewModelFactory.getInstance(
            this
        )
    }
    private lateinit var binding: ActivityDetailProductBinding
    private var productCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val detail = intent.getParcelableExtra<CatalogItem>(EXTRA_DETAIL)
        showLoading(true)
        initialView(detail)
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

        binding.btnAddCart.setOnClickListener {
            val request = AddCartRequest(productId = detail?.id.orEmpty(), productCount)

            viewModel.addItemToCart(request).observe(this) { result ->
                when (result) {
                    is Libraries.Loading -> {
                    }

                    is Libraries.Error -> {
                    }

                    is Libraries.Success -> {
                        startActivity(Intent(this@DetailProductActivity, CartFragment::class.java))
                    }
                }
            }
        }
    }

    private fun initialView(detail: CatalogItem?) {

        binding.apply {
            tvTitle.text = detail?.localName
            tvLatin.text = detail?.flowerName
            tvDescription.text = detail?.description
            tvTreatment.text = detail?.treatment
        }
        Glide.with(this)
            .load(detail?.cover)
            .into(binding.ivBg)
        showLoading(false)

    }

    private fun showLoading(isLoad: Boolean) {
        binding.loadingProcess.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}