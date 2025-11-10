package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.gorman.fitnessapp.domain.usecases.GetProgramIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getProgramIdUseCase: GetProgramIdUseCase
): ViewModel() {

}