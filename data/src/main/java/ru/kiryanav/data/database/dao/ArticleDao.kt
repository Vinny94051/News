package ru.kiryanav.data.database.dao

import androidx.room.*
import ru.kiryanav.data.database.entites.ArticleEntity

@Dao
interface ArticleDao {
    /**
     * Get all entities which table contains
     * @return Flowable List of entities
     */
    @Query("SELECT * FROM articles")
    suspend fun getAll(): List<ArticleEntity>


    /**
     *Insert in the table one entity
     *@param article - entity which will be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: ArticleEntity)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)
}