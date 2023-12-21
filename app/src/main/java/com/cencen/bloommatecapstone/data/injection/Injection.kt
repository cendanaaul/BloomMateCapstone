package com.cencen.bloommatecapstone.data.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.cencen.bloommatecapstone.data.api.ApiConfig
import com.cencen.bloommatecapstone.data.preference.PreferenceSetting
import com.cencen.bloommatecapstone.data.repository.CartRepository
import com.cencen.bloommatecapstone.data.repository.CatalogRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("bloommate")
object Injection {

    fun setRepository(con: Context): CatalogRepository {
        val conf = PreferenceSetting.getInstance(con.dataStore)
        val api = ApiConfig.getApiInstance()
        return CatalogRepository.getInstance(conf,api)
    }

    fun provideCartRepository(con: Context): CartRepository {
        val conf = PreferenceSetting.getInstance(con.dataStore)
        val api = ApiConfig.getApiInstance()
        return CartRepository.getInstance(conf,api)
    }
}