@file:OptIn(FlowPreview::class)

package com.nandaiqbalh.kmp.bookapp.di

import com.nandaiqbalh.kmp.bookapp.book_feature.data.network.KtorRemoteBookDataSource
import com.nandaiqbalh.kmp.bookapp.book_feature.data.network.RemoteBookDataSource
import com.nandaiqbalh.kmp.bookapp.book_feature.data.repository.BookRepositoryImpl
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.repository.BookRepository
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.SelectedBookViewModel
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_detail.BookDetailViewModel
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListViewModel
import com.nandaiqbalh.kmp.bookapp.core.data.HttpClientFactory
import kotlinx.coroutines.FlowPreview
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformModule: Module

@OptIn(FlowPreview::class)
val sharedModule = module {
	single {
		HttpClientFactory.create(get())
	}

	singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
	singleOf(::BookRepositoryImpl).bind<BookRepository>()

	viewModelOf(::BookListViewModel)
	viewModelOf(::BookDetailViewModel)
	viewModelOf(::SelectedBookViewModel)

}