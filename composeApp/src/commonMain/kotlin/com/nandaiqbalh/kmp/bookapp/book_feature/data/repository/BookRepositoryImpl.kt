package com.nandaiqbalh.kmp.bookapp.book_feature.data.repository

import androidx.sqlite.SQLiteException
import com.nandaiqbalh.kmp.bookapp.book_feature.data.database.FavoriteBookDao
import com.nandaiqbalh.kmp.bookapp.book_feature.data.mapper.toBook
import com.nandaiqbalh.kmp.bookapp.book_feature.data.mapper.toBookEntity
import com.nandaiqbalh.kmp.bookapp.book_feature.data.network.RemoteBookDataSource
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.repository.BookRepository
import com.nandaiqbalh.kmp.bookapp.core.domain.DataError
import com.nandaiqbalh.kmp.bookapp.core.domain.EmptyResult
import com.nandaiqbalh.kmp.bookapp.core.domain.Result
import com.nandaiqbalh.kmp.bookapp.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
	private val remoteBookDataSource: RemoteBookDataSource,
	private val favoriteBookDao: FavoriteBookDao,
) : BookRepository {
	override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
		return remoteBookDataSource
			.searchBooks(query)
			.map { dto ->
				dto.results.map { it.toBook() }
			}
	}

	override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError> {
		val localResult = favoriteBookDao.getFavoriteBook(bookWorkId)
		return if (localResult == null) {
			remoteBookDataSource

				.getBookDetail(bookWorkId)
				.map { it.description }
		} else {
			Result.Success(localResult.description)
		}
	}

	override fun getFavoriteBooks(): Flow<List<Book>> {
		return favoriteBookDao.getFavoriteBooks()
			.map { bookEntities ->
				bookEntities.map {
					it.toBook()
				}
			}
	}

	override fun isBookFavorite(id: String): Flow<Boolean> {
		return favoriteBookDao.getFavoriteBooks()
			.map { bookEntities ->
				bookEntities.any { it.id == id }
			}
	}

	override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
		return try {
			favoriteBookDao.upsert(book.toBookEntity())

			Result.Success(Unit)
		} catch (e: SQLiteException) {
			Result.Error(DataError.Local.DISK_FULL)
		}
	}

	override suspend fun deleteFromFavorites(id: String) {
		favoriteBookDao.deleteFavoriteBook(id)
	}
}