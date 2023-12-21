package com.cencen.bloommatecapstone.ui.maps

import androidx.lifecycle.ViewModel
import com.cencen.bloommatecapstone.data.repository.CatalogRepository

class MapsViewModel(private val rep: CatalogRepository): ViewModel() {

    fun getFlowerSeller(floweName: String) = rep.getFlowerSeller(floweName)

}