package com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list

import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.core.presentation.UiText

data class BookListState(
	val searchQuery: String = "Football",
	val searchResults: List<Book> = emptyList(),
	val favoriteBooks: List<Book> = emptyList(),
	val isLoading: Boolean = true,
	val selectedTabIndex: Int = 0,
	val errorMessage: UiText? = null
)
