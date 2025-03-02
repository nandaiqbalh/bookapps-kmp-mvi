@file:OptIn(FlowPreview::class)

package com.nandaiqbalh.kmp.bookapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.nandaiqbalh.kmp.bookapp.book_feature.data.network.KtorRemoteBookDataSource
import com.nandaiqbalh.kmp.bookapp.book_feature.data.repository.BookRepositoryImpl
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListScreenRoot
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListViewModel
import com.nandaiqbalh.kmp.bookapp.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.FlowPreview

@Composable
fun App(engine: HttpClientEngine) {

	BookListScreenRoot(
		viewModel = remember {
			BookListViewModel(
				bookRepository = BookRepositoryImpl(
					remoteBookDataSource = KtorRemoteBookDataSource(
						httpClient = HttpClientFactory.create(
							engine = engine
						)
					)
				)
			)
		},
		onBookClick = {

		}
	)
}