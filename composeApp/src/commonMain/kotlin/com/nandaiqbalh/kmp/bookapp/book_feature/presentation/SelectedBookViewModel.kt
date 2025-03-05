package com.nandaiqbalh.kmp.bookapp.book_feature.presentation

import androidx.lifecycle.ViewModel
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// shared view model
class SelectedBookViewModel : ViewModel() {

	private val _selectedBook = MutableStateFlow<Book?>(null)
	val selectedBook = _selectedBook.asStateFlow()

	fun onSelectBook(book: Book?) {
		_selectedBook.value = book
	}
}