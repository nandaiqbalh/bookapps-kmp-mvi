package com.nandaiqbalh.kmp.bookapp.book_feature.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
	fun create(): RoomDatabase.Builder<FavoriteBookDatabase>
}