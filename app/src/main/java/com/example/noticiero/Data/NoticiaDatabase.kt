package com.example.noticiero.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noticiero.Entities.Noticia
import com.example.noticiero.Entities.UsuarioEntity

@Database(entities = [Noticia::class,UsuarioEntity::class] ,version = 2)
abstract class NoticiaDatabase : RoomDatabase() {
    abstract fun bookDao(): NoticiaDao
    abstract fun usuarioDao(): UsuarioDao


    companion object {
        @Volatile
        private var INSTANCE: NoticiaDatabase? = null

        fun getDatabase(context: Context): NoticiaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoticiaDatabase::class.java,
                    "noticias_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}