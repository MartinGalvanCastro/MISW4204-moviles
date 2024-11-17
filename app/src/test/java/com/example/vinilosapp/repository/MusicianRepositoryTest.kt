package com.example.vinilosapp.repository

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.services.adapters.MusicianServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MusicianRepositoryTest {

    @Mock
    private lateinit var musicianServiceAdapter: MusicianServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @Mock
    private lateinit var cache: Cache

    @InjectMocks
    private lateinit var musicianRepository: MusicianRepository

    @Test
    fun fetchMusiciansWithCachedData() = runTest {
        val cacheKey = "list-MusicianSimpleDTO"
        val mockMusicians = listOf(mock(MusicianSimpleDTO::class.java), mock(MusicianSimpleDTO::class.java))
        `when`(cache.getList<MusicianSimpleDTO>(cacheKey)).thenReturn(mockMusicians)

        val result = musicianRepository.fetchAll()

        assertTrue(result.isSuccess)
        assertEquals(mockMusicians, result.getOrNull())
        verify(musicianServiceAdapter, never()).getMusicians()
    }

    @Test
    fun fetchMusiciansWithoutCachedData() = runTest {
        val cacheKey = "list-MusicianSimpleDTO"
        val mockMusicians = listOf(mock(MusicianSimpleDTO::class.java), mock(MusicianSimpleDTO::class.java))
        `when`(cache.getList<MusicianSimpleDTO>(cacheKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(musicianServiceAdapter.getMusicians()).thenReturn(Result.success(mockMusicians))

        val result = musicianRepository.fetchAll()

        assertTrue(result.isSuccess)
        assertEquals(mockMusicians, result.getOrNull())
        verify(musicianServiceAdapter).getMusicians()
        verify(cache).putList(cacheKey, mockMusicians)
    }

    @Test
    fun fetchMusiciansWithoutNetwork() = runBlocking {
        val cacheKey = "list-MusicianSimpleDTO"
        `when`(cache.getList<MusicianSimpleDTO>(cacheKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = musicianRepository.fetchAll()

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun fetchMusicianByIdWithCachedData() = runTest {
        val musicianId = "1"
        val cacheKey = "detail-MusicianDetailDTO-$musicianId"
        val mockMusicianDetail = mock(MusicianDetailDTO::class.java)
        `when`(cache.getDetail<MusicianDetailDTO>(cacheKey)).thenReturn(mockMusicianDetail)

        val result = musicianRepository.fetchById(musicianId)

        assertTrue(result.isSuccess)
        assertEquals(mockMusicianDetail, result.getOrNull())
        verify(musicianServiceAdapter, never()).getMusicianById(musicianId)
    }

    @Test
    fun fetchMusicianByIdWithoutCachedData() = runTest {
        val musicianId = "1"
        val cacheKey = "detail-MusicianDetailDTO-$musicianId"
        val mockMusicianDetail = mock(MusicianDetailDTO::class.java)
        `when`(cache.getDetail<MusicianDetailDTO>(cacheKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(musicianServiceAdapter.getMusicianById(musicianId)).thenReturn(Result.success(mockMusicianDetail))

        val result = musicianRepository.fetchById(musicianId)

        assertTrue(result.isSuccess)
        assertEquals(mockMusicianDetail, result.getOrNull())
        verify(musicianServiceAdapter).getMusicianById(musicianId)
        verify(cache).putDetail(cacheKey, mockMusicianDetail)
    }

    @Test
    fun fetchMusicianByIdWithoutNetwork() = runBlocking {
        val musicianId = "1"
        val cacheKey = "detail-MusicianDetailDTO-$musicianId"
        `when`(cache.getDetail<MusicianDetailDTO>(cacheKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = musicianRepository.fetchById(musicianId)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }
}
