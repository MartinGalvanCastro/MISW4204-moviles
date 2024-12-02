package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilosapp.models.ViewModelState
import com.example.vinilosapp.repository.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<SimpleDTO : Any, DetailDTO : Any>(
    protected val repository: BaseRepository<SimpleDTO, DetailDTO>,
    protected var ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    protected var defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    protected var mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
) : ViewModel() {

    protected val _state: MutableStateFlow<ViewModelState<SimpleDTO, DetailDTO>> by lazy {
        MutableStateFlow(ViewModelState(isLoading = true))
    }
    val state: StateFlow<ViewModelState<SimpleDTO, DetailDTO>> = _state

    protected suspend fun updateState(
        isLoading: Boolean? = null,
        items: List<SimpleDTO>? = null,
        filteredItems: List<SimpleDTO>? = null,
        detail: DetailDTO? = null,
        errorMessage: String? = null,
    ) {
        withContext(mainDispatcher) {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = isLoading ?: currentState.isLoading,
                    items = items ?: currentState.items,
                    filteredItems = filteredItems ?: currentState.filteredItems,
                    detail = detail ?: currentState.detail,
                    errorMessage = errorMessage ?: currentState.errorMessage,
                )
            }
        }
    }

    fun fetchAllItems() {
        viewModelScope.launch(ioDispatcher) {
            updateState(isLoading = true)
            val result = repository.fetchAll()
            result.onSuccess { itemList ->
                updateState(
                    items = itemList,
                    filteredItems = itemList,
                    isLoading = false,
                )
            }.onFailure { error ->
                updateState(
                    errorMessage = error.message,
                    isLoading = false,
                )
            }
        }
    }

    fun fetchDetailById(id: String) {
        viewModelScope.launch(ioDispatcher) {
            updateState(isLoading = true)
            val result = repository.fetchById(id)
            result.onSuccess { detailItem ->
                updateState(
                    detail = detailItem,
                    isLoading = false,
                )
            }.onFailure { error ->
                updateState(
                    errorMessage = error.message,
                    isLoading = false,
                )
            }
        }
    }

    suspend fun filterItems(query: String, nameSelector: (SimpleDTO) -> String) {
        val filtered = if (query.isBlank()) {
            _state.value.items
        } else {
            _state.value.items.filter { nameSelector(it).contains(query, ignoreCase = true) }
        }
        updateState(filteredItems = filtered)
    }
}
