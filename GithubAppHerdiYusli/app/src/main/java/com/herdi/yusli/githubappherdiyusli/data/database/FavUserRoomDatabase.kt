package com.herdi.yusli.githubappherdiyusli.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDAO

    companion object {
        @Volatile
        private var INSTANCE: FavUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavUserRoomDatabase::class.java, "favorite_database"
                    )
                        .build()
                }
            }
            return INSTANCE as FavUserRoomDatabase
        }
    }
}