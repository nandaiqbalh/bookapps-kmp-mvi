@file:OptIn(FlowPreview::class)

package com.nandaiqbalh.kmp.bookapp.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.SelectedBookViewModel
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListScreenRoot
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {

	MaterialTheme {

		val navController = rememberNavController()

		NavHost(
			navController = navController,
			startDestination = Route.BookGraph
		) {

			navigation<Route.BookGraph>(
				startDestination = Route.BookList
			) {

				// composable for book list screen
				composable<Route.BookList> {entry ->
					val viewModel = koinViewModel<BookListViewModel>()

					// shared view model
					val selectedBookViewModel =
						entry.sharedKoinViewModel<SelectedBookViewModel>(navController)

					LaunchedEffect(true){
						selectedBookViewModel.onSelectBook(null)
					}

					BookListScreenRoot(
						viewModel = viewModel,
						onBookClick = { book ->

							selectedBookViewModel.onSelectBook(book)

							navController.navigate(
								Route.BookDetail(book.id)
							)
						}
					)
				}

				// composable for book detail screen
				composable<Route.BookDetail> { entry ->

					// shared view model
					val selectedBookViewModel =
						entry.sharedKoinViewModel<SelectedBookViewModel>(navController)

					val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

					Box(
						modifier = Modifier
							.fillMaxSize(),
						contentAlignment = Alignment.Center
					) {
						Text(
							"Hello, World! The book is $selectedBook"
						)
					}
				}
			}
		}
	}
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
	navController: NavController,
): T {
	// Use .orEmpty() to handle potential null routes
	val navGraphRoute = destination.parent?.route.orEmpty()

	// If no route is found, fall back to standard ViewModel creation
	return if (navGraphRoute.isNotEmpty()) {
		val parentEntry = remember(this) {
			navController.getBackStackEntry(navGraphRoute)
		}
		koinViewModel(viewModelStoreOwner = parentEntry)
	} else {
		// Fallback to standard ViewModel creation if no route is found
		koinViewModel()
	}
}