package com.nandaiqbalh.kmp.bookapp

import androidx.compose.ui.window.ComposeUIViewController
import com.nandaiqbalh.kmp.bookapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
	configure = {
		initKoin()
	}
) {
	App()
}