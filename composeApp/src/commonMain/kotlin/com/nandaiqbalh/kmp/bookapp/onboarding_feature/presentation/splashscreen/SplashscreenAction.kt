package com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.splashscreen

sealed interface SplashscreenAction {
	data class OnCompleteSplashScreen(val isComplete: Boolean) : SplashscreenAction
}