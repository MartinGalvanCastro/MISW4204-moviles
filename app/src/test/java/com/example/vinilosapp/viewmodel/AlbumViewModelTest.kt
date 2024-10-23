package com.example.vinilosapp.viewmodel

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
import com.example.vinilosapp.services.AlbumService
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AlbumViewModelTest {

    @Mock
    private lateinit var albumService: AlbumService

    @Mock
    private lateinit var networkChecker: NetworkChecker

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
    fun `Given network is available When fetchAlbums is called Then albums are updated`() = runTest {
        val mockAlbumList = listOf(
            mock(AlbumSimpleDTO::class.java),
            mock(AlbumSimpleDTO::class.java),
        )

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAllAlbums()).thenReturn(mockAlbumList)

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.filteredAlbums.value, `is`(mockAlbumList))
        assertThat(albumViewModel.loading.value, `is`(false))
    }

    @Test
    fun `Given network is not available When fetchAlbums is called Then errorMessage is set`() = runTest {
        `when`(networkChecker.isConnected()).thenReturn(false)

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.errorMessage.value, `is`("No internet connection."))
    }

    @Test
    fun `Given network is available When fetchAlbums fails Then errorMessage is set`() = runTest {
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAllAlbums()).thenThrow(RuntimeException("API error"))

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.loading.value, `is`(false))
        assertThat(albumViewModel.errorMessage.value, `is`("Error fetching albums"))
    }

    @Test
    fun `Given network is available When fetchAlbumById is called Then album is updated`() = runTest {
        val mockAlbum = mock(AlbumDetailDTO::class.java)
        val albumId = 1

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAlbumById(albumId)).thenReturn(mockAlbum)

        albumViewModel.fetchAlbumById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.album.value, `is`(mockAlbum))
        assertThat(albumViewModel.loading.value, `is`(false))
    }

    @Test
    fun `Given network is not available When fetchAlbumById is called Then errorMessage is set`() = runTest {
        val albumId = 1

        `when`(networkChecker.isConnected()).thenReturn(false)

        albumViewModel.fetchAlbumById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.errorMessage.value, `is`("No internet connection."))
        assertThat(albumViewModel.loading.value, `is`(false))
    }

    @Test
    fun `Given network is available When fetchAlbumById fails Then errorMessage is set`() = runTest {
        val albumId = 1

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAlbumById(albumId)).thenThrow(RuntimeException("API error"))

        albumViewModel.fetchAlbumById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.errorMessage.value, `is`("Error fetching album details"))
        assertThat(albumViewModel.loading.value, `is`(false))
    }

    @Test
    fun `Given network is available When createAlbum is called Then successMessage is set`() = runTest {
        val newAlbum = mock(AlbumSimpleDTO::class.java)

        `when`(newAlbum.name).thenReturn("Test Album")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.createAlbum(newAlbum)).thenReturn(newAlbum)

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.successMessage.value, `is`("Album 'Test Album' created successfully!"))
        assertThat(albumViewModel.loading.value, `is`(false))
    }

    @Test
    fun `Given network is not available When createAlbum is called Then errorMessage is set`() = runTest {
        val newAlbum = mock(AlbumSimpleDTO::class.java)

        `when`(networkChecker.isConnected()).thenReturn(false)

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.errorMessage.value, `is`("No internet connection."))
        assertThat(albumViewModel.loading.value, `is`(false))
    }

    @Test
    fun `Given network is available When createAlbum fails Then errorMessage is set`() = runTest {
        val newAlbum = mock(AlbumSimpleDTO::class.java)

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.createAlbum(newAlbum)).thenThrow(RuntimeException("API error"))

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.errorMessage.value, `is`("Error creating album"))
        assertThat(albumViewModel.loading.value, `is`(false))
    }

    @Test
    fun `Given albums exist When filterAlbums is called with matching query Then filteredAlbums is updated`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java).apply {
            `when`(name).thenReturn("Album One")
        }
        val album2 = mock(AlbumSimpleDTO::class.java).apply {
            `when`(name).thenReturn("Album Two")
        }
        val album3 = mock(AlbumSimpleDTO::class.java).apply {
            `when`(name).thenReturn("Album Three")
        }

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAllAlbums()).thenReturn(listOf(album1, album2, album3))

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        albumViewModel.filterAlbums("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.filteredAlbums.value, `is`(listOf(album1)))
    }

    @Test
    fun `Given albums exist When filterAlbums is called with non-matching query Then filteredAlbums is empty`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java).apply {
            `when`(name).thenReturn("Album One")
        }
        val album2 = mock(AlbumSimpleDTO::class.java).apply {
            `when`(name).thenReturn("Album Two")
        }
        val album3 = mock(AlbumSimpleDTO::class.java).apply {
            `when`(name).thenReturn("Album Three")
        }

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAllAlbums()).thenReturn(listOf(album1, album2, album3))

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        albumViewModel.filterAlbums("Non-existing album")
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.filteredAlbums.value, `is`(emptyList()))
    }

    @Test
    fun `Given albums exist When filterAlbums is called with blank query Then all albums are shown`() = runTest {
        val album1 = mock(AlbumSimpleDTO::class.java)
        val album2 = mock(AlbumSimpleDTO::class.java)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAllAlbums()).thenReturn(listOf(album1, album2))

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.filteredAlbums.value.size, `is`(2))

        albumViewModel.filterAlbums("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.filteredAlbums.value, `is`(listOf(album1, album2)))
    }
}
