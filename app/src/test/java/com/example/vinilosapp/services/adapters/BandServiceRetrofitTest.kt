package com.example.vinilosapp.services.adapters

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.services.api.BandAPI
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class BandServiceRetrofitTest {

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var bandAPI: BandAPI

    @InjectMocks
    private lateinit var bandServiceRetrofit: BandServiceRetrofit

    @Before
    fun setUp() {
        `when`(retrofit.create(BandAPI::class.java)).thenReturn(bandAPI)
    }

    @Test
    fun `Given successful response When getAllBands is called Then it should return a list of bands`() = runBlocking {
        val mockBands = listOf(mock(BandSimpleDTO::class.java), mock(BandSimpleDTO::class.java))
        `when`(bandAPI.getBands()).thenReturn(mockBands)

        val result = bandServiceRetrofit.getBands()

        assertTrue(result.isSuccess)
        assertEquals(mockBands, result.getOrNull())
    }

    @Test
    fun `Given an error response When getAllBands is called Then it should return a failure`() = runBlocking {
        val exception = RuntimeException("Network error")
        `when`(bandAPI.getBands()).thenThrow(exception)

        val result = bandServiceRetrofit.getBands()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given successful response When getBandById is called Then it should return band details`() = runBlocking {
        val bandId = "1"
        val mockBandDetail = BandDetailDTO(
            id = BigDecimal(bandId),
            name = "Test Band",
            description = "Description",
            albums = emptyList(),
            musicians = emptyList(),
            image = "",
            creationDate = "",
            collectors = emptyList(),
            performerPrizes = emptyList(),
        )
        `when`(bandAPI.getBandById(bandId)).thenReturn(mockBandDetail)

        val result = bandServiceRetrofit.getBandById(bandId)

        assertTrue(result.isSuccess)
        assertEquals(mockBandDetail, result.getOrNull())
    }

    @Test
    fun `Given an error response When getBandById is called Then it should return a failure`() = runBlocking {
        val bandId = "1"
        val exception = RuntimeException("Network error")
        `when`(bandAPI.getBandById(bandId)).thenThrow(exception)

        val result = bandServiceRetrofit.getBandById(bandId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
