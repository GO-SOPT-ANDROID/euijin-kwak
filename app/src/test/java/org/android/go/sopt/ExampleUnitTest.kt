package org.android.go.sopt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.repository.MusicRepository
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val musicRepository: MusicRepository = TestMusicRepositoryImpl()
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    
    fun getMusicList() {
        CoroutineScope(Dispatchers.IO).launch {
            musicRepository.getMusicList("test").onSuccess {
                assertEquals(it.message, "API SUCCESS")
                this.cancel()
            }.onFailure {
                this.cancel()
            }
        }
    }
}