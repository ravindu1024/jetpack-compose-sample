package com.ravindu1024.newsbrowser.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravindu1024.newsbrowser.domain.AppSharedPrefs
import com.ravindu1024.newsbrowser.domain.HeadLinesUseCase
import com.ravindu1024.newsbrowser.ui.state.HeadlinesUiState
import com.ravindu1024.newsbrowser.utils.launchIO
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
class HeadlineViewModel @Inject constructor(
    private val headLinesUseCase: HeadLinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HeadlinesUiState())
    val uiState: StateFlow<HeadlinesUiState> = _uiState

    fun getHeadlines() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launchIO {
            headLinesUseCase.getHeadlines(
                pageNum = _uiState.value.pageNum,
                pageSize = _uiState.value.pageSize
            )
                .catch { error ->
                    error.printStackTrace()
                    _uiState.update {
                        it.copy(error = error)
                    }
                }
                .collect { headlines ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            headlines = headlines
                        )
                    }
                }
        }
    }
}