package ru.umnvd.tamagotchi.presentation

import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class TamagotchiAccessibilityService :
    AccessibilityService(),
    LifecycleOwner,
    SavedStateRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    private var windowManager: WindowManager? = null
    private var overlayView: View? = null

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override fun onCreate() {
        Log.d("TEST", "TamagotchiAccessibilityService: onCreate")
        super.onCreate()
        windowManager = getSystemService<WindowManager>()

        savedStateRegistryController.performAttach()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("TEST", "TamagotchiAccessibilityService: onServiceConnected")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("TEST", "TamagotchiAccessibilityService: onStartCommand")
        if (intent.hasExtra(SHOW_WINDOW_EXTRA_KEY)) {
            Log.d("TEST", "TamagotchiAccessibilityService: show window")
            showWindow()
        }
        if (intent.hasExtra(HIDE_WINDOW_EXTRA_KEY)) {
            Log.d("TEST", "TamagotchiAccessibilityService: hide window")
            hideWindow()
        }

        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("TEST", "TamagotchiAccessibilityService: onDestroy")
        super.onDestroy()
        hideWindow()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    private fun showWindow() {
        if (overlayView != null) return

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        overlayView = ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@TamagotchiAccessibilityService)
            setViewTreeSavedStateRegistryOwner(this@TamagotchiAccessibilityService)
            setContent {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .background(Color.Magenta),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Umka")
                }
            }
        }

        windowManager?.addView(overlayView, getLayoutParams())
    }

    private fun hideWindow() {
        if (overlayView == null) return

        windowManager?.removeView(overlayView)
        overlayView = null

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    private fun getLayoutParams(): WindowManager.LayoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT,
    ).apply {
        gravity = Gravity.START + Gravity.TOP
        x = 150
        y = 150
    }

    companion object {

        private const val SHOW_WINDOW_EXTRA_KEY = "show_window"
        private const val HIDE_WINDOW_EXTRA_KEY = "hide_window"

        fun getShowWindowIntent(context: Context): Intent =
            Intent(context, TamagotchiAccessibilityService::class.java).apply {
                putExtra(SHOW_WINDOW_EXTRA_KEY, true)
            }

        fun getHideWindowIntent(context: Context): Intent =
            Intent(context, TamagotchiAccessibilityService::class.java).apply {
                putExtra(HIDE_WINDOW_EXTRA_KEY, true)
            }
    }
}