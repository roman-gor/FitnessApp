package com.gorman.fitnessapp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.Article
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.usecases.GetArticlesUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramIdUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserFromLocalUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logger: AppLogger,
    private val getProgramIdUseCase: GetProgramIdUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase
): ViewModel() {
    private val _programExistingState = mutableStateOf(false)
    val programExistingState: State<Boolean> = _programExistingState

    private val _articleListState = mutableStateOf<List<Article>>(emptyList())
    val articleListState: State<List<Article>> = _articleListState

    private val _userDataState = mutableStateOf<UsersData?>(null)
    val userDataState: State<UsersData?> = _userDataState

    private val _homeUiState = mutableStateOf<HomeUiState>(HomeUiState.Idle)
    val homeUiState: State<HomeUiState> = _homeUiState

    fun prepareData() {
        viewModelScope.launch {
            try {
                _articleListState.value = getArticlesUseCase()
                _programExistingState.value = getProgramIdUseCase().isNotEmpty()
                _userDataState.value = getUserFromLocalUseCase()
                _homeUiState.value = HomeUiState.Success
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