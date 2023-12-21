package com.cencen.bloommatecapstone.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.cencen.bloommatecapstone.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceSetting private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun login() {
        dataStore.edit { con ->
            con[STATE] = true
        }
    }

    fun getUserData(): Flow<User> {
        return dataStore.data.map { con ->
            User(
                con[CREDENTIAL] ?: "",
                con[STATE] ?: false
            )
        }
    }

    suspend fun saveUserData(user: User) {
        dataStore.edit { con ->
            con[STATE] = user.isLogin
            con[CREDENTIAL] = user.credential
        }
    }

    suspend fun logout() {
        dataStore.edit { con ->
            con.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferenceSetting? = null

        fun getInstance(dataStore: DataStore<Preferences>): PreferenceSetting {
            return INSTANCE ?: synchronized(this) {
                val instant = PreferenceSetting(dataStore)
                INSTANCE = instant
                instant
            }
        }

        private val CREDENTIAL = stringPreferencesKey("credential")
        private val STATE = booleanPreferencesKey("state")
    }
}