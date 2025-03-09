package com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.splashscreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashscreenViewModel : ViewModel() {

	private val _state = MutableStateFlow(SplashscreenState())
	val state: StateFlow<SplashscreenState> = _state

	fun onAction(action: SplashscreenAction) {
		when (action) {
			is SplashscreenAction.OnCompleteSplashScreen -> {

			}
		}
	}
}