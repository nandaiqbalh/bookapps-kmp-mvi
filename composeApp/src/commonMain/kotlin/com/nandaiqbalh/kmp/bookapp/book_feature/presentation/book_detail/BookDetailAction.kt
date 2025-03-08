package com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_detail

import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book

sealed interface BookDetailAction {
	data object OnBackClick : BookDetailAction
	data object OnFavoriteClick : BookDetailAction
	data class OnSelectedBookChange(val book: Book) : BookDetailAction
}