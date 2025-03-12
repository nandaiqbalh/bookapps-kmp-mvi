package com.nandaiqbalh.kmp.bookapp.onboarding_feature.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bookapps_kmp.composeapp.generated.resources.Res
import bookapps_kmp.composeapp.generated.resources.btn_get_started
import bookapps_kmp.composeapp.generated.resources.btn_skip
import bookapps_kmp.composeapp.generated.resources.desc_ob_1
import bookapps_kmp.composeapp.generated.resources.desc_ob_2
import bookapps_kmp.composeapp.generated.resources.desc_ob_3
import bookapps_kmp.composeapp.generated.resources.iv_splash_1
import bookapps_kmp.composeapp.generated.resources.iv_splash_2
import bookapps_kmp.composeapp.generated.resources.iv_splash_3
import bookapps_kmp.composeapp.generated.resources.montserrat_medium
import bookapps_kmp.composeapp.generated.resources.montserrat_regular
import bookapps_kmp.composeapp.generated.resources.title_ob_1
import bookapps_kmp.composeapp.generated.resources.title_ob_2
import bookapps_kmp.composeapp.generated.resources.title_ob_3
import com.nandaiqbalh.kmp.bookapp.core.presentation.components.GeneralTextButton
import com.nandaiqbalh.kmp.bookapp.core.presentation.fontPrimary
import com.nandaiqbalh.kmp.bookapp.core.presentation.grayFont
import com.nandaiqbalh.kmp.bookapp.core.presentation.primaryBlue
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingScreenRoot(
	viewModel: OnboardingViewModel,
	onComplete: () -> Unit,
	onSkip: () -> Unit,
) {

	OnboardingScreen(
		onAction = { action ->
			when (action) {
				is OnboardingAction.OnComplete -> {
					onComplete()
				}

				is OnboardingAction.OnSkip -> {
					onSkip()
				}
			}

			viewModel.onAction(action)

		}
	)
}


@Composable
fun OnboardingScreen(
	onAction: (OnboardingAction) -> Unit,
) {
	val pagerState = rememberPagerState(pageCount = { 3 }) // 3 slides
	val scope = rememberCoroutineScope()

	LaunchedEffect(pagerState.currentPage) {
		pagerState.animateScrollToPage(pagerState.currentPage, animationSpec = tween(1000))
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.White)
	) {

		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(Color.White)
				.navigationBarsPadding(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {

			// Skip Button (conditionally shown, no layout shift when hidden)
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 32.dp, end = 16.dp)
			) {

				Spacer(modifier = Modifier.height(20.dp))

				// Only show Skip button if on the 3rd page (index 2)
				if (pagerState.currentPage < 2) {
					Text(
						text = stringResource(Res.string.btn_skip),
						style = TextStyle(
							fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
							fontSize = 16.sp,
							color = grayFont
						),
						modifier = Modifier
							.clickable { onAction(OnboardingAction.OnSkip) }
							.align(Alignment.TopEnd) // Align to top end of the Box
					)
				}
			}

			// Horizontal Pager for Slides
			HorizontalPager(
				state = pagerState,
				modifier = Modifier.weight(1f)
			) { page ->
				OnboardingPage(page)
			}

			Spacer(modifier = Modifier.height(16.dp))

			// Dot Indicator
			Row(
				horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
			) {
				repeat(3) { index ->
					Box(
						modifier = Modifier
							.width(
								if (index == pagerState.currentPage) 24.dp else 8.dp
							)
							.height(8.dp)
							.clip(CircleShape)
							.background(if (index == pagerState.currentPage) primaryBlue else grayFont)
					)
				}
			}

			Spacer(modifier = Modifier.height(16.dp))

			Box{
				Spacer(modifier = Modifier.height(48.dp))

				Column{
					AnimatedVisibility(
						visible = pagerState.currentPage == 2, // Button visible only on the 3rd page
						enter = fadeIn(animationSpec = tween(durationMillis = 500)), // Fade in animation
						exit = fadeOut(animationSpec = tween(durationMillis = 500))  // Fade out animation
					) {
						GeneralTextButton(
							modifier = Modifier
								.fillMaxWidth()
								.height(48.dp)
								.padding(horizontal = 16.dp),
							text = stringResource(Res.string.btn_get_started),
							textSize = 18,
							textColor = Color.White,
							onClick = {
								onAction(OnboardingAction.OnComplete)
							}
						)
					}
				}
			}


			Spacer(modifier = Modifier.height(8.dp))
		}
	}

}


@Composable
fun OnboardingPage(page: Int) {
	val imageRes = when (page) {
		0 -> painterResource(Res.drawable.iv_splash_1) // Replace with actual drawable
		1 -> painterResource(Res.drawable.iv_splash_2) // Replace with actual drawable
		else -> painterResource(Res.drawable.iv_splash_3) // Replace with actual drawable
	}

	val title = when (page) {
		0 -> stringResource(Res.string.title_ob_1)
		1 -> stringResource(Res.string.title_ob_2)
		else -> stringResource(Res.string.title_ob_3)
	}

	val description = when (page) {
		0 -> stringResource(Res.string.desc_ob_1)
		1 -> stringResource(Res.string.desc_ob_2)
		else -> stringResource(Res.string.desc_ob_3)
	}

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Image(
			painter = imageRes,
			contentDescription = "Onboarding Illustration",
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).size(200.dp)
		)

		Spacer(modifier = Modifier.height(32.dp))

		Text(
			text = title,
			style = TextStyle(
				fontFamily = FontFamily(Font(Res.font.montserrat_medium)),
				fontSize = 18.sp,
				color = fontPrimary
			),
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
			textAlign = TextAlign.Center
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = description,
			style = TextStyle(
				fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
				fontSize = 12.sp,
				color = fontPrimary
			),
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
			textAlign = TextAlign.Center
		)
	}
}
