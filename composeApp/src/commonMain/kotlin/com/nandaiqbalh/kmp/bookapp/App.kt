package com.nandaiqbalh.kmp.bookapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListScreenRoot
import com.nandaiqbalh.kmp.bookapp.book_feature.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    BookListScreenRoot(
        viewModel = remember { BookListViewModel() },
        onBookClick = {

        }
    )
}