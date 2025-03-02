package com.nandaiqbalh.kmp.bookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListScreen
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListScreenRoot
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListState
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}


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

@Preview
@Composable
fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchQuery = "Football",
            searchResults = books,
            favoriteBooks = books,
            isLoading = false,
            errorMessage = null,
            selectedTabIndex = 0,
        ),
        onAction = {

        }
    )
}