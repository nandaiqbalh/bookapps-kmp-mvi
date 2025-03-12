package com.nandaiqbalh.kmp.bookapp.di

import com.nandaiqbalh.kmp.bookapp.book_feature.data.database.DatabaseFactory
import com.nandaiqbalh.kmp.bookapp.core.data.PreferencesRepositoryImpl
import com.nandaiqbalh.kmp.bookapp.core.data.createDataStore
import com.nandaiqbalh.kmp.bookapp.core.domain.PreferencesRepository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
	get() = module {
		single<HttpClientEngine> { OkHttp.create()  }
		single { DatabaseFactory(androidApplication()) }
		single { createDataStore(androidApplication()) } // Inject platform-specific DataStore
		single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }
	}