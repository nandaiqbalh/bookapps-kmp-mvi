package com.nandaiqbalh.kmp.bookapp.book_feature.data.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
	val description: String? = null,
)