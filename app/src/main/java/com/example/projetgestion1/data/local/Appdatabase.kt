package com.example.projetgestion1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projetgestion1.data.local.dao.CartDao
import com.example.projetgestion1.data.local.entities.CartItem

@Database(
    entities = [CartItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // ✅ Cette fonction doit exister pour que cartDao() fonctionne
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marketshop_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}