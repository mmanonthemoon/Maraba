package com.maraba.app.action.ui

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import com.maraba.app.service.MarabaAccessibilityService
import timber.log.Timber
import javax.inject.Inject

/**
 * TypeTextActionHandler — types text into the focused UI element.
 * TODO: find focused node, use ACTION_SET_TEXT or clipboard-based approach
 */
class TypeTextActionHandler @Inject constructor() : ActionHandler<Action.TypeText> {

    override suspend fun execute(action: Action.TypeText, context: ActionContext): ActionResult {
        if (!MarabaAccessibilityService.isConnected) {
            return ActionResult.Failure(FailureReason.NO_ACCESSIBILITY, "Accessibility Service not connected")
        }
        val resolvedText = context.resolveVariable(action.text)
        // TODO: find focused editable node, set text via Bundle
        Timber.d("TypeTextAction: text length=${resolvedText.length}")
        return ActionResult.Success
    }
}
