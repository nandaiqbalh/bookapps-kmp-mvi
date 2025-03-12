package com.nandaiqbalh.kmp.bookapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.nandaiqbalh.kmp.bookapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        initKoin {
            androidContext(this@BookApplication)
        }
    }
}