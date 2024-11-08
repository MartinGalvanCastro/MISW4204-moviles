package com.example.vinilosapp.services.adapters

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.services.api.ColeccionistaAPI
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
class ColeccionistaServiceRetrofitTest {

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var coleccionistaAPI: ColeccionistaAPI

    @InjectMocks
    private lateinit var coleccionistaServiceRetrofit: ColecionistaServiceRetrofit

    @Before
    fun setUp() {
        `when`(retrofit.create(ColeccionistaAPI::class.java)).thenReturn(coleccionistaAPI)
    }

    @Test
    fun `Given successful response When getColeccionistas is called Then it should return a list of collectors`() = runBlocking {
        val mockCollectors = listOf(mock(CollectorSimpleDTO::class.java), mock(CollectorSimpleDTO::class.java))
        `when`(coleccionistaAPI.getCollectors()).thenReturn(mockCollectors)

        val result = coleccionistaServiceRetrofit.getColecionistas()

        assertTrue(result.isSuccess)
        assertEquals(mockCollectors, result.getOrNull())
    }

    @Test
    fun `Given an error response When getColeccionistas is called Then it should return a failure`() = runBlocking {
        val exception = RuntimeException("Network error")
        `when`(coleccionistaAPI.getCollectors()).thenThrow(exception)

        val result = coleccionistaServiceRetrofit.getColecionistas()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given successful response When getColeccionistaById is called Then it should return collector details`() = runBlocking {
        val collectorId = "1"
        val mockCollectorDetail = CollectorDetailDTO(
            id = BigDecimal(collectorId),
            name = "Test Collector",
            email = "collector@test.com",
            telephone = "123456789",
            favoritePerformers = emptyList(),
            comments = emptyList(),
            collectorAlbums = emptyList(),
        )
        `when`(coleccionistaAPI.getCollectors(collectorId)).thenReturn(mockCollectorDetail)

        val result = coleccionistaServiceRetrofit.getColecionistaById(collectorId)

        assertTrue(result.isSuccess)
        assertEquals(mockCollectorDetail, result.getOrNull())
    }

    @Test
    fun `Given an error response When getColeccionistaById is called Then it should return a failure`() = runBlocking {
        val collectorId = "1"
        val exception = RuntimeException("Network error")
        `when`(coleccionistaAPI.getCollectors(collectorId)).thenThrow(exception)

        val result = coleccionistaServiceRetrofit.getColecionistaById(collectorId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
