package com.example.vinilosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilosapp.repository.BaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<SimpleDTO, DetailDTO>(
    protected val repository: BaseRepository<SimpleDTO, DetailDTO>,
) : ViewModel() {

    private val _items = MutableStateFlow<List<SimpleDTO>>(emptyList())
    val items: StateFlow<List<SimpleDTO>> = _items

    private val _filteredItems = MutableStateFlow<List<SimpleDTO>>(emptyList())
    val filteredItems: StateFlow<List<SimpleDTO>> = _filteredItems

    private val _detail = MutableStateFlow<DetailDTO?>(null)
    val detail: StateFlow<DetailDTO?> = _detail

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    protected fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    protected fun setErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    fun fetchAllItems() {
        viewModelScope.launch {
            setLoading(true)
            val result = repository.fetchAll()
            result.onSuccess { itemList ->
                _items.value = itemList
                _filteredItems.value = itemList
            }.onFailure {
                setErrorMessage("Error fetching items: ${it.message}")
            }
            setLoading(false)
        }
    }

    fun fetchDetailById(id: String) {
        viewModelScope.launch {
            setLoading(true)
            val result = repository.fetchById(id)
            result.onSuccess { detailItem ->
                _detail.value = detailItem
            }.onFailure {
                setErrorMessage("Error fetching item details: ${it.message}")
            }
            setLoading(false)
        }
    }

    fun filterItems(query: String, nameSelector: (SimpleDTO) -> String) {
        _filteredItems.value = if (query.isBlank()) {
            _items.value
        } else {
            _items.value.filter { item ->
                nameSelector(item).contains(query, ignoreCase = true)
            }
        }
    }
}
