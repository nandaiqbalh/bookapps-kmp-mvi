package com.nandaiqbalh.kmp.bookapp.book_feature.data.mapper

import com.nandaiqbalh.kmp.bookapp.book_feature.data.database.BookEntity
import com.nandaiqbalh.kmp.bookapp.book_feature.data.dto.SearchBookDto
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book

fun SearchBookDto.toBook(): Book {
	return Book(
		id = id.substringAfterLast("/"),
		title = title,
		imageUrl = if (coverKey != null) "https://covers.openlibrary.org/b/olid/$coverKey-L.jpg" else "https://covers.openlibrary.org/b/olid/$coverAlternativeKey-L.jpg",
		authors = authorNames ?: emptyList(),
		languages = languages ?: emptyList(),
		description = null,
		firstPublishYear = firstPublishYear.toString(),
		averageRating = ratingsAverage,
		ratingCount = ratingsCount,
		numPages = numPagesMedian,
		numEditions = numEditions ?: 0
	)
}

fun Book.toBookEntity(): BookEntity{
	return BookEntity(
		id = id,
		title = title,
		imageUrl = imageUrl,
		authors = authors,
		languages = languages,
		description = description,
		firstPublishYear = firstPublishYear.toString(),
		ratingsAverage = averageRating,
		ratingsCount = ratingCount,
		numPagesMedian = numPages,
		numEditions = numEditions
	)
}

fun BookEntity.toBook(): Book{
	return Book(
		id = id,
		title = title,
		imageUrl = imageUrl,
		authors = authors,
		languages = languages,
		description = description,
		firstPublishYear = firstPublishYear.toString(),
		averageRating = ratingsAverage,
		ratingCount = ratingsCount,
		numPages = numPagesMedian,
		numEditions = numEditions
	)
}