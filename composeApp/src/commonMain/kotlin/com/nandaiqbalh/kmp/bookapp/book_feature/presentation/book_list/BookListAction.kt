package com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list

import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book

sealed interface BookListAction {
	data class OnSearchQueryChange(val searchQuery: String) : BookListAction
	data class OnBookClick(val book: Book): BookListAction
	data class OnTabSelected(val index: Int): BookListAction
}