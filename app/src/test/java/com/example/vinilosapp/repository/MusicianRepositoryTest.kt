package com.example.vinilosapp.repository

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.vinilosapp.services.adapters.MusicianServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class MusicianRepositoryTest {

    @Mock
    private lateinit var musicianServiceAdapter: MusicianServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @InjectMocks
    private lateinit var musicianRepository: MusicianRepository

    @Test
    fun `Given network is connected and service success When fetchMusicians is called Then it should return a list of musicians`() = runBlocking {
        val mockMusicians = listOf(mock(MusicianSimpleDTO::class.java), mock(MusicianSimpleDTO::class.java))
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(musicianServiceAdapter.getMusicians()).thenReturn(Result.success(mockMusicians))

        val result = musicianRepository.fetchAll()

        assertTrue(result.isSuccess)
        assertEquals(mockMusicians, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When fetchMusicians is called Then it should return failure`() = runBlocking {
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(musicianServiceAdapter.getMusicians()).thenReturn(Result.failure(exception))

        val result = musicianRepository.fetchAll()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When fetchMusicians is called Then it should return network failure`() = runBlocking {
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = musicianRepository.fetchAll()

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun `Given network is connected and service success When fetchById is called Then it should return musician details`() = runBlocking {
        val musicianId = "1"
        val mockMusicianDetail = MusicianDetailDTO(
            id = BigDecimal(musicianId),
            name = "Test Musician",
            description = "Description",
            image = "",
            birthDate = "",
            albums = emptyList(),
            collectors = emptyList(),
            performerPrizes = emptyList(),
        )
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(musicianServiceAdapter.getMusicianById(musicianId)).thenReturn(Result.success(mockMusicianDetail))

        val result = musicianRepository.fetchById(musicianId)

        assertTrue(result.isSuccess)
        assertEquals(mockMusicianDetail, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When fetchById is called Then it should return failure`() = runBlocking {
        val musicianId = "1"
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(musicianServiceAdapter.getMusicianById(musicianId)).thenReturn(Result.failure(exception))

        val result = musicianRepository.fetchById(musicianId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When fetchById is called Then it should return network failure`() = runBlocking {
        val musicianId = "1"
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = musicianRepository.fetchById(musicianId)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }
}
