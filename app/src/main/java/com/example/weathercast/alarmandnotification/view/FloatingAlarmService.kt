package com.example.weathercast.alarmandnotification.view

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.network.RemoteDataSource
import com.example.weathercast.R
import com.example.weathercast.data.localdatasource.SharedPreferencelLocationData
import com.example.weathercast.data.localdatasource.WeatherLocalDataSource
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.db.alert.AlarmLocallDataSource
import com.example.weathercast.db.location.LocationLocalDataSource
import com.example.weathercast.db.todayweather.TodayWeatherLocallDataSource
import com.example.weathercast.homeweather.viewmodel.HomeViewModel
import com.example.weathercast.homeweather.viewmodel.HomeViewModelFactory
import com.example.weathercast.viemodel.SettingViewModel
import com.example.weathercast.viemodel.SettingViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class FloatingAlarmService : Service() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private var mediaPlayer: MediaPlayer? = null
    private val CHANNEL_ID = "AlarmNotificationChannel"
    override fun onCreate() {
        super.onCreate()


        // Create a notification channel for Android O and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Weather Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        // Start the foreground service with a notification
        startForeground(1, createNotification())

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflate the floating view layout
        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_alarm, null)

        // Set up the alarm sound
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer.create(this, alarmUri)
        mediaPlayer?.start()

        // Set the layout parameters for the floating view
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            android.graphics.PixelFormat.TRANSLUCENT
        )

        // Set the position of the floating view (top of the screen)
        params.gravity = android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL
        params.x = 0
        params.y = 100 // Adjust Y position as needed

        // Add the floating view to the window
        windowManager.addView(floatingView, params)

        // Set up button click listener to dismiss the alarm
        val dismissButton: Button = floatingView.findViewById(R.id.dismissButton)
        dismissButton.setOnClickListener {
            stopAlarm()
            stopSelf()
        }
    }

    // Create a notification for the foreground service
    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, AlarmAndNotificationFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.cloud2) // Set a small icon
            .setContentTitle("Current Weather")
            .setContentText("Tap to dismiss")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    private fun stopAlarm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    override fun onDestroy() {
        super.onDestroy()
        if (::windowManager.isInitialized) {
            windowManager.removeView(floatingView) // Remove the floating view
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
