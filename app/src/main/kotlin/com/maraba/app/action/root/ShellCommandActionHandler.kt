package com.maraba.app.action.root

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import com.maraba.app.util.RootChecker
import timber.log.Timber
import javax.inject.Inject

/**
 * ShellCommandActionHandler — executes shell command via su.
 * Returns ActionResult.Failure(ROOT_REQUIRED) if device is not rooted.
 * TODO: implement su process execution, capture output
 */
class ShellCommandActionHandler @Inject constructor(
    private val rootChecker: RootChecker
) : ActionHandler<Action.ShellCommand> {

    override suspend fun execute(action: Action.ShellCommand, context: ActionContext): ActionResult {
        if (!rootChecker.isRooted()) {
            return ActionResult.Failure(FailureReason.ROOT_REQUIRED, "Root access required for shell commands")
        }
        val resolvedCommand = context.resolveVariable(action.command)
        // TODO: execute via Runtime.exec("su -c $resolvedCommand"), capture stdout
        Timber.d("ShellCommandAction: executing command (root)")
        return ActionResult.Success
    }
}
