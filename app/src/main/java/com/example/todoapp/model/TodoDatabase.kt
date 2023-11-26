package com.example.todoapp.model

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapp.util.MIGRATION_1_2

@Database(entities = arrayOf(Todo::class), version = 2)
abstract class TodoDatabase:RoomDatabase() {
    abstract  fun todoDao(): TodoDao


    companion object{
        //singleton implementation

        @Volatile private var instance:TodoDatabase ?=null
        private val LOCK = Any()

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null")
            }
        }

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, TodoDatabase::class.java, "tododb")
                .addMigrations(MIGRATION_1_2)
                .build()


        operator fun invoke(context: Context){
            if(instance != null){
                synchronized(LOCK){
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }

//        operator fun invoke(context: Context){
//            if(instance != null){
//                synchronized(LOCK){
//                    instance ?= buildDatabase(context).also{
//                        instance = it
//                }
//                }
//            }
//        }


    }
}