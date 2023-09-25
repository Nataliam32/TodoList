package me.dio.todolist.datasourse

import me.dio.todolist.model.Task


object TaskDataSourse {
    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task: Task) {
        list.add(task.copy(id = list.size + 1))
    }
}

