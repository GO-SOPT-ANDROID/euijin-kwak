package org.android.go.sopt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.android.go.sopt.data.dao.MusicDao
import org.android.go.sopt.data.model.music.MusicDataEntity

@Database(entities = [MusicDataEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun musicDao(): MusicDao
}