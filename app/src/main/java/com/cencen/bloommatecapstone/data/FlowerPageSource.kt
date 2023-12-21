package com.cencen.bloommatecapstone.data

import android.util.Log
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cencen.bloommatecapstone.data.api.ApiServices
import com.cencen.bloommatecapstone.data.model.FlowerItem

class FlowerPageSource(
    private val api: ApiServices
): PagingSource<Int, FlowerItem>() {
    override fun getRefreshKey(state: PagingState<Int, FlowerItem>): Int? {
        return state.anchorPosition?.let {
            val pageAnchor = state.closestPageToPosition(it)
            pageAnchor?.prevKey?.plus(1) ?: pageAnchor?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FlowerItem> {
        return try {
            val paging = params.key ?: INITIAL_PAGE_IDX
            val response = api.getFlower()

            if (response.status == "success") {
                Log.d("FlowerPageSource", "Data loaded successfully")
                val flowerItems = response.data

                LoadResult.Page(
                    data = flowerItems,
                    prevKey = if (paging == INITIAL_PAGE_IDX) null else paging -1,
                    nextKey = if (flowerItems.isEmpty()) null else paging + 1
                )

            } else {
                LoadResult.Error(Exception("Failed to load flower"))
            }
        } catch (e: Exception) {
            Log.e("FlowerPageSource", "Error loading data: ${e.message}")
            LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_IDX = 1
    }
}