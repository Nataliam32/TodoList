package me.dio.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import me.dio.todolist.databinding.ActivityAddTaskBinding
import me.dio.todolist.datasourse.TaskDataSourse
import me.dio.todolist.extensions.format
import me.dio.todolist.extensions.text
import me.dio.todolist.model.Task
import java.util.Date
import java.util.TimeZone
import javax.security.auth.login.LoginException

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }
    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time)* -1
                binding.tilDate.text = Date(it + offSet).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
        
        binding.tilHour.editText?.setOnClickListener { 
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnCreate.setOnClickListener {
            val task = Task(
                title = binding.title.text,
                description = binding.tilDescription.text,
                date = binding.tilDate.text,
                hour = binding.tilHour.text
            )
            TaskDataSourse.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
            Log.e("TAG", "insertListeners: " + TaskDataSourse.getList())
        }
    }
}