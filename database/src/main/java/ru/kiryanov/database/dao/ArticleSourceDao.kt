package ru.kiryanov.database.dao

import androidx.room.*
import ru.kiryanov.database.entites.ArticleSourceEntity

@Dao
interface ArticleSourceDao {

    @Query("SELECT * FROM sources")
    suspend fun getAll(): List<ArticleSourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(vararg sources: ArticleSourceEntity)

    @Delete
    suspend fun deleteSource(source : ArticleSourceEntity)
}