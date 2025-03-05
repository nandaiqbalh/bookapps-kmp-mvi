package com.nandaiqbalh.kmp.bookapp.book_feature.data.mapper

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