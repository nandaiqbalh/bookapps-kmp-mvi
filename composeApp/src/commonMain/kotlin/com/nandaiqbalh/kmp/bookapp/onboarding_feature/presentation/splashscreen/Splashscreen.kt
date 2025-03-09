package com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nandaiqbalh.kmp.bookapp.core.presentation.DarkBlue
import com.nandaiqbalh.kmp.bookapp.core.presentation.SandYellow
import kotlinx.coroutines.delay

@Composable
fun SplashScreenRoot(
	viewModel: SplashscreenViewModel,
	onCompleteSplash: (Boolean) -> Unit,
) {

	// collect state
	val state by viewModel.state.collectAsStateWithLifecycle()

	SplashScreen(
		state = state,
		onAction = { action ->
			when (action) {
				is SplashscreenAction.OnCompleteSplashScreen -> {
					onCompleteSplash(action.isComplete)
				}
			}

			viewModel.onAction(action)

		}
	)

}

@Composable
fun SplashScreen(
	state: SplashscreenState,
	onAction: (SplashscreenAction) -> Unit,
) {

	LaunchedEffect(state.isLoading) {
		if (state.isLoading) {
			delay(3000L)
			onAction(SplashscreenAction.OnCompleteSplashScreen(true))
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(DarkBlue),
		contentAlignment = Alignment.Center
	) {
		Box(
			modifier = Modifier
				.size(100.dp)
				.background(SandYellow)
		)
	}

}