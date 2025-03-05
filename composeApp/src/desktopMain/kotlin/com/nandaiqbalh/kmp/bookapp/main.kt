package com.nandaiqbalh.kmp.bookapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.nandaiqbalh.kmp.bookapp.app.App
import com.nandaiqbalh.kmp.bookapp.di.initKoin

fun main(){
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "BookApps-KMP",
        ) {
            App()
        }
    }
}