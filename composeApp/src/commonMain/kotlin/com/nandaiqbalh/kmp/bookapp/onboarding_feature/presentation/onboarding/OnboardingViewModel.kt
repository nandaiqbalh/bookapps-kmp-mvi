package com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel : ViewModel() {

	private var _state = MutableStateFlow(OnboardingState())
	val state: StateFlow<OnboardingState> get() = _state

	fun onAction(action: OnboardingAction) {
		when (action) {
			OnboardingAction.OnComplete -> {

			}

			OnboardingAction.OnSkip -> {

			}
		}
	}
}