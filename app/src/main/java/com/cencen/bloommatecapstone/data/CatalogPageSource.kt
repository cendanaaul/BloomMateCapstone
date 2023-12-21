package com.cencen.bloommatecapstone.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cencen.bloommatecapstone.data.api.ApiServices
import com.cencen.bloommatecapstone.data.model.CatalogItem


class CatalogPageSource(
    private val api: ApiServices
): PagingSource<Int, CatalogItem>() {
    override fun getRefreshKey(state: PagingState<Int, CatalogItem>): Int? {
        return state.anchorPosition?.let {
            val pageAnchor = state.closestPageToPosition(it)
            pageAnchor?.prevKey?.plus(1) ?: pageAnchor?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatalogItem> {
        return try {
            val paging = params.key ?: INITIAL_PAGE_IDX
            val pageSize = params.loadSize
            val response = api.getCatalog()

            if (response.status == "success") {
                Log.d("CatalogPageSource", "Data loaded successfully")
                val catalogItems = response.data

                LoadResult.Page(
                    data = catalogItems,
                    prevKey = if (paging == INITIAL_PAGE_IDX) null else paging - 1,
                    nextKey = if (catalogItems.isEmpty()) null else paging + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to load catalog"))
            }
        } catch (e: Exception) {
            Log.e("CatalogPageSource", "Error loading data: ${e.message}")
            LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_IDX = 1
    }
}