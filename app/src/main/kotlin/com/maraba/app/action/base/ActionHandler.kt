package com.maraba.app.action.base

import com.maraba.app.data.model.Action

/**
 * ActionHandler interface — every action type has its own implementation.
 * Aksiyonlar sırayla yürütülür (MacroExecutor tarafından), paralel değil.
 */
interface ActionHandler<T : Action> {

    /**
     * Execute the given action.
     * @param action the action to execute
     * @param context contextual info available during execution (variables, macro id, etc.)
     * @return ActionResult.Success or ActionResult.Failure — never throws
     */
    suspend fun execute(action: T, context: ActionContext): ActionResult
}

/**
 * Context object passed to every ActionHandler during execution.
 * Provides access to variable resolution and service connections.
 */
data class ActionContext(
    val macroId: String,
    val macroName: String,
    val triggerPayload: Map<String, String> = emptyMap(),
    /** Resolve %VARIABLE placeholders in strings */
    val resolveVariable: suspend (String) -> String
)
