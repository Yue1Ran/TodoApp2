package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import com.example.todoapp.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val todoLD = MutableLiveData<Todo>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetch(uuid:Int){
        launch {
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }

    fun update(todo: Todo){
        launch {
            val db = buildDB(getApplication())
            db.todoDao().update(todo)
        }
    }

    fun addTodo(todo: Todo){
        launch {
            val db = buildDB(getApplication())
            db.todoDao().insertAll(todo)

            // terbaru
//            val db = buildDB(getApplication())
//            db.todoDao().update(title , notes, priority, uuid)
        }
    }
}