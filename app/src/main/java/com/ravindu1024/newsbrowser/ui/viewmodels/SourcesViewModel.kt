package com.ravindu1024.newsbrowser.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravindu1024.newsbrowser.domain.AppSharedPrefs
import com.ravindu1024.newsbrowser.domain.SourcesUseCase
import com.ravindu1024.newsbrowser.ui.state.SourcesListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val sourcesUseCase: SourcesUseCase,
    private val sharedPrefs: AppSharedPrefs
): ViewModel() {

    private val _uiState = MutableStateFlow(SourcesListUiState())
    val uiState: StateFlow<SourcesListUiState> = _uiState

    init {
        _uiState.update {
            it.copy(savedSources = sharedPrefs.getSources())
        }
    }

    fun getSources(){
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            sourcesUseCase.getAllLocalSources()
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error)
                    }
                }
                .collect{ sources ->
                    _uiState.update {
                        it.copy(isLoading = false, sources = sources, error = null)
                    }
                }
        }
    }

    fun updateSource(source: String, add: Boolean){
        val savedSources = sharedPrefs.getSources().toMutableList()
        if(add && !savedSources.contains(source)){
            savedSources.add(source)
            sharedPrefs.saveSources(savedSources)
        }

        if(!add){
            savedSources.remove(source)
            sharedPrefs.saveSources(savedSources)
        }

        _uiState.update {
            it.copy(savedSources = savedSources)
        }
    }
}