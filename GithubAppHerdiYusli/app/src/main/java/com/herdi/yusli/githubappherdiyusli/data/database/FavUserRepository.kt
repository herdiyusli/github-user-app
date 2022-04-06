package com.herdi.yusli.githubappherdiyusli.data.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mFavoriteDAO: FavoriteDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavUserRoomDatabase.getDatabase(application)
        mFavoriteDAO = db.favoriteDao()
    }
    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDAO.getAllUserFavorite()
    fun insertUserFav(username: String, id: Int, img_url: String) {
        executorService.execute { mFavoriteDAO.insertUserFavorite(Favorite(id,username,img_url)) }
    }
    fun checkIsFavorite(id: Int): LiveData<List<Favorite>> = mFavoriteDAO.checkIsFavorite(id)
    fun deleteUserFav(id: Int) {
        executorService.execute { mFavoriteDAO.deleteUserFavorite(id) }
    }

}