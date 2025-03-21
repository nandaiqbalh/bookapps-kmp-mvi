package com.nandaiqbalh.kmp.bookapp.app

import kotlinx.serialization.Serializable

sealed interface Route{

	@Serializable
	data object Splashscreen: Route

	@Serializable
	data object Onboarding: Route

	@Serializable
	data object BookGraph: Route

	@Serializable
	data object BookList: Route

	@Serializable
	data class BookDetail(val id: String): Route
}