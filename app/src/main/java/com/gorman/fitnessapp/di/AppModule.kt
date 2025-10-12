package com.gorman.fitnessapp.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.gorman.fitnessapp.data.datasource.MySQLService
import com.gorman.fitnessapp.data.datasource.local.AppDatabase
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPI
import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPIImpl
import com.gorman.fitnessapp.data.repository.DatabaseRepositoryImpl
import com.gorman.fitnessapp.data.repository.FirebaseRepositoryImpl
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
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
    fun provideDatabaseRepositoryImpl(usersDataDao: UsersDataDao, exerciseDao: ExerciseDao): DatabaseRepository =
        DatabaseRepositoryImpl(usersDataDao, exerciseDao)

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
}