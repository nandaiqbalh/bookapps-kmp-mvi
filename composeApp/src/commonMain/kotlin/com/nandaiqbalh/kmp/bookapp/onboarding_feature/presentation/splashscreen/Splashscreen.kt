package com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.splashscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import bookapps_kmp.composeapp.generated.resources.Res
import bookapps_kmp.composeapp.generated.resources.app_name
import bookapps_kmp.composeapp.generated.resources.montserrat_bold
import com.nandaiqbalh.kmp.bookapp.core.presentation.primaryBlue
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashScreenRoot(
	viewModel: SplashscreenViewModel,
	onCompleteSplash: (Boolean) -> Unit,
) {

	SplashScreen(
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
	onAction: (SplashscreenAction) -> Unit,
) {

	LaunchedEffect(Unit) {
		delay(3000)
		onAction(SplashscreenAction.OnCompleteSplashScreen(isComplete = true))
	}

	var visible by remember { mutableStateOf(false) }

	LaunchedEffect(Unit) {
		delay(100) // Initial delay before starting the animation
		visible = true
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(primaryBlue),
		contentAlignment = Alignment.Center
	) {

		// Animated Visibility for Text
		AnimatedVisibility(
			visible = visible,
			enter = expandHorizontally(
				initialWidth = { 20 },
				expandFrom = Alignment.CenterHorizontally,
				animationSpec = tween(
					durationMillis = 1200, // Set the duration to 1200 ms
					easing = LinearOutSlowInEasing
				)
			),
			exit = shrinkHorizontally(
				animationSpec = tween(
					durationMillis = 1000, // Set the duration to 1000 ms
					easing = FastOutLinearInEasing
				),
				shrinkTowards = Alignment.CenterHorizontally
			) { fullWidth -> fullWidth / 4 }
		) {
			// Text Content that will expand
			Text(
				text = stringResource(Res.string.app_name),
				style = TextStyle(
					fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
					fontSize = 40.sp,
					color = Color.White
				)
			)
		}
	}
}
