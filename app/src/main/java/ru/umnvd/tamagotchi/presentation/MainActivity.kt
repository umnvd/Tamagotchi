package ru.umnvd.tamagotchi.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import ru.umnvd.tamagotchi.ui.theme.TamagotchiTheme
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds


class MainActivity : ComponentActivity() {

//    private val overlaySettingsLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult(),
//            ::onOverlaySettingsResult,
//        )

    private val accessibilitySettingsLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ::onAccessibilitySettingsResult,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Settings.canDrawOverlays(this)) {
            Log.d("TEST", "onCreate: Has no permission")
//            overlaySettingsLauncher.launch(createOverlaySettingsIntent())
            accessibilitySettingsLauncher.launch(createAccessibilitySettingsIntent())
        } else {
            Log.d("TEST", "onCreate: Has permission")
//            startTamagotchiService()
//            startTamagotchiAccessibilityService()
        }

        setContent {
            TamagotchiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        Button(
                            onClick = ::onScheduleServiceClick,
                        ) {
                            Text(text = "Schedule service")
                        }
                    }
                }
            }
        }
    }

    private fun onScheduleServiceClick() {
        val alarmManager = getSystemService<AlarmManager>() ?: return
        val intent = Intent(this, TamagotchiAccessibilityService::class.java)
        val pendingIntent = PendingIntent.getService(
            this,
            101,
            intent,
            PendingIntent.FLAG_IMMUTABLE + PendingIntent.FLAG_UPDATE_CURRENT,
        )
        Log.d("TEST", "MainActivity: set AlarmManager")
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 10.seconds.inWholeMilliseconds,
            pendingIntent,
        )
    }

//    @Suppress("unused_parameter")
//    private fun onOverlaySettingsResult(result: ActivityResult) {
//        if (Settings.canDrawOverlays(this)) {
//            Log.d("TEST", "onSettingsResult: Permission granted")
////            startTamagotchiService()
////            startTamagotchiAccessibilityService()
//        } else {
//            Log.d("TEST", "onSettingsResult: Permission denied")
//            overlaySettingsLauncher.launch(createOverlaySettingsIntent())
//        }
//    }

    @Suppress("unused_parameter")
    private fun onAccessibilitySettingsResult(result: ActivityResult) {
        if (Settings.canDrawOverlays(this)) {
            Log.d("TEST", "onSettingsResult: Permission granted")
//            startTamagotchiService()
//            startTamagotchiAccessibilityService()
        } else {
            Log.d("TEST", "onSettingsResult: Permission denied")
//            overlaySettingsLauncher.launch(createAccessibilitySettingsIntent())
        }
    }

    private fun createOverlaySettingsIntent(): Intent = Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.parse("package:$packageName")
    )

    private fun createAccessibilitySettingsIntent(): Intent = Intent(
        Settings.ACTION_ACCESSIBILITY_SETTINGS
    )

    private fun startTamagotchiService() {
        startService(Intent(this, TamagotchiService::class.java))
    }

    private fun startTamagotchiAccessibilityService() {
        startService(Intent(this, TamagotchiAccessibilityService::class.java))
    }
}
