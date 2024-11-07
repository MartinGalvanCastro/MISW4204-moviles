package com.example.vinilosapp.services.adapters

import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.services.api.PremioAPI
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

@RunWith(MockitoJUnitRunner::class)
class PremioServiceRetrofitTest {

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var premioAPI: PremioAPI

    @InjectMocks
    private lateinit var premioServiceRetrofit: PremioServiceRetrofit

    private val prizeId = "1"

    @Before
    fun setUp() {
        `when`(retrofit.create(PremioAPI::class.java)).thenReturn(premioAPI)
    }

    @Test
    fun `Given successful response When getPremioById is called Then it should return prize data`() = runBlocking {
        val mockPrize = mock(PrizeDetailDTO::class.java)
        `when`(premioAPI.getPremioById(prizeId)).thenReturn(mockPrize)

        val result = premioServiceRetrofit.getPremioById(prizeId)

        assertTrue(result.isSuccess)
        assertEquals(mockPrize, result.getOrNull())
    }

    @Test
    fun `Given network exception When getPremioById is called Then it should return failure`() = runBlocking {
        val exception = RuntimeException("Network error")
        `when`(premioAPI.getPremioById(prizeId)).thenThrow(exception)

        val result = premioServiceRetrofit.getPremioById(prizeId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
