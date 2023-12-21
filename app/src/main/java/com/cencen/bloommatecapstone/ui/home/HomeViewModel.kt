package com.cencen.bloommatecapstone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cencen.bloommatecapstone.data.api.ApiConfig
import com.cencen.bloommatecapstone.data.model.CatalogItem
import com.cencen.bloommatecapstone.data.model.ListCatalog
import com.cencen.bloommatecapstone.data.model.User
import com.cencen.bloommatecapstone.data.repository.CatalogRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val rep: CatalogRepository) : ViewModel() {

    fun getCatalog(): LiveData<PagingData<CatalogItem>> {
        return rep.getCatalog().cachedIn(viewModelScope)
    }
}

/*

private val _searchResult = MutableLiveData<PagingData<CatalogItem>> ()
val searchResult : LiveData<PagingData<CatalogItem>> get() = _searchResult
*/
