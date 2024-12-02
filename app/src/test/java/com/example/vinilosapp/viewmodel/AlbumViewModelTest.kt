package com.example.vinilosapp.viewmodel

import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple
import com.example.vinilosapp.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private suspend fun initializeViewModelWithMockAlbums(mockAlbums: List<AlbumSimple>) {
        `when`(albumRepository.fetchAll()).thenReturn(Result.success(mockAlbums))
        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `fetchAllItems should update items when repository succeeds`() = runBlocking {
        val mockAlbumList = listOf(mock(AlbumSimple::class.java), mock(AlbumSimple::class.java))
        initializeViewModelWithMockAlbums(mockAlbumList)

        assertEquals(mockAlbumList, albumViewModel.state.value.filteredItems)
        assertThat(albumViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `fetchAllItems should set errorMessage when repository fails`() = runBlocking {
        `when`(albumRepository.fetchAll()).thenReturn(Result.failure(RuntimeException("API error")))
        albumViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", albumViewModel.state.value.errorMessage)
        assertThat(albumViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `fetchDetailById should update detail when repository succeeds`() = runBlocking {
        val albumId = "1"
        val mockAlbumDetail = mock(AlbumDetail::class.java)
        `when`(albumRepository.fetchById(albumId)).thenReturn(Result.success(mockAlbumDetail))

        albumViewModel.fetchDetailById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockAlbumDetail, albumViewModel.state.value.detail)
        assertThat(albumViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `fetchDetailById should set errorMessage when repository fails`() = runBlocking {
        val albumId = "1"
        `when`(albumRepository.fetchById(albumId)).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.fetchDetailById(albumId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", albumViewModel.state.value.errorMessage)
        assertThat(albumViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `createAlbum should set successMessage when repository succeeds`() = runBlocking {
        val newAlbum = mock(AlbumSimple::class.java).apply { `when`(name).thenReturn("Test Album") }
        `when`(albumRepository.createAlbum(newAlbum)).thenReturn(Result.success(newAlbum))

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Album 'Test Album' created successfully!", albumViewModel.successMessage.value)
        assertThat(albumViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `createAlbum should set errorMessage when repository fails`() = runBlocking {
        val newAlbum = mock(AlbumSimple::class.java)
        `when`(albumRepository.createAlbum(newAlbum)).thenReturn(Result.failure(RuntimeException("API error")))

        albumViewModel.createAlbum(newAlbum)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error creating album: API error", albumViewModel.state.value.errorMessage)
        assertThat(albumViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `filterAlbums should update filteredItems with matching results`() = runBlocking {
        val album1 = mock(AlbumSimple::class.java).apply { `when`(name).thenReturn("Album One") }
        val album2 = mock(AlbumSimple::class.java).apply { `when`(name).thenReturn("Album Two") }
        initializeViewModelWithMockAlbums(listOf(album1, album2))

        albumViewModel.filterAlbums("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(album1), albumViewModel.state.value.filteredItems)
    }

    @Test
    fun `filterAlbums should update filteredItems with empty list when no match is found`() = runBlocking {
        val album1 = mock(AlbumSimple::class.java).apply { `when`(name).thenReturn("Album One") }
        val album2 = mock(AlbumSimple::class.java).apply { `when`(name).thenReturn("Album Two") }
        initializeViewModelWithMockAlbums(listOf(album1, album2))

        albumViewModel.filterAlbums("Non-existing album")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(albumViewModel.state.value.filteredItems.isEmpty())
    }

    @Test
    fun `filterAlbums should update filteredItems with all items for blank query`() = runBlocking {
        val album1 = mock(AlbumSimple::class.java)
        val album2 = mock(AlbumSimple::class.java)
        initializeViewModelWithMockAlbums(listOf(album1, album2))

        albumViewModel.filterAlbums("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(album1, album2), albumViewModel.state.value.filteredItems)
    }
}
