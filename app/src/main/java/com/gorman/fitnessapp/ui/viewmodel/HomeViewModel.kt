package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.Article
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.usecases.GetAndSyncUserProgramsUseCase
import com.gorman.fitnessapp.domain.usecases.GetArticlesUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramFromLocalUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramIdUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserFromLocalUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.ArticlesState
import com.gorman.fitnessapp.ui.states.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logger: AppLogger,
    private val getProgramIdUseCase: GetProgramIdUseCase,
    private val getProgramFromLocalUseCase: GetProgramFromLocalUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase
): ViewModel() {
    private val _programExistingState = MutableStateFlow(false)
    val programExistingState: StateFlow<Boolean> = _programExistingState

    private val _articleListState = MutableStateFlow<List<Article>>(emptyList())
    val articleListState: StateFlow<List<Article>> = _articleListState

    private val _userDataState = MutableStateFlow<UsersData?>(null)
    val userDataState: StateFlow<UsersData?> = _userDataState

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _articlesUiState = MutableStateFlow<ArticlesState>(ArticlesState.Loading)
    val articlesUiState: StateFlow<ArticlesState> = _articlesUiState

    private val _programDescriptionState = MutableStateFlow("")
    val programDescriptionState: StateFlow<String> = _programDescriptionState.asStateFlow()

    fun prepareData() {
        _homeUiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val articlesDeferred = async { getArticlesUseCase() }
                _programExistingState.value = getProgramIdUseCase().isNotEmpty()
                _userDataState.value = getUserFromLocalUseCase()
                _homeUiState.value = HomeUiState.Success
                if (getProgramIdUseCase().isNotEmpty())
                    _programDescriptionState.value = getProgramFromLocalUseCase().keys.first().name
                try {
                    _articleListState.value = articlesDeferred.await()
                    _articlesUiState.value = ArticlesState.Success
                } catch (e: Exception) {
                    _articlesUiState.value = ArticlesState.Error(e.message ?: "Ошибка загрузки статей")
                }
                logger.d("PROGRAM ID", getProgramIdUseCase())
            } catch (e: IllegalStateException) {
                logger.e("HOME", "Ошибка загрузки статей: ${e.message}")
                _homeUiState.value = HomeUiState.Error(e.message ?: "Ошибка загрузки статей")
            } catch (e: Exception) {
                logger.e("HOME", "Неожиданная ошибка: ${e.message}")
                _homeUiState.value = HomeUiState.Error(e.message ?: "Неожиданная ошибка")
            }
        }
    }
}