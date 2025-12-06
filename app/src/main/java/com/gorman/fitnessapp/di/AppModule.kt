package com.gorman.fitnessapp.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.gorman.fitnessapp.BuildConfig
import com.gorman.fitnessapp.data.datasource.ai.AiApiClient
import com.gorman.fitnessapp.data.datasource.ai.GeminiApiClientModel
import com.gorman.fitnessapp.data.datasource.ai.GeminiGenerator
import com.gorman.fitnessapp.data.datasource.ai.GeminiGeneratorImpl
import com.gorman.fitnessapp.data.datasource.local.AppDatabase
import com.gorman.fitnessapp.data.datasource.local.dao.ArticleDao
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanItemDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanTemplateDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UserProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.UserProgressDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.datasource.local.dao.WorkoutHistoryDao
import com.gorman.fitnessapp.data.datasource.remote.PostgreSQLService
import com.gorman.fitnessapp.data.datasource.remote.PostgreSQLServiceImpl
import com.gorman.fitnessapp.data.datasource.remote.Storage
import com.gorman.fitnessapp.data.datasource.remote.StorageImpl
import com.gorman.fitnessapp.data.repository.AiRepositoryImpl
import com.gorman.fitnessapp.data.repository.DatabaseRepositoryImpl
import com.gorman.fitnessapp.data.repository.SupabaseRepositoryImpl
import com.gorman.fitnessapp.data.repository.MealRepositoryImpl
import com.gorman.fitnessapp.data.repository.ProgramRepositoryImpl
import com.gorman.fitnessapp.data.repository.SettingsRepositoryImpl
import com.gorman.fitnessapp.data.repository.StorageRepositoryImpl
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import com.gorman.fitnessapp.domain.repository.MealRepository
import com.gorman.fitnessapp.domain.repository.ProgramRepository
import com.gorman.fitnessapp.domain.repository.SettingsRepository
import com.gorman.fitnessapp.domain.repository.StorageRepository
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.GetMealsUseCase
import com.gorman.fitnessapp.domain.usecases.SetMealsIdUseCase
import com.gorman.fitnessapp.domain.usecases.SetProgramIdUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.logger.AppLoggerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    @Singleton
    fun provideStorage(storage: FirebaseStorage, logger: AppLogger): Storage =
        StorageImpl(storage, logger)

    @Provides
    @Singleton
    fun provideStorageRepository(storage: Storage): StorageRepository =
        StorageRepositoryImpl(storage)

    @Provides
    @Singleton
    fun providePostgreSQLService(client: SupabaseClient, logger: AppLogger): PostgreSQLService =
        PostgreSQLServiceImpl(client, logger)

    @Provides
    @Singleton
    fun provideSupabaseRepositoryImpl(api: PostgreSQLService): SupabaseRepository =
        SupabaseRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideDatabaseRepositoryImpl(usersDataDao: UsersDataDao,
                                      exerciseDao: ExerciseDao,
                                      userProgramDao: UserProgramDao,
                                      programExerciseDao: ProgramExerciseDao,
                                      programDao: ProgramDao,
                                      mealDao: MealDao,
                                      mealPlanTemplateDao: MealPlanTemplateDao,
                                      mealPlanItemDao: MealPlanItemDao,
                                      userProgressDao: UserProgressDao,
                                      workoutHistoryDao: WorkoutHistoryDao,
                                      articleDao: ArticleDao): DatabaseRepository {
        return DatabaseRepositoryImpl(
            usersDataDao,
            exerciseDao,
            userProgramDao,
            programExerciseDao,
            programDao,
            mealDao,
            mealPlanTemplateDao,
            mealPlanItemDao,
            userProgressDao,
            workoutHistoryDao,
            articleDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context,
            AppDatabase::class.java,
            "fitness_app_db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    @Singleton
    fun provideAppLogger(): AppLogger = AppLoggerImpl()

    @Provides
    @Singleton
    fun provideUsersDataDao(db: AppDatabase): UsersDataDao = db.usersDataDao()

    @Provides
    @Singleton
    fun provideExerciseDao(db: AppDatabase): ExerciseDao = db.exerciseDao()

    @Provides
    @Singleton
    fun provideProgramDao(db: AppDatabase): ProgramDao = db.programDao()

    @Provides
    @Singleton
    fun provideProgramExerciseDao(db: AppDatabase): ProgramExerciseDao = db.programExerciseDao()

    @Provides
    @Singleton
    fun provideUserProgramDao(db: AppDatabase): UserProgramDao = db.userProgramDao()

    @Provides
    @Singleton
    fun provideMealDao(db: AppDatabase): MealDao = db.mealDao()

    @Provides
    @Singleton
    fun provideMealPlanItemDao(db: AppDatabase): MealPlanItemDao = db.mealPlanItemDao()

    @Provides
    @Singleton
    fun provideMealPlanTemplateDao(db: AppDatabase): MealPlanTemplateDao = db.mealPlanTemplateDao()

    @Provides
    @Singleton
    fun provideUserProgressDao(db: AppDatabase): UserProgressDao = db.userProgressDao()

    @Provides
    @Singleton
    fun provideWorkoutHistoryDao(db: AppDatabase): WorkoutHistoryDao = db.workoutHistoryDao()

    @Provides
    @Singleton
    fun provideArticleHistoryDao(db: AppDatabase): ArticleDao = db.articleDao()

    @Provides
    @Singleton
    fun provideGeminiApiKey(): String =
        BuildConfig.GEMINI_API

    @Provides
    @Singleton
    fun provideGeminiApiClientModel(apiKey: String): AiApiClient =
        GeminiApiClientModel(apiKey)

    @Provides
    @Singleton
    fun provideGeminiGenerator(aiApiClient: AiApiClient): GeminiGenerator =
        GeminiGeneratorImpl(aiApiClient)

    @Provides
    @Singleton
    fun provideAiRepository(geminiGenerator: GeminiGenerator): AiRepository =
        AiRepositoryImpl(geminiGenerator)

    @Provides
    @Singleton
    fun provideProgramRepository(supabaseRepository: SupabaseRepository,
                                 aiRepository: AiRepository,
                                 databaseRepository: DatabaseRepository,
                                 getExercisesUseCase: GetExercisesUseCase,
                                 setProgramIdUseCase: SetProgramIdUseCase): ProgramRepository =
        ProgramRepositoryImpl(supabaseRepository, aiRepository, databaseRepository, getExercisesUseCase, setProgramIdUseCase)

    @Provides
    @Singleton
    fun provideMealRepository(getMealsUseCase: GetMealsUseCase,
                              aiRepository: AiRepository,
                              supabaseRepository: SupabaseRepository,
                              databaseRepository: DatabaseRepository,
                              setMealsIdUseCase: SetMealsIdUseCase): MealRepository =
        MealRepositoryImpl(getMealsUseCase, aiRepository, supabaseRepository, databaseRepository, setMealsIdUseCase)

    @Provides
    @Singleton
    fun provideSettingRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }
}