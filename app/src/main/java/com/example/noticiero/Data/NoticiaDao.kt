package com.example.noticiero.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.noticiero.Entities.Noticia

@Dao
interface NoticiaDao {
    @Insert
    suspend fun addBook(noticia: Noticia)

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Noticia>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): Noticia?

    @Update
    suspend fun updateBook(noticia: Noticia)

    @Delete
    suspend fun deleteBook(noticia: Noticia)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()

}