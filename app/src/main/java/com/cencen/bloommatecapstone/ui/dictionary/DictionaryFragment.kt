package com.cencen.bloommatecapstone.ui.dictionary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.cencen.bloommatecapstone.adapter.FlowerAdapter
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.data.model.DataFlower
import com.cencen.bloommatecapstone.data.model.FlowerItem
import com.cencen.bloommatecapstone.data.response.PredictResponse
import com.cencen.bloommatecapstone.databinding.FragmentDictionaryBinding
import com.cencen.bloommatecapstone.ui.detaildictionary.DetailDictionaryActivity
import com.cencen.bloommatecapstone.ui.detailscan.DetailScanActivity
import com.cencen.bloommatecapstone.util.ViewModelProviderFactory
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.Serializable

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding
    private lateinit var flowerAdapter: FlowerAdapter
    private lateinit var dictionaryViewModel: DictionaryViewModel

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedImageUri ->
            val file = File(getRealPathFromURI(selectedImageUri))
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

            lifecycleScope.launch {
                try {
                    val response = dictionaryViewModel.predictFlower(imagePart)
                    Log.d("Predict Flower response:", response.toString())
                    when (response) {
                        is Libraries.Success<*> -> {
                            val data = response.asPredictResponse()
                            if (data != null) {
                                val dataFlower = data.data?.flowerData
                                Log.d("Predict Flower dataFlower:", dataFlower.toString())
                                dataFlower?.let {
                                    val flowerName = it.flowerName
                                    Log.d("Predict Flower flowerName:", flowerName.toString())
                                    flowerName?.let { name ->
                                        if (flowerName.isNotEmpty()) {
                                            getFlowerDetailsByName(data.data?.flowerData?.flowerName ?: "")
                                        } else {
                                            Toast.makeText(requireContext(), "Not found flower", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            } else {
                                // handle unexpected success case
                            }
                        }

                        is Libraries.Error -> {
                            // handle error
                            Toast.makeText(requireContext(), response.error, Toast.LENGTH_SHORT).show()
                        }

                        is Libraries.Loading -> {
                            showLoading(true)
                        }

                        else -> {}
                    }

                } catch (e: Exception) {
                    Log.d("Predict Flower exception:", e.message.toString())
                    // handle other exceptions if needed
                }
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        initialVM()
        initialView()
        initialDictionary()
        Log.d("DictionaryFragment", "LayoutManager: ${binding.rvItemDictionary.layoutManager}")
    }

    private fun showLoading(isLoad: Boolean) {
        binding.loadingProcess.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun initialVM() {
        val fact: ViewModelProviderFactory = ViewModelProviderFactory.getInstance(requireActivity())
        dictionaryViewModel = ViewModelProvider(this, fact)[DictionaryViewModel::class.java]
    }

    private fun initialView() {
        flowerAdapter = FlowerAdapter()
        binding.rvItemDictionary.adapter = flowerAdapter
        binding.rvItemDictionary.layoutManager = GridLayoutManager(requireContext(), 1)

        dictionaryViewModel.getFlower()

        binding.ibGallery.setOnClickListener {
            openGallery()
        }
    }

    private fun initialDictionary() {
        dictionaryViewModel.getFlower().observe(viewLifecycleOwner) {
            Log.d("DictionaryFragment", "Flower data received: ${it?.toString()}")
            flowerAdapter.submitData(lifecycle, it)
            showLoading(false)
        }
    }

    private fun openGallery() {
        getContent.launch("image/*")
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            it.moveToFirst()
            val columnIndex = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            it.getString(columnIndex)
        }
    }

    private fun getFlowerDetailsByName(flowerName: String) {
        lifecycleScope.launch {
            try {
                val response = dictionaryViewModel.getFlowerDetailsByName(flowerName)
                when (response) {
                    is Libraries.Success -> {
                        val flowerItem = response.data.data
                        flowerItem?.let { item ->
                            val arrayList = ArrayList(item)
                            val intent = Intent(requireContext(), DetailScanActivity::class.java).apply {
                                putExtra(DetailScanActivity.EXTRA_DETAIL, arrayList)
                            }
                            startActivity(intent)
                        } ?: run {
                            Toast.makeText(requireContext(), "No flower data available", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Libraries.Error -> {
                        Toast.makeText(requireContext(), response.error, Toast.LENGTH_SHORT).show()
                    }

                    is Libraries.Loading -> {
                        showLoading(true)
                    }

                    else -> { }
                }
            } catch (e: Exception) {
                Log.d("getFlowerDetailsByName", "Exception: ${e.message}")
                // Handle other exceptions if needed
            } finally {
                showLoading(false)
            }
        }
    }



    fun Libraries<*>.asPredictResponse(): PredictResponse? {
        return if (this is Libraries.Success<*>) {
            val responseData = this.data as? PredictResponse
            responseData?.data?.let { dataFlower ->
                // Make sure 'data' is of type DataFlower
                if (dataFlower is DataFlower) {
                    responseData
                } else {
                    null
                }
            } ?: run {
                null
            }
        } else {
            null
        }
    }


    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
}



/*private fun getFlowerDetailsById(flowerId: String) {
        dictionaryViewModel.getFlowerDetailsByFlowerId(flowerId)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Libraries.Success -> {
                        val flowerItem = convertFlowerResponseToFlowerItem(result.data)
                        flowerItem?.let { item ->
                            val intent = Intent(requireContext(), DetailDictionaryActivity::class.java).apply {
                                putExtra(DetailDictionaryActivity.EXTRA_DETAIL, item)
                            }
                            startActivity(intent)
                        } ?: run {
                            Toast.makeText(requireContext(), "Failed to convert data", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Libraries.Error -> {
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }

                    is Libraries.Loading -> {
                        showLoading(true)
                    }

                    else -> { }
                }
            })
    }*/



/*private fun convertFlowerResponseToFlowerItem(response: FlowerItem?): FlowerItem? {
    return response?.let {
        FlowerItem(
            flowerName = it.flowerName,
            id = it.id,
            localName = it.localName,
            cover = it.cover,
            price = it.price,
            description = it.description,
            treatment = it.treatment,
            klasifikasi = it.klasifikasi,
            stock = it.stock
        )
    }
}*/

