@file:OptIn(FlowPreview::class)

package com.nandaiqbalh.kmp.bookapp.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.SelectedBookViewModel
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_detail.BookDetailAction
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_detail.BookDetailScreenRoot
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_detail.BookDetailViewModel
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListScreenRoot
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListViewModel
import com.nandaiqbalh.kmp.bookapp.core.utils.PreferenceKey
import com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.onboarding.OnboardingScreenRoot
import com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.onboarding.OnboardingViewModel
import com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.splashscreen.SplashScreenRoot
import com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.splashscreen.SplashscreenViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {

	MaterialTheme {

		val navController = rememberNavController()

		NavHost(
			navController = navController,
			startDestination = Route.Splashscreen
		) {

			// composable for book list screen
			composable<Route.Splashscreen>(
				exitTransition = { fadeOut(animationSpec = tween(500)) },
			) {
				val viewModel = koinViewModel<SplashscreenViewModel>()

				val isFirstInstall by viewModel.preferencesRepository
					.getBoolean(PreferenceKey.IS_FIRST_INSTALL, true)
					.collectAsState(true)

				SplashScreenRoot(
					viewModel = viewModel,
					onCompleteSplash = { isComplete ->
						if (isComplete) {
							if (isFirstInstall){
								// navigate to on boarding
								navController.navigate(Route.Onboarding) {
									popUpTo(Route.Splashscreen) { inclusive = true }
								}
							} else {
								// navigate to home
								navController.navigate(Route.BookGraph) {
									popUpTo(Route.Splashscreen) { inclusive = true }
								}
							}
						}
					}
				)
			}

			// composable for book list screen
			composable<Route.Onboarding>(
				enterTransition = { fadeIn(animationSpec = tween(1000)) },
				exitTransition = { fadeOut() }
			) {
				val viewModel = koinViewModel<OnboardingViewModel>()

				OnboardingScreenRoot(
					viewModel = viewModel,
					onComplete = {
						navController.navigate(
							Route.BookGraph
						)
					},
					onSkip = {
						navController.navigate(
							Route.BookGraph
						)
					}
				)
			}


			navigation<Route.BookGraph>(
				startDestination = Route.BookList,
				enterTransition = { fadeIn() }
			) {

				// composable for book list screen
				composable<Route.BookList>(
					exitTransition = { slideOutHorizontally() },
					popEnterTransition = { slideInHorizontally() }
				) {entry ->
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
				composable<Route.BookDetail>(
					enterTransition = {
						slideInHorizontally { initialOffset ->
							initialOffset
						}
					},
					exitTransition = {
						slideOutHorizontally { initialOffset ->
							initialOffset
						}
					}
				) { entry ->

					// shared view model
					val selectedBookViewModel =
						entry.sharedKoinViewModel<SelectedBookViewModel>(navController)

					val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

					val viewModel = koinViewModel<BookDetailViewModel>()

					LaunchedEffect(selectedBook){
						selectedBook?.let {
							viewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
						}
					}

					BookDetailScreenRoot(
						viewModel = viewModel,
						onBackClick = {
							navController.navigateUp()
						}
					)
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