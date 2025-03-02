package com.nandaiqbalh.kmp.bookapp.book_feature.data.network

import com.nandaiqbalh.kmp.bookapp.book_feature.data.dto.SearchBookDto
import com.nandaiqbalh.kmp.bookapp.book_feature.data.dto.SearchResponseDto
import com.nandaiqbalh.kmp.bookapp.book_feature.domain.model.Book
import com.nandaiqbalh.kmp.bookapp.core.data.safeCall
import com.nandaiqbalh.kmp.bookapp.core.domain.DataError
import com.nandaiqbalh.kmp.bookapp.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter


private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
	private val httpClient: HttpClient,
): RemoteBookDataSource {
	override suspend fun searchBooks(
		query: String,
		resultLimit: Int?,
	): Result<SearchResponseDto, DataError.Remote> {
		return safeCall {
			httpClient.get(
				urlString = "$BASE_URL/search.json"
			){
				parameter("q", query)
				parameter("limit", resultLimit)
				parameter(
					"fields",
					"key,title,language,cover_i,author_key,author_name,cover_edition_key," +
							"first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count"
				) // Include only fields from SearchBookDto
			}
		}
	}
}