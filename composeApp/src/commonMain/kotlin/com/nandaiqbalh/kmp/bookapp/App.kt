@file:OptIn(FlowPreview::class)

package com.nandaiqbalh.kmp.bookapp

import androidx.compose.runtime.Composable
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListScreenRoot
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {

	BookListScreenRoot(
		viewModel = koinViewModel<BookListViewModel>(),
		onBookClick = {

		}
	)
}