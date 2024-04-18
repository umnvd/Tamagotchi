package ru.umnvd.tamagotchi.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import ru.umnvd.tamagotchi.core.ui.theme.TamagotchiTheme
import kotlin.time.Duration.Companion.seconds


class MainActivity : ComponentActivity() {
    private val accessibilitySettingsLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ::onAccessibilitySettingsResult,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        if (!isAccessibilityServiceEnabled(this, TamagotchiAccessibilityService::class.java)) {
            Log.d("TEST", "onCreate: Accessibility service not enabled")
            accessibilitySettingsLauncher.launch(createAccessibilitySettingsIntent())
        } else {
            Log.d("TEST", "onCreate: Accessibility service enabled")
            startTamagotchiAccessibilityService()
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
                            onClick = ::onShowWindow,
                        ) {
                            Text(text = "Show")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = ::onHideWindow,
                        ) {
                            Text(text = "Hide")
                        }
                    }
                }
            }
        }
    }

    private fun onShowWindow() {
        Log.d("TEST", "onShowWindow")
        scheduleIntent(TamagotchiAccessibilityService.getShowWindowIntent(this))
    }

    private fun onHideWindow() {Log.d("TEST", "onHideWindow")
        scheduleIntent(TamagotchiAccessibilityService.getHideWindowIntent(this))
    }

    private fun scheduleIntent(intent: Intent) {
        val alarmManager = getSystemService<AlarmManager>() ?: return
        val pendingIntent = PendingIntent.getService(
            this,
            101,
            intent,
            PendingIntent.FLAG_IMMUTABLE + PendingIntent.FLAG_UPDATE_CURRENT,
        )
        Log.d("TEST", "scheduleIntent")
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 2.seconds.inWholeMilliseconds,
            pendingIntent,
        )
    }

    @Suppress("unused_parameter")
    private fun onAccessibilitySettingsResult(result: ActivityResult) {
        if (isAccessibilityServiceEnabled(this, TamagotchiAccessibilityService::class.java)) {
            Log.d("TEST", "onAccessibilitySettingsResult: Accessibility service enabled")
        } else {
            Log.d("TEST", "onAccessibilitySettingsResult: Accessibility service not enabled")
        }
    }

    private fun createAccessibilitySettingsIntent(): Intent = Intent(
        Settings.ACTION_ACCESSIBILITY_SETTINGS
    )

    private fun startTamagotchiAccessibilityService() {
        startService(Intent(this, TamagotchiAccessibilityService::class.java))
    }

    private fun isAccessibilityServiceEnabled(context: Context, service: Class<*>): Boolean {
        val componentName = ComponentName(context, service)
        val serviceId = componentName.flattenToShortString()

        val accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        return accessibilityManager
            .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK)
            .any { it.id == serviceId }
    }
}
