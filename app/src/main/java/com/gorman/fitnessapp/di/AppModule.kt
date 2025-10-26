package com.gorman.fitnessapp.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.gorman.fitnessapp.BuildConfig
import com.gorman.fitnessapp.data.datasource.ai.AiApiClient
import com.gorman.fitnessapp.data.datasource.ai.GeminiApiClientModel
import com.gorman.fitnessapp.data.datasource.ai.GeminiGenerator
import com.gorman.fitnessapp.data.datasource.ai.GeminiGeneratorImpl
import com.gorman.fitnessapp.data.datasource.local.MySQLService
import com.gorman.fitnessapp.data.datasource.local.AppDatabase
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanItemDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanTemplateDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UserProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPI
import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPIImpl
import com.gorman.fitnessapp.data.repository.AiRepositoryImpl
import com.gorman.fitnessapp.data.repository.DatabaseRepositoryImpl
import com.gorman.fitnessapp.data.repository.FirebaseRepositoryImpl
import com.gorman.fitnessapp.data.repository.MealRepositoryImpl
import com.gorman.fitnessapp.data.repository.ProgramRepositoryImpl
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import com.gorman.fitnessapp.domain.repository.MealRepository
import com.gorman.fitnessapp.domain.repository.ProgramRepository
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.GetMealsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

private const val BASE_URL = "https://fitnessapp.42web.io/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMySQLService(retrofit: Retrofit): MySQLService =
        retrofit.create(MySQLService::class.java)

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideDatabaseReference(database: FirebaseDatabase): DatabaseReference {
        return database.getReference("FitnessApp")
    }

    @Provides
    @Singleton
    fun provideFirebaseAPI(db: DatabaseReference): FirebaseAPI =
        FirebaseAPIImpl(db)

    @Provides
    @Singleton
    fun provideFirebaseRepositoryImpl(api: FirebaseAPI): FirebaseRepository =
        FirebaseRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideDatabaseRepositoryImpl(usersDataDao: UsersDataDao,
                                      exerciseDao: ExerciseDao,
                                      userProgramDao: UserProgramDao,
                                      programExerciseDao: ProgramExerciseDao,
                                      programDao: ProgramDao,
                                      mealDao: MealDao,
                                      mealPlanTemplateDao: MealPlanTemplateDao,
                                      mealPlanItemDao: MealPlanItemDao): DatabaseRepository =
        DatabaseRepositoryImpl(usersDataDao, exerciseDao, userProgramDao, programExerciseDao, programDao, mealDao, mealPlanTemplateDao, mealPlanItemDao)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context,
            AppDatabase::class.java,
            "fitness_app_db")
            .fallbackToDestructiveMigration(false)
            .addCallback(AppDatabase.MIGRATION_CALLBACK)
            .build()

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
    fun provideProgramRepository(firebaseRepository: FirebaseRepository,
                                 aiRepository: AiRepository,
                                 databaseRepository: DatabaseRepository,
                                 getExercisesUseCase: GetExercisesUseCase): ProgramRepository =
        ProgramRepositoryImpl(firebaseRepository, aiRepository, databaseRepository, getExercisesUseCase)

    @Provides
    @Singleton
    fun provideMealRepository(getMealsUseCase: GetMealsUseCase,
                              aiRepository: AiRepository,
                              firebaseRepository: FirebaseRepository,
                              databaseRepository: DatabaseRepository): MealRepository =
        MealRepositoryImpl(getMealsUseCase, aiRepository, firebaseRepository, databaseRepository)
}