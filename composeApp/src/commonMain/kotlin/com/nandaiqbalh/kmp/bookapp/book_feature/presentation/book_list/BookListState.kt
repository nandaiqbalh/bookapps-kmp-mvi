package com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list

import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.core.presentation.UiText

data class BookListState(
	val searchQuery: String = "Football",
	val searchResults: List<Book> = books,
	val favoriteBooks: List<Book> = emptyList(),
	val isLoading: Boolean = false,
	val selectedTabIndex: Int = 0,
	val errorMessage: UiText? = null
)

private val books = (1..100).map {
	Book(
		id = it.toString(),
		title = "Book $it",
		imageUrl = "https://",
		authors = listOf("Nanda Iqbal Hanafi, Iqbal, Hanafi"),
		description = "Description of book $it",
		languages = emptyList(),
		firstPublishYear = null,
		averageRating = 4.6546456,
		ratingCount = 5,
		numPages = 100,
		numEditions = 3
	)
}