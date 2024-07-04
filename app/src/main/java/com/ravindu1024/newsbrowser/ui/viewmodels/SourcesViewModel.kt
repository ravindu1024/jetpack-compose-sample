package com.ravindu1024.newsbrowser.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravindu1024.domain.usecases.SourcesUseCase
import com.ravindu1024.newsbrowser.ui.state.SourcesListUiState
import com.ravindu1024.newsbrowser.utils.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val sourcesUseCase: SourcesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(SourcesListUiState())
    val uiState: StateFlow<SourcesListUiState> = _uiState

    fun getSources(){
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launchIO {

            sourcesUseCase.getAllSavedSources()
                .flatMapConcat { saved ->
                    _uiState.update {
                        it.copy(savedSources = saved)
                    }
                    sourcesUseCase.getAllLocalSources()
                }
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
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            if(add){
                sourcesUseCase.addSavedSource(source)
            }else{
                sourcesUseCase.deleteSavedSource(source)
            }.collect{ updatedSavedSources ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        savedSources = updatedSavedSources
                    )
                }
            }
        }
    }
}