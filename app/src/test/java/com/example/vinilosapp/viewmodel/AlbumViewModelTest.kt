package com.example.vinilosapp.viewmodel

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.repository.AlbumRepository
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
    fun `Given successful repository response When fetchAlbums is called Then albums are updated`() = runTest {
        val mockAlbumList = listOf(
            mock(AlbumSimpleDTO::class.java),
            mock(AlbumSimpleDTO::class.java),
        )
        `when`(albumRepository.fetchAlbums()).thenReturn(Result.success(mockAlbumList))

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockAlbumList, albumViewModel.filteredAlbums.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAlbums is called Then errorMessage is set`() = runTest {
        `when`(albumRepository.fetchAlbums()).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching albums", albumViewModel.errorMessage.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchAlbumById is called Then album is updated`() = runTest {
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
        `when`(albumRepository.fetchAlbumById(albumId)).thenReturn(Result.success(mockAlbumDetail))

        albumViewModel.fetchAlbumById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockAlbumDetail, albumViewModel.album.first())
        assertThat(albumViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAlbumById is called Then errorMessage is set`() = runTest {
        val albumId = "1"
        `when`(albumRepository.fetchAlbumById(albumId)).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.fetchAlbumById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching album details", albumViewModel.errorMessage.first())
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
    fun `Given albums exist When filterAlbums is called with matching query Then filteredAlbums is updated`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album One") }
        val album2 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Two") }
        val album3 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Three") }

        `when`(albumRepository.fetchAlbums()).thenReturn(Result.success(listOf(album1, album2, album3)))
        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        albumViewModel.filterAlbums("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(album1), albumViewModel.filteredAlbums.first())
    }

    @Test
    fun `Given albums exist When filterAlbums is called with non-matching query Then filteredAlbums is empty`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album One") }
        val album2 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Two") }
        val album3 = mock(AlbumSimpleDTO::class.java).apply { `when`(name).thenReturn("Album Three") }

        `when`(albumRepository.fetchAlbums()).thenReturn(Result.success(listOf(album1, album2, album3)))
        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        albumViewModel.filterAlbums("Non-existing album")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(albumViewModel.filteredAlbums.first().isEmpty())
    }

    @Test
    fun `Given albums exist When filterAlbums is called with blank query Then all albums are shown`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java)
        val album2 = mock(AlbumSimpleDTO::class.java)
        `when`(albumRepository.fetchAlbums()).thenReturn(Result.success(listOf(album1, album2)))

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        albumViewModel.filterAlbums("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(album1, album2), albumViewModel.filteredAlbums.first())
    }
}
