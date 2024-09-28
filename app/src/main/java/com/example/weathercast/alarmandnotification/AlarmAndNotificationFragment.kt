package com.example.weathercast.alarmandnotification

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.databinding.AddAlarmCustomDialogBinding
import com.example.weathercast.databinding.FragmentAlarmAndNotificationBinding
import com.example.weathercast.viemodel.ToolBarTextViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AlarmAndNotificationFragment : Fragment() {
    private val toolBarTextViewModel: ToolBarTextViewModel by activityViewModels()
    private lateinit var binding: AddAlarmCustomDialogBinding
    private lateinit var fragmentBinding: FragmentAlarmAndNotificationBinding

    private val OVERLAY_PERMISSION_REQUEST_CODE = 1234

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentAlarmAndNotificationBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            toolBarTextViewModel.setToolbarTitle("Alarm")
        }
        fragmentBinding.addButton.setOnClickListener {
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
            checkOverlayPermissionAndStartAlarm()
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

    // Function to check overlay permission
    private fun checkOverlayPermissionAndStartAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                // Ask for overlay permission
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${requireContext().packageName}")
                )
                startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE)
            } else {
                // Permission granted, proceed to start the alarm
                setAlarm()
//                startAlarmService()
            }
        } else {
            // If Android version is lower than M, start the alarm service directly
            setAlarm()
//            startAlarmService()
        }
    }

    // Handle the result of the overlay permission request
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            // Check if permission was granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(requireContext())) {
                    // Permission granted, start the alarm service
                    Toast.makeText(context, "Overlay permission granted", Toast.LENGTH_SHORT).show()
                    setAlarm()
                } else {
                    // Permission denied, show a message to the user
                    showOverlayPermissionDeniedMessage()
                }
            }
        }
    }

    private fun setAlarm() {
        var time1 = binding.textView6.text.toString()
        var date1 = binding.textView8.text.toString()
        var time2 = binding.textView7.text.toString()
        var date2 = binding.textView9.text.toString()
        Log.d("alarm", "setAlarm:$time1")
        Log.d("alarm", "setAlarm:$date1")
        Log.d("alarm", "setAlarm:$time2")
        Log.d("alarm", "setAlarm:$date2")

        val localDateTime = getLocalDateTimeFromText(date1, time1)
                val alertScheduler: AlertSchedulerInterface = AlertScheduler(requireContext())
        if (localDateTime != null) {
            alertScheduler.schedule(AlertItem(localDateTime,"city","country",3.5,5.5))
        }

    }


    // Function to convert Arabic numerals to English numerals
    fun convertArabicNumeralsToEnglish(input: String): String {
        val arabicNumerals = arrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
        val englishNumerals = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

        var output = input
        for (i in arabicNumerals.indices) {
            output = output.replace(arabicNumerals[i], englishNumerals[i])
        }
        return output
    }

    // Map English and Arabic month names to month numbers
    val monthMap = mapOf(
        "يناير" to 1, "Jan" to 1, "January" to 1,
        "فبراير" to 2, "Feb" to 2, "February" to 2,
        "مارس" to 3, "Mar" to 3, "March" to 3,
        "أبريل" to 4, "Apr" to 4, "April" to 4,
        "مايو" to 5, "May" to 5,
        "يونيو" to 6, "Jun" to 6, "June" to 6,
        "يوليو" to 7, "Jul" to 7, "July" to 7,
        "أغسطس" to 8, "Aug" to 8, "August" to 8,
        "سبتمبر" to 9, "Sep" to 9, "September" to 9,
        "أكتوبر" to 10, "Oct" to 10, "October" to 10,
        "نوفمبر" to 11, "Nov" to 11, "November" to 11,
        "ديسمبر" to 12, "Dec" to 12, "December" to 12
    )

    fun getLocalDateTimeFromText(dateText: String, timeText: String): LocalDateTime? {
        try {
            // Convert Arabic numerals in date and time
            val timeFormatted = convertArabicNumeralsToEnglish(timeText)
            val dateFormatted = convertArabicNumeralsToEnglish(dateText)

            // Parse the time (both 12-hour format with AM/PM for English/Arabic)
            val isAm = timeFormatted.contains("AM", true) || timeFormatted.contains("ص")
            val isPm = timeFormatted.contains("PM", true) || timeFormatted.contains("م")

            // Clean up time by removing AM/PM symbols
            val cleanedTime = timeFormatted.replace("AM", "", true)
                .replace("PM", "", true)
                .replace("ص", "").replace("م", "").trim()

            // Split time into hours and minutes
            val timeParts = cleanedTime.split(":")
            var hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()

            // Adjust for 12-hour format if PM
            if (isPm && hour < 12) hour += 12
            else if (isAm && hour == 12) hour = 0 // Midnight case

            // Split the date into day, month name, and year
            val dateParts = dateFormatted.split(",", " ").filter { it.isNotBlank() }
            val day = dateParts[0].toInt()  // First part is the day
            val monthText = dateParts[1]    // Second part is the month name
            val year = dateParts[2].toInt() // Third part is the year

            // Get the month number from the month name (both English and Arabic are handled)
            val month = monthMap[monthText] ?: throw IllegalArgumentException("Invalid month name: $monthText")

            // Return the LocalDateTime object
            return LocalDateTime.of(year, month, day, hour, minute)

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    private fun showOverlayPermissionDeniedMessage() {
        // Handle what to do when the permission is denied (e.g., show a toast or disable features)
        Toast.makeText(context, "Overlay permission denied", Toast.LENGTH_SHORT).show()
    }
}