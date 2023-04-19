package org.android.go.sopt.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.android.go.sopt.data.model.music.MusicDataEntity

@Dao
interface MusicDao {
    @Query("SELECT * from music")
    fun getAll(): Flow<List<MusicDataEntity>>

    @Query("SELECT * from music ORDER BY id DESC LIMIT 1")
    fun getLatestMusic(): MusicDataEntity

    @Insert
    fun insert(word: MusicDataEntity)

    @Delete
    fun delete(word: MusicDataEntity)

    @Update
    fun update(word: MusicDataEntity)

}