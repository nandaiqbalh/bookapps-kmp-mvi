package com.nandaiqbalh.kmp.bookapp.book_feature.domain.repository

import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.core.domain.DataError
import com.nandaiqbalh.kmp.bookapp.core.domain.Result

interface BookRepository {
	suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>

	suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError>
}