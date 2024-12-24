package com.nandaiqbalh.kmp.bookapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform