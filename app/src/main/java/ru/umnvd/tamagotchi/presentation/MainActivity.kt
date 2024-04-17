package ru.umnvd.tamagotchi.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.umnvd.tamagotchi.ui.theme.TamagotchiTheme


class MainActivity : ComponentActivity() {

    private val overlaySettingsLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ::onOverlaySettingsResult,
        )

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
            startTamagotchiAccessibilityService()
        }

        setContent {
            TamagotchiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    @Suppress("unused_parameter")
    private fun onOverlaySettingsResult(result: ActivityResult) {
        if (Settings.canDrawOverlays(this)) {
            Log.d("TEST", "onSettingsResult: Permission granted")
//            startTamagotchiService()
            startTamagotchiAccessibilityService()
        } else {
            Log.d("TEST", "onSettingsResult: Permission denied")
            overlaySettingsLauncher.launch(createOverlaySettingsIntent())
        }
    }

    @Suppress("unused_parameter")
    private fun onAccessibilitySettingsResult(result: ActivityResult) {
        if (Settings.canDrawOverlays(this)) {
            Log.d("TEST", "onSettingsResult: Permission granted")
//            startTamagotchiService()
            startTamagotchiAccessibilityService()
        } else {
            Log.d("TEST", "onSettingsResult: Permission denied")
            overlaySettingsLauncher.launch(createAccessibilitySettingsIntent())
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TamagotchiTheme {
        Greeting("Android")
    }
}