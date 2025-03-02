package com.nandaiqbalh.kmp.bookapp.book_feature.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
	@SerialName("docs") val results: List<SearchBookDto>
)