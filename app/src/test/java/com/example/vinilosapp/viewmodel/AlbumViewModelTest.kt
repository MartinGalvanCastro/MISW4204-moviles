import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.repository.AlbumRepository
import com.example.vinilosapp.viewmodel.AlbumViewModel
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
class AlbumViewModelTest {

    @Mock
    private lateinit var albumRepository: AlbumRepository

    @InjectMocks
    private lateinit var albumViewModel: AlbumViewModel

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
        val mockAlbumList = listOf(
            mock(AlbumSimpleDTO::class.java),
            mock(AlbumSimpleDTO::class.java),
        )
        `when`(albumRepository.fetchAll()).thenReturn(Result.success(mockAlbumList))

        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockAlbumList, albumViewModel.filteredItems.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAllItems is called Then errorMessage is set`() = runTest {
        `when`(albumRepository.fetchAll()).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching items: API error", albumViewModel.errorMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchDetailById is called Then detail is updated`() = runTest {
        val albumId = "1"
        val mockAlbumDetail = AlbumDetailDTO(
            id = BigDecimal(1),
            name = "Test Album",
            cover = "Cover URL",
            releaseDate = "2022-01-01",
            description = "Description",
            performers = emptyList(),
            tracks = emptyList(),
            comments = emptyList(),
        )
        `when`(albumRepository.fetchById(albumId)).thenReturn(Result.success(mockAlbumDetail))

        albumViewModel.fetchDetailById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockAlbumDetail, albumViewModel.detail.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchDetailById is called Then errorMessage is set`() = runTest {
        val albumId = "1"
        `when`(albumRepository.fetchById(albumId)).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.fetchDetailById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching item details: API error", albumViewModel.errorMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When createAlbum is called Then successMessage is set`() = runTest {
        val newAlbum = mock(AlbumSimpleDTO::class.java)
        `when`(newAlbum.name).thenReturn("Test Album")
        `when`(albumRepository.createAlbum(newAlbum)).thenReturn(Result.success(newAlbum))

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Album 'Test Album' created successfully!", albumViewModel.successMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When createAlbum is called Then errorMessage is set`() = runTest {
        val newAlbum = mock(AlbumSimpleDTO::class.java)
        `when`(albumRepository.createAlbum(newAlbum)).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error creating album", albumViewModel.errorMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given albums exist When filterAlbums is called with matching query Then filteredItems is updated`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album One") }
        val album2 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Two") }
        val album3 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Three") }

        `when`(albumRepository.fetchAll()).thenReturn(Result.success(listOf(album1, album2, album3)))
        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        albumViewModel.filterAlbums("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(album1), albumViewModel.filteredItems.first())
    }

    @Test
    fun `Given albums exist When filterAlbums is called with non-matching query Then filteredItems is empty`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album One") }
        val album2 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Two") }
        val album3 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Three") }

        `when`(albumRepository.fetchAll()).thenReturn(Result.success(listOf(album1, album2, album3)))
        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        albumViewModel.filterAlbums("Non-existing album")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(albumViewModel.filteredItems.first().isEmpty())
    }

    @Test
    fun `Given albums exist When filterAlbums is called with blank query Then all items are shown`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java)
        val album2 = mock(AlbumSimpleDTO::class.java)
        `when`(albumRepository.fetchAll()).thenReturn(Result.success(listOf(album1, album2)))

        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        albumViewModel.filterAlbums("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(album1, album2), albumViewModel.filteredItems.first())
    }
}
