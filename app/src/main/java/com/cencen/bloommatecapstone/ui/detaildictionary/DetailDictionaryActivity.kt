package com.cencen.bloommatecapstone.ui.detaildictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cencen.bloommatecapstone.R
import com.cencen.bloommatecapstone.data.model.FlowerItem
import com.cencen.bloommatecapstone.databinding.ActivityDetailDictionaryBinding

class DetailDictionaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDictionaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDictionaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        showLoading(true)
        initialView()
    }

    private fun showLoading(isLoad: Boolean) {
        binding.loadingProcess.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun initialView() {
        val detail = intent.getParcelableExtra<FlowerItem>(EXTRA_DETAIL)

        binding.apply {
            tvTitle.text = detail?.localName
            tvLatin.text = detail?.flowerName
            tvDesc.text = detail?.description
            tvClassification.text = detail?.klasifikasi
            tvTreatment.text = detail?.treatment
        }
        showLoading(false)
    }


    /*private fun initialView() {
        val detailList = intent.getParcelableArrayListExtra<FlowerItem>(EXTRA_DETAIL)

        if (detailList != null && detailList.isNotEmpty()) {
            val detail = detailList[0] // Mengambil item pertama dari ArrayList

            binding.apply {
                tvTitle.text = detail.localName
                tvLatin.text = detail.flowerName
                tvDesc.text = detail.description
                tvClassification.text = detail.klasifikasi
                tvTreatment.text = detail.treatment
            }
        } else {
            Toast.makeText(this, "No flower data available", Toast.LENGTH_SHORT).show()
        }

        showLoading(false)
    }*/


    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}