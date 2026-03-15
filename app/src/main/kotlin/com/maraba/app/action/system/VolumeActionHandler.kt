package com.maraba.app.action.system

import android.content.Context
import android.media.AudioManager
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.data.model.Action
import com.maraba.app.data.model.VolumeStream
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * VolumeActionHandler — sets system volume for a specific stream.
 */
class VolumeActionHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : ActionHandler<Action.SetVolume> {

    override suspend fun execute(action: Action.SetVolume, context: ActionContext): ActionResult {
        val audioManager = this.context.getSystemService(AudioManager::class.java)
        val flags = if (action.showUI) AudioManager.FLAG_SHOW_UI else 0
        val stream = when (action.stream) {
            VolumeStream.RING -> AudioManager.STREAM_RING
            VolumeStream.MEDIA -> AudioManager.STREAM_MUSIC
            VolumeStream.ALARM -> AudioManager.STREAM_ALARM
            VolumeStream.NOTIFICATION -> AudioManager.STREAM_NOTIFICATION
        }
        audioManager.setStreamVolume(stream, action.level, flags)
        Timber.d("VolumeAction: stream=${action.stream} level=${action.level}")
        return ActionResult.Success
    }
}
