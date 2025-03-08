package com.nandaiqbalh.kmp.bookapp.book_feature.data.repository

import com.nandaiqbalh.kmp.bookapp.book_feature.data.mapper.toBook
import com.nandaiqbalh.kmp.bookapp.book_feature.data.network.RemoteBookDataSource
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.repository.BookRepository
import com.nandaiqbalh.kmp.bookapp.core.domain.DataError
import com.nandaiqbalh.kmp.bookapp.core.domain.Result
import com.nandaiqbalh.kmp.bookapp.core.domain.map

class BookRepositoryImpl(
	private val remoteBookDataSource: RemoteBookDataSource,
): BookRepository {
	override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
		return remoteBookDataSource
			.searchBooks(query)
			.map { dto ->
				dto.results.map { it.toBook() }
			}
	}

	override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError> {
		return remoteBookDataSource
			.getBookDetail(bookWorkId)
			.map { it.description }
	}
}