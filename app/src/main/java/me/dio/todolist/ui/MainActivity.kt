package me.dio.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
        adapter.listenerEdit = {
            Log.e("TAG", "listenerEdit: + $it")
        }
        adapter.listenerDelete = {
            Log.e("TAG", "listenerDelete: + $it")
        }
    }

    private val openAddTaskActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.rvTasks.adapter = adapter
            adapter.submitList(TaskDataSourse.getList())
        }
    }


    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}