package com.example.weathercast.alarmandnotification

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weathercast.R
import com.example.weathercast.databinding.AddAlarmCustomDialogBinding
import com.example.weathercast.viemodel.ToolBarTextViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AlarmAndNotificationFragment : Fragment() {
    private val toolBarTextViewModel: ToolBarTextViewModel by activityViewModels()
    private lateinit var binding: AddAlarmCustomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_and_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            toolBarTextViewModel.setToolbarTitle("Alarm")
        }
        val addButton = view.findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {
            showDialog()
        }
    }


    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding = AddAlarmCustomDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        dialog.setContentView(binding.root)

        // Set current date and time
        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val currentDate = SimpleDateFormat("dd, MMM yyyy", Locale.getDefault()).format(Date())

        binding.textView6.text = currentTime
        binding.textView8.text = currentDate
        binding.textView7.text = currentTime
        binding.textView9.text = currentDate

        // Set click listeners for frame1 and frame2
        binding.frame1.setOnClickListener {
            showDateTimePicker(binding.textView6, binding.textView8)
        }

        binding.frame2.setOnClickListener {
            showDateTimePicker(binding.textView7, binding.textView9)
        }

        // Set dialog views properties here
        binding.button.setOnClickListener {
            // Handle button click here
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun showDateTimePicker(timeTextView: TextView, dateTextView: TextView) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
            timeTextView.text = time

            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = SimpleDateFormat("dd, MMM yyyy", Locale.getDefault()).format(calendar.time)
                dateTextView.text = date
            }, year, month, day)
            datePickerDialog.show()
        }, hour, minute, false)

        timePickerDialog.show()
    }
}