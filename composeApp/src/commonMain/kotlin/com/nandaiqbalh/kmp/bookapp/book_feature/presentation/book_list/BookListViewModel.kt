package com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.repository.BookRepository
import com.nandaiqbalh.kmp.bookapp.core.domain.onError
import com.nandaiqbalh.kmp.bookapp.core.domain.onSuccess
import com.nandaiqbalh.kmp.bookapp.core.domain.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@FlowPreview
class BookListViewModel(
	private val bookRepository: BookRepository
): ViewModel() {

	private val cachedBooks = emptyList<Book>()

	// define job
	private var searchJob: Job? = null
	private var favoritesJob: Job? = null

	private val _state = MutableStateFlow(BookListState())
	val state = _state
		.onStart {
			if (cachedBooks.isEmpty()){
				observeSearchQuery()
			}

			observeFavoriteBooks()
		}
		.stateIn(
			viewModelScope,
			SharingStarted.WhileSubscribed(5000L),
			_state.value
		)



	fun onAction(action: BookListAction){
		when(action){
			is BookListAction.OnBookClick -> {

			}
			is BookListAction.OnSearchQueryChange -> {
				_state.update { it.copy(searchQuery = action.searchQuery) }
			}
			is BookListAction.OnTabSelected -> {
				_state.update { it.copy(selectedTabIndex = action.index) }
			}
		}
	}

	private fun observeFavoriteBooks(){
		favoritesJob?.cancel()

		favoritesJob = bookRepository
			.getFavoriteBooks()
			.onEach { favoriteBooks ->
				_state.update {
					it.copy(
						favoriteBooks = favoriteBooks
					)
				}
			}
			.launchIn(viewModelScope)
	}

	private fun observeSearchQuery() {
		state
			.map { it.searchQuery }
			.distinctUntilChanged()
			.debounce(1000L)
			.onEach { query ->
				when {
					query.isBlank() -> {
						_state.update {
							it.copy(
								errorMessage = null,
								searchResults = cachedBooks
							)
						}
					}
					query.length >= 2 -> {
						searchJob?.cancel()
						searchJob = searchBooks(query)
					}
				}
			}
			.launchIn(viewModelScope)
	}

	private fun searchBooks(query: String) =
		viewModelScope.launch {
			_state.update { it.copy(isLoading = true) }
			bookRepository
				.searchBooks(query)
				.onSuccess { searchResults ->
					_state.update {
						it.copy(
							searchResults = searchResults,
							errorMessage = null,
							isLoading = false
						)
					}
				}.onError { error ->
					_state.update {
						it.copy(
							searchResults = emptyList(),
							isLoading = false,
							errorMessage = error.toUiText()
						)
					}
				}

		}
}