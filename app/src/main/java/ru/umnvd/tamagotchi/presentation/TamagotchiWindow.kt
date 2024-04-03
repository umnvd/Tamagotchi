package ru.umnvd.tamagotchi.presentation

import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.getSystemService
import ru.umnvd.tamagotchi.R
import kotlin.math.roundToInt

class TamagotchiWindow(private val context: Context) {

    private val windowManager: WindowManager? = context.getSystemService()
    private val layoutInflater: LayoutInflater? = context.getSystemService()

    // TODO: learn params
    private val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
//        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT,
    ).apply {
        gravity = Gravity.START + Gravity.TOP
        x = dpToPx(16)
        y = dpToPx(96)
    }
    private val view: View? = layoutInflater?.inflate(R.layout.tamagotchi_window, null)

    fun open() {
        try {
            if (view?.windowToken == null && view?.parent == null) {
                windowManager?.addView(view, layoutParams)

            }
        } catch (e: Exception) {
            Log.d("TEST", e.toString())
        }
    }

    fun close() {
        try {
            windowManager?.removeView(view)
            view?.invalidate()
            (view?.parent as ViewGroup).removeAllViews()
        } catch (e: Exception) {
            Log.d("TEST", e.toString())
        }
    }

    private fun dpToPx(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            context.resources.displayMetrics,
        ).roundToInt()
    }
}