package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorman.fitnessapp.data.models.room.ArticleEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<ArticleEntity>
    @Query("SELECT COUNT(*) FROM articles")
    suspend fun getArticlesCount(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoArticles(article: ArticleEntity)
    @Delete(entity = ArticleEntity::class)
    suspend fun deleteFromArticles(article: ArticleEntity)
    @Query("DELETE FROM articles")
    suspend fun deleteAllFromArticles()
}