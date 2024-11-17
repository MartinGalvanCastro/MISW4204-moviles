package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilosapp.models.ViewModelState
import com.example.vinilosapp.repository.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<SimpleDTO : Any, DetailDTO : Any>(
    protected val repository: BaseRepository<SimpleDTO, DetailDTO>,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel() {

    protected val _state = MutableStateFlow(ViewModelState<SimpleDTO, DetailDTO>(isLoading = true))
    val state: StateFlow<ViewModelState<SimpleDTO, DetailDTO>> = _state

    fun fetchAllItems() {
        viewModelScope.launch(ioDispatcher) {
            _state.value = _state.value.copy(isLoading = true)
            val result = repository.fetchAll()
            result.onSuccess { itemList ->
                _state.value = _state.value.copy(
                    items = itemList,
                    filteredItems = itemList,
                    isLoading = false,
                )
            }.onFailure {
                _state.value = _state.value.copy(
                    errorMessage = it.message,
                    isLoading = false,
                )
            }
        }
    }

    fun fetchDetailById(id: String) {
        viewModelScope.launch(ioDispatcher) {
            _state.value = _state.value.copy(isLoading = true)
            val result = repository.fetchById(id)
            result.onSuccess { detailItem ->
                _state.value = _state.value.copy(
                    detail = detailItem,
                    isLoading = false,
                )
            }.onFailure {
                _state.value = _state.value.copy(
                    errorMessage = it.message,
                    isLoading = false,
                )
            }
        }
    }

    fun filterItems(query: String, nameSelector: (SimpleDTO) -> String) {
        viewModelScope.launch(defaultDispatcher) {
            val filtered = if (query.isBlank()) {
                _state.value.items
            } else {
                _state.value.items.filter { nameSelector(it).contains(query, ignoreCase = true) }
            }
            _state.value = _state.value.copy(filteredItems = filtered)
        }
    }
}
