package com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.nandaiqbalh.kmp.bookapp.core.domain.PreferencesRepository

class SplashscreenViewModel(
	val preferencesRepository: PreferencesRepository
) : ViewModel() {

	fun onAction(action: SplashscreenAction) {
		when (action) {
			is SplashscreenAction.OnCompleteSplashScreen -> {

			}
		}
	}
}