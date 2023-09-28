package me.dio.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.app.Activity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import me.dio.todolist.databinding.ActivityMainBinding
import me.dio.todolist.datasourse.TaskDataSourse


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertListeners()
    }

    private fun insertListeners() {
        binding.btnAdd.setOnClickListener {
            openAddTaskActivity.launch(Intent(this, AddTaskActivity::class.java))
        }

        adapter.listenerDelete = {
            TaskDataSourse.deleteTask(it)
            updateTask()
        }
    }

    private val openAddTaskActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            binding.rvTasks.adapter = adapter
            updateTask()
        }
    }

    private fun updateTask() {
        val list = TaskDataSourse.getList()
        binding.include.emptyState.visibility = if(list.isEmpty()) {
             View.VISIBLE
        } else {
            View.GONE
        }

        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}
