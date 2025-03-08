package com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_detail

import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book

data class BookDetailState(
	val isLoading: Boolean = true,
	val isFavorite: Boolean = false,
	val book: Book? = null
)