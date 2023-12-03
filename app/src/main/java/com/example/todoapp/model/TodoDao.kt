package com.example.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg todo: Todo)

    @Query("SELECT * FROM todo WHERE is_done = 0 ORDER BY priority DESC")
    fun all(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid = :id")
    fun find(id: Int): Todo

    @Query("DELETE FROM todo")
    fun deleteAll()

    @Delete
    fun delete(todo: Todo)

    @Query("UPDATE todo SET is_done = 1 WHERE uuid = :id")
    fun setDone(id: Int)
}