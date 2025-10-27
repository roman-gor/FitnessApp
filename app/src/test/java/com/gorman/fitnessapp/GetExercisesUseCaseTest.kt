package com.gorman.fitnessapp

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.mockito.kotlin.never

@ExperimentalCoroutinesApi
class GetExercisesUseCaseTest {

    private lateinit var getExercisesUseCase: GetExercisesUseCase
    private val databaseRepository: DatabaseRepository = mock()
    private val supabaseRepository: SupabaseRepository = mock()

    private val cachedExercises = listOf(Exercise(1, "Push Up", "", "", "", 1, ""))
    private val remoteExercises = listOf(
        Exercise(1, "Push Up", "", "", "", 1, ""),
        Exercise(2, "Squat", "", "", "", 1, "")
    )

    @Before
    fun setUp() {
        getExercisesUseCase = GetExercisesUseCase(databaseRepository, supabaseRepository)
    }

    @Test
    fun `invoke() with non-empty cache should return cached data and not fetch from remote`() = runTest {
        // Arrange
        whenever(databaseRepository.getExercises()).thenReturn(cachedExercises)

        // Act
        val result = getExercisesUseCase()

        // Assert
        Assert.assertEquals(cachedExercises, result)
        verify(supabaseRepository, never()).getExercises()
        verify(databaseRepository, never()).insertExercises(remoteExercises)
    }

    @Test
    fun `invoke() with empty cache should fetch from remote, save and return`() = runTest {
        // Arrange
        whenever(databaseRepository.getExercises()).thenReturn(emptyList())
        whenever(supabaseRepository.getExercises()).thenReturn(remoteExercises)

        // Act
        val result = getExercisesUseCase()

        // Assert
        Assert.assertEquals(remoteExercises, result)
        verify(databaseRepository).insertExercises(remoteExercises) // Verify cache is updated
    }
}