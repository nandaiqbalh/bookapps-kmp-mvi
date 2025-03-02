package com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookapps_kmp.composeapp.generated.resources.Res
import bookapps_kmp.composeapp.generated.resources.favorites
import bookapps_kmp.composeapp.generated.resources.no_favorites
import bookapps_kmp.composeapp.generated.resources.no_search_result
import bookapps_kmp.composeapp.generated.resources.search_result
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.components.BookList
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.components.BookSearchBar
import com.nandaiqbalh.kmp.bookapp.core.presentation.DarkBlue
import com.nandaiqbalh.kmp.bookapp.core.presentation.DesertWhite
import com.nandaiqbalh.kmp.bookapp.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/*
We are following best practices for structuring our composable.
	✅ If you're working with Jetpack Compose and Navigation
	✅ If you want better separation of concerns
	✅ If you need easier UI testing & previews
 */


@Composable
fun BookListScreenRoot(
	viewModel: BookListViewModel = koinViewModel(),
	onBookClick: (Book) -> Unit,
) {
	val state by viewModel.state.collectAsStateWithLifecycle()

	BookListScreen(
		state = state,
		onAction = { action ->
			when (action) {
				// we need to intercept the action from view model, since we need handling action for specified UI manner (for example in this case is navigation, because our viewmodel can not access navigation controller)
				is BookListAction.OnBookClick -> {
					onBookClick(action.book)
				}
				// leave another action that not related to UI specific as default action from view model
				else -> {
					Unit
				}
			}

			// forward intercepted action to view model
			viewModel.onAction(action)
		}
	)
}


// leave our actual screen composable in isolated
@Composable
fun BookListScreen(
	state: BookListState,
	onAction: (BookListAction) -> Unit,
) {

	val keyboardController = LocalSoftwareKeyboardController.current
	val pagerState = rememberPagerState { 2 }
	val searchLazyListState = rememberLazyListState()
	val favoriteLazyListState = rememberLazyListState()

	LaunchedEffect(state.searchResults){
		searchLazyListState.animateScrollToItem(0)
	}

	LaunchedEffect(state.selectedTabIndex){
		pagerState.animateScrollToPage(state.selectedTabIndex)
	}

	LaunchedEffect(pagerState.currentPage) {
		onAction(BookListAction.OnTabSelected(pagerState.currentPage))
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(DarkBlue)
			.statusBarsPadding(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		BookSearchBar(
			searchQuery = state.searchQuery,
			onSearchQueryChange = {
				onAction(BookListAction.OnSearchQueryChange(it))
			},
			onImeSearch = {
				keyboardController?.hide()
			},
			modifier = Modifier
				.widthIn(max = 400.dp)
				.fillMaxWidth()
				.padding(16.dp)
		)

		Surface(
			modifier = Modifier
				.weight(1f)
				.fillMaxWidth(),
			color = DesertWhite,
			shape = RoundedCornerShape(
				topStart = 32.dp,
				topEnd = 32.dp
			)
		){

			Column(
				horizontalAlignment = Alignment.CenterHorizontally
			){
				TabRow(
					selectedTabIndex = state.selectedTabIndex,
					modifier = Modifier
						.padding(vertical = 12.dp)
						.widthIn(max = 700.dp)
						.fillMaxWidth(),
					containerColor = DesertWhite,
					indicator = { tabPositions ->
						TabRowDefaults.SecondaryIndicator(
							modifier = Modifier
								.tabIndicatorOffset(tabPositions[state.selectedTabIndex])
						)
					}
				){
					Tab(
						selected = state.selectedTabIndex == 0,
						onClick = {
							onAction(BookListAction.OnTabSelected(0))
						},
						modifier = Modifier.weight(1f),
						selectedContentColor = SandYellow,
						unselectedContentColor = Color.Black.copy(alpha = 0.5f)
					){
						Text(
							text = stringResource(Res.string.search_result),
							modifier = Modifier.padding(vertical = 12.dp)
						)
					}

					Tab(
						selected = state.selectedTabIndex == 1,
						onClick = {
							onAction(BookListAction.OnTabSelected(1))
						},
						modifier = Modifier.weight(1f),
						selectedContentColor = SandYellow,
						unselectedContentColor = Color.Black.copy(alpha = 0.5f)
					){
						Text(
							text = stringResource(Res.string.favorites),
							modifier = Modifier.padding(vertical = 12.dp)
						)
					}
				}

				Spacer(modifier = Modifier.height(4.dp))

				HorizontalPager(
					state = pagerState,
					modifier = Modifier
						.fillMaxWidth()
						.weight(1f)
				){ pageIndex: Int ->

					Box(
						modifier = Modifier
							.fillMaxSize(),
						contentAlignment = Alignment.Center
					){

						when(pageIndex){
							0 -> {
								if (state.isLoading){
									CircularProgressIndicator()
								} else {
									when{
										state.errorMessage != null -> {
											Text(
												text = state.errorMessage.asString(),
												textAlign = TextAlign.Center,
												style = MaterialTheme.typography.headlineSmall,
												color = MaterialTheme.colorScheme.error
											)
										}
										state.searchResults.isEmpty() -> {
											Text(
												text = stringResource(Res.string.no_search_result),
												textAlign = TextAlign.Center,
												style = MaterialTheme.typography.headlineSmall,
												color = MaterialTheme.colorScheme.error
											)
										}
										else -> {
											BookList(
												books = state.searchResults,
												onBookClick = { book ->
													onAction(BookListAction.OnBookClick(book))
												},
												modifier = Modifier.fillMaxSize(),
												scrollState = searchLazyListState
											)
										}
									}
								}
							}
							1 -> {
								if (state.favoriteBooks.isEmpty()){
									Text(
										text = stringResource(Res.string.no_favorites),
										textAlign = TextAlign.Center,
										style = MaterialTheme.typography.headlineSmall,
									)
								} else {
									BookList(
										books = state.favoriteBooks,
										onBookClick = { book ->
											onAction(BookListAction.OnBookClick(book))
										},
										modifier = Modifier.fillMaxSize(),
										scrollState = favoriteLazyListState
									)
								}
							}
						}
					}
				}

			}
		}
	}
}