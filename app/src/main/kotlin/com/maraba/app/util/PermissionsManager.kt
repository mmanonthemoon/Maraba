package com.maraba.app.util

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.accessibility.AccessibilityManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * PermissionsManager — centralizes permission checks (ADR-008).
 * Checks status for each permission category.
 * Never requests permissions directly — only reports status.
 */
@Singleton
class PermissionsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /** Check if a runtime permission is granted */
    fun isGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    /** Check if AccessibilityService is enabled */
    fun isAccessibilityEnabled(): Boolean {
        val am = context.getSystemService(AccessibilityManager::class.java)
        val enabledServices = am?.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        val packageName = context.packageName
        return enabledServices?.any { it.resolveInfo.serviceInfo.packageName == packageName } == true
    }

    /** Check if NotificationListenerService is enabled */
    fun isNotificationListenerEnabled(): Boolean {
        val flat = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        ) ?: return false
        return flat.contains(context.packageName)
    }

    /** Check if WRITE_SETTINGS permission is granted */
    fun canWriteSettings(): Boolean = Settings.System.canWrite(context)

    /** Check if battery optimization is disabled for this app */
    fun isBatteryOptimizationDisabled(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
        val pm = context.getSystemService(android.os.PowerManager::class.java)
        return pm?.isIgnoringBatteryOptimizations(context.packageName) == true
    }

    /** Check if app can display over other apps */
    fun canDrawOverlays(): Boolean = Settings.canDrawOverlays(context)
}
