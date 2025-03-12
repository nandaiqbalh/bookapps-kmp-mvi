package com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.onboarding

sealed interface OnboardingAction{

	data object OnComplete: OnboardingAction

	data object OnSkip: OnboardingAction
}