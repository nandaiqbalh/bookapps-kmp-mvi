package com.nandaiqbalh.kmp.bookapp.book_feature.data.network

import com.nandaiqbalh.kmp.bookapp.book_feature.data.dto.SearchResponseDto
import com.nandaiqbalh.kmp.bookapp.core.domain.DataError
import com.nandaiqbalh.kmp.bookapp.core.domain.Result

interface RemoteBookDataSource {

	suspend fun searchBooks(
		query: String,
		resultLimit: Int? = null
	): Result<SearchResponseDto, DataError.Remote>
}