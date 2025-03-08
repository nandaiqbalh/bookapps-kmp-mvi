package com.nandaiqbalh.kmp.bookapp.book_feature.domain.repository

import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.core.domain.DataError
import com.nandaiqbalh.kmp.bookapp.core.domain.EmptyResult
import com.nandaiqbalh.kmp.bookapp.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
	suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>

	suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError>

	fun getFavoriteBooks(): Flow<List<Book>>
	fun isBookFavorite(id: String): Flow<Boolean>
	suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
	suspend fun deleteFromFavorites(id: String)

}