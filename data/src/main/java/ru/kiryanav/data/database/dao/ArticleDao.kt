package ru.kiryanav.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    suspend fun insertCity(article: ArticleEntity)
}