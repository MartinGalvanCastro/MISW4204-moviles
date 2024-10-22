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

        assertThat(albumViewModel.albums.value, `is`(mockAlbumList))
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
    fun `Given network is available When fetchAlbums fails Then loading is set to true and false correctly`() = runTest {
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAllAlbums()).thenThrow(RuntimeException("API error"))

        albumViewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.loading.value, `is`(false))
        assertThat(albumViewModel.errorMessage.value, `is`("Error fetching albums"))
    }

    @Test
    fun `Given network is available When fetchAlbumById is called Then album is updated and loading is false`() = runTest {
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
    fun `Given network is available When fetchAlbumById throws an exception Then errorMessage is set`() = runTest {
        val albumId = 1

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.getAlbumById(albumId)).thenThrow(Exception("API error"))

        albumViewModel.fetchAlbumById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.errorMessage.value, `is`("Error fetching album details"))
        assertThat(albumViewModel.loading.value, `is`(false))
    }

    @Test
    fun `Given network is available When createAlbum is called Then successMessage is set and loading is false`() = runTest {
        val newAlbum = mock(AlbumSimpleDTO::class.java)
        val createdAlbum = mock(AlbumSimpleDTO::class.java)

        `when`(newAlbum.name).thenReturn("Test Album")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.createAlbum(newAlbum)).thenReturn(createdAlbum)

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
    fun `Given network is available When createAlbum throws an exception Then errorMessage is set`() = runTest {
        val newAlbum = mock(AlbumSimpleDTO::class.java)

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumService.createAlbum(newAlbum)).thenThrow(Exception("API error"))

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(albumViewModel.errorMessage.value, `is`("Error creating album"))
        assertThat(albumViewModel.loading.value, `is`(false))
    }
}
