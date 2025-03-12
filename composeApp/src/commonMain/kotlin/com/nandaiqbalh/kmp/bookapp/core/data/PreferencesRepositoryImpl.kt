package com.nandaiqbalh.kmp.bookapp.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.nandaiqbalh.kmp.bookapp.core.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepositoryImpl(private val dataStore: DataStore<Preferences>) :
	PreferencesRepository {

	override suspend fun saveString(key: String, value: String) {
		dataStore.edit { preferences -> preferences[stringPreferencesKey(key)] = value }
	}

	override fun getString(key: String, default: String): Flow<String> {
		return dataStore.data.map { preferences -> preferences[stringPreferencesKey(key)] ?: default }
	}

	override suspend fun saveBoolean(key: String, value: Boolean) {
		dataStore.edit { preferences -> preferences[booleanPreferencesKey(key)] = value }
	}

	override fun getBoolean(key: String, default: Boolean): Flow<Boolean> {
		return dataStore.data.map { preferences -> preferences[booleanPreferencesKey(key)] ?: default }
	}

	override suspend fun saveInt(key: String, value: Int) {
		dataStore.edit { preferences -> preferences[intPreferencesKey(key)] = value }
	}

	override fun getInt(key: String, default: Int): Flow<Int> {
		return dataStore.data.map { preferences -> preferences[intPreferencesKey(key)] ?: default }
	}

	override suspend fun saveLong(key: String, value: Long) {
		dataStore.edit { preferences -> preferences[longPreferencesKey(key)] = value }
	}

	override fun getLong(key: String, default: Long): Flow<Long> {
		return dataStore.data.map { preferences -> preferences[longPreferencesKey(key)] ?: default }
	}
}
