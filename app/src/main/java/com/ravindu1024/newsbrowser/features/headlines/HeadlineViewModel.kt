package com.ravindu1024.newsbrowser.features.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravindu1024.domain.usecases.HeadLinesUseCase
import com.ravindu1024.newsbrowser.utils.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HeadlineViewModel @Inject constructor(
    private val headLinesUseCase: HeadLinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HeadlinesUiState())
    val uiState: StateFlow<HeadlinesUiState> = _uiState

    fun getHeadlines(nextPage: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = true,
                pageNum = if(nextPage) it.pageNum + 1 else 1
            )
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
                    val h = if(nextPage){
                        _uiState.value.headlines + headlines
                    }else{
                        headlines
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            headlines = h
                        )
                    }
                }
        }
    }
}