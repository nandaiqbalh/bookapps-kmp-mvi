package com.nandaiqbalh.kmp.bookapp.di

import com.nandaiqbalh.kmp.bookapp.book_feature.data.database.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
	get() = module {
		single<HttpClientEngine> { OkHttp.create()  }
		single { DatabaseFactory() }

	}