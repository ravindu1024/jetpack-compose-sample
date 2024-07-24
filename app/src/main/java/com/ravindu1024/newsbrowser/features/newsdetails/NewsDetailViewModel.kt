package com.ravindu1024.newsbrowser.features.newsdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravindu1024.domain.model.NewsHeadline
import com.ravindu1024.domain.usecases.SavedHeadlinesUseCase
import com.ravindu1024.newsbrowser.utils.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val savedHeadlinesUSeCase: SavedHeadlinesUseCase
): ViewModel()  {

    private val _uiState = MutableStateFlow(NewsDetailUiState())
    val uiState: StateFlow<NewsDetailUiState> = _uiState

    fun getNewsHeadline(headline: NewsHeadline){

        viewModelScope.launchIO {

            savedHeadlinesUSeCase.getAllSavedHeadlines()
                .collect{ savedHeadlines ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isFavourite = savedHeadlines.any { it.url == headline.url },
                            headline = headline
                        )
                    }
                }
        }
    }

    fun addRemoveFromFavourites(headline: NewsHeadline, add: Boolean){
        viewModelScope.launchIO {
            val flowable = if(add)
                savedHeadlinesUSeCase.addHeadline(headline)
            else
                savedHeadlinesUSeCase.deleteSavedHeadline(headline)

            flowable.collect{
                _uiState.update {
                    it.copy(isFavourite = add)
                }
            }
        }
    }
}