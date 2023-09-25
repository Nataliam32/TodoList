package me.dio.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.app.Activity
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
        updateTask()
        insertListeners()
    }

    private fun insertListeners() {
        binding.btnAdd.setOnClickListener {
            openAddTaskActivity.launch(Intent(this, AddTaskActivity::class.java))
        }
        adapter.listenerEdit = {
            Log.e("TAG", "insertListeners: + $it ", )
        }

        adapter.listenerDelete = {
            Log.e("TAG", "listenerDelete: + $it")
        }
    }

    private val openEditTaskActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Handle the result when the EditTaskActivity finishes
            // This code will run when the EditTaskActivity calls setResult(Activity.RESULT_OK)
            // You can update your task list or perform other actions here.
            binding.rvTasks.adapter = adapter
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
        adapter.submitList(TaskDataSourse.getList())
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}
