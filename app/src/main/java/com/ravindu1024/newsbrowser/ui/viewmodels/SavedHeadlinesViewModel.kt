package com.ravindu1024.newsbrowser.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravindu1024.newsbrowser.domain.SavedHeadlinesUseCase
import com.ravindu1024.newsbrowser.ui.state.SavedHeadlinesUiState
import com.ravindu1024.newsbrowser.utils.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SavedHeadlinesViewModel @Inject constructor(
    private val savedHeadlinesUseCase: SavedHeadlinesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(SavedHeadlinesUiState())
    val uiState: StateFlow<SavedHeadlinesUiState> = _uiState


    fun getSavedHeadlines(){
        viewModelScope.launchIO {
            _uiState.update {
                it.copy(isLoading = true)
            }

            savedHeadlinesUseCase.getAllSavedHeadlines()
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                }
                .collect{ headlines ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            savedHeadlines = headlines
                        )
                    }
                }
        }
    }
}