import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.repository.BandRepository
import com.example.vinilosapp.repository.PrizeRepository
import com.example.vinilosapp.viewmodel.BandViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
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
import java.math.BigDecimal

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BandViewModelTest {

    @Mock
    private lateinit var bandRepository: BandRepository

    @Mock
    private lateinit var prizeRepository: PrizeRepository

    @InjectMocks
    private lateinit var bandViewModel: BandViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given successful repository response When fetchAllItems is called Then items are updated`() = runTest {
        val mockBandList = listOf(mock(BandSimpleDTO::class.java), mock(BandSimpleDTO::class.java))
        `when`(bandRepository.fetchAll()).thenReturn(Result.success(mockBandList))

        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockBandList, bandViewModel.filteredItems.first())
        assertThat(bandViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAllItems is called Then errorMessage is set`() = runTest {
        `when`(bandRepository.fetchAll()).thenReturn(Result.failure(RuntimeException("API error")))

        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching items: API error", bandViewModel.errorMessage.first())
        assertThat(bandViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchDetailById is called Then detail is updated`() = runTest {
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
        `when`(bandRepository.fetchById(bandId)).thenReturn(Result.success(mockBandDetail))

        bandViewModel.fetchDetailById(bandId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockBandDetail, bandViewModel.detail.first())
        assertThat(bandViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchDetailById is called Then errorMessage is set`() = runTest {
        val bandId = "1"
        `when`(bandRepository.fetchById(bandId)).thenReturn(Result.failure(RuntimeException("API error")))

        bandViewModel.fetchDetailById(bandId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching item details: API error", bandViewModel.errorMessage.first())
        assertThat(bandViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given bands exist When filterBands is called with matching query Then filteredItems is updated`() = runTest {
        val band1 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band One") }
        val band2 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Two") }
        val band3 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Three") }

        `when`(bandRepository.fetchAll()).thenReturn(Result.success(listOf(band1, band2, band3)))
        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(band1), bandViewModel.filteredItems.first())
    }

    @Test
    fun `Given bands exist When filterBands is called with non-matching query Then filteredItems is empty`() = runTest {
        val band1 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band One") }
        val band2 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Two") }
        val band3 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Three") }

        `when`(bandRepository.fetchAll()).thenReturn(Result.success(listOf(band1, band2, band3)))
        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("Non-existing band")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(bandViewModel.filteredItems.first().isEmpty())
    }

    @Test
    fun `Given bands exist When filterBands is called with blank query Then all items are shown`() = runTest {
        val band1 = mock(BandSimpleDTO::class.java)
        val band2 = mock(BandSimpleDTO::class.java)
        `when`(bandRepository.fetchAll()).thenReturn(Result.success(listOf(band1, band2)))

        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(band1, band2), bandViewModel.filteredItems.first())
    }

    @Test
    fun `Given successful prize fetch When fetchPrizes is called Then prizes are updated`() = runTest {
        val prizeId = "1"
        val mockPrize = mock(PrizeDetailDTO::class.java)
        `when`(prizeRepository.fetchPrizes(listOf(prizeId))).thenReturn(listOf(mockPrize))

        bandViewModel.fetchPrizes(listOf(prizeId))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(mockPrize), bandViewModel.prizes.first())
    }
}
