package com.nandaiqbalh.kmp.bookapp.core.domain

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
	suspend fun saveString(key: String, value: String)
	fun getString(key: String, default: String): Flow<String>

	suspend fun saveBoolean(key: String, value: Boolean)
	fun getBoolean(key: String, default: Boolean): Flow<Boolean>

	suspend fun saveInt(key: String, value: Int)
	fun getInt(key: String, default: Int): Flow<Int>

	suspend fun saveLong(key: String, value: Long)
	fun getLong(key: String, default: Long): Flow<Long>
}
