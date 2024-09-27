package com.example.dateofbirth

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dateofbirth.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, month, dayofMonth ->

                val selectedDate = "$dayofMonth/${month + 1}/$year"
                binding.tvSelectedDate.text = selectedDate

                val simpleDF = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = simpleDF.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = simpleDF.parse(simpleDF.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val diffInMinutes = currentDate.time / 60000 - selectedDateInMinutes
                        binding.tvAgeInMins.text = diffInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 86400000
        datePickerDialog.show()
    }
}