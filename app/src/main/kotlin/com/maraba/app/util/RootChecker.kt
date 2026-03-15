package com.maraba.app.util

import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * RootChecker — detects if the device is rooted.
 * Used by root-required action handlers.
 * TODO: add more robust detection methods (su binary path, SuperSU, Magisk)
 */
@Singleton
class RootChecker @Inject constructor() {

    private var cachedResult: Boolean? = null

    fun isRooted(): Boolean {
        cachedResult?.let { return it }
        val result = checkRootPaths() || checkSuCommand()
        cachedResult = result
        return result
    }

    private fun checkRootPaths(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        return paths.any { File(it).exists() }
    }

    private fun checkSuCommand(): Boolean = try {
        Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
        true
    } catch (e: Exception) {
        Timber.d("Root check: su command not available")
        false
    }
}
