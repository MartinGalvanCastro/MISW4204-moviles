package com.example.vinilosapp.viewmodel

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AlbumViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var albumRepository: AlbumRepository

    private lateinit var albumViewModel: AlbumViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)

        albumViewModel = AlbumViewModel(
            albumRepository = albumRepository,
            ioDispatcher = testDispatcher,
            defaultDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private suspend fun initializeViewModelWithMockAlbums(mockAlbums: List<AlbumSimpleDTO>) {
        `when`(albumRepository.fetchAll()).thenReturn(Result.success(mockAlbums))
        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `Given successful repository response When fetchAllItems is called Then items are updated`() = runBlocking {
        val mockAlbumList = listOf(mock(AlbumSimpleDTO::class.java), mock(AlbumSimpleDTO::class.java))
        initializeViewModelWithMockAlbums(mockAlbumList)
        assertEquals(mockAlbumList, albumViewModel.filteredItems.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAllItems is called Then errorMessage is set`() = runBlocking {
        `when`(albumRepository.fetchAll()).thenReturn(Result.failure(RuntimeException("API error")))
        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals("Error fetching items: API error", albumViewModel.errorMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchDetailById is called Then detail is updated`() = runBlocking {
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
    fun `Given repository failure When fetchDetailById is called Then errorMessage is set`() = runBlocking {
        val albumId = "1"
        `when`(albumRepository.fetchById(albumId)).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.fetchDetailById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching item details: API error", albumViewModel.errorMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When createAlbum is called Then successMessage is set`() = runBlocking {
        val newAlbum = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Test Album") }
        `when`(albumRepository.createAlbum(newAlbum)).thenReturn(Result.success(newAlbum))

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Album 'Test Album' created successfully!", albumViewModel.successMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When createAlbum is called Then errorMessage is set`() = runBlocking {
        val newAlbum = mock(AlbumSimpleDTO::class.java)
        `when`(albumRepository.createAlbum(newAlbum)).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error creating album", albumViewModel.errorMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given albums exist When filterAlbums is called with matching query Then filteredItems is updated`() = runBlocking {
        val album1 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album One") }
        val album2 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Two") }
        initializeViewModelWithMockAlbums(listOf(album1, album2))

        albumViewModel.filterAlbums("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(album1), albumViewModel.filteredItems.first())
    }

    @Test
    fun `Given albums exist When filterAlbums is called with non-matching query Then filteredItems is empty`() = runBlocking {
        val album1 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album One") }
        val album2 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Two") }
        initializeViewModelWithMockAlbums(listOf(album1, album2))

        albumViewModel.filterAlbums("Non-existing album")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(albumViewModel.filteredItems.first().isEmpty())
    }

    @Test
    fun `Given albums exist When filterAlbums is called with blank query Then all items are shown`() = runBlocking {
        val album1 = mock(AlbumSimpleDTO::class.java)
        val album2 = mock(AlbumSimpleDTO::class.java)
        initializeViewModelWithMockAlbums(listOf(album1, album2))

        albumViewModel.filterAlbums("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(album1, album2), albumViewModel.filteredItems.first())
    }
}
