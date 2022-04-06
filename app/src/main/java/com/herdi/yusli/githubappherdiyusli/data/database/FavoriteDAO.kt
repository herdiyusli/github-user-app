package com.herdi.yusli.githubappherdiyusli.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserFavorite(favorite: Favorite)

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    fun deleteUserFavorite(id: Int): Int

    @Query("SELECT * from user_favorite")
    fun getAllUserFavorite(): LiveData<List<Favorite>>

    @Query("SELECT  * from user_favorite WHERE id = :id")
    fun checkIsFavorite(id: Int): LiveData<List<Favorite>>
}