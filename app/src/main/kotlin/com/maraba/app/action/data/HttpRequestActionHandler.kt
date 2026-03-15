package com.maraba.app.action.data

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import com.maraba.app.data.model.HttpMethod
import com.maraba.app.domain.engine.VariableManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import javax.inject.Inject

/**
 * HttpRequestActionHandler — executes HTTP requests and optionally stores response.
 * TODO: implement full error handling, timeout configuration
 */
class HttpRequestActionHandler @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val variableManager: VariableManager
) : ActionHandler<Action.HttpRequest> {

    override suspend fun execute(action: Action.HttpRequest, context: ActionContext): ActionResult {
        val resolvedUrl = context.resolveVariable(action.url)
        val resolvedBody = action.body?.let { context.resolveVariable(it) }

        val requestBuilder = Request.Builder().url(resolvedUrl)
        action.headers.forEach { (key, value) -> requestBuilder.addHeader(key, value) }

        val request = when (action.method) {
            HttpMethod.GET -> requestBuilder.get().build()
            HttpMethod.POST -> requestBuilder.post(
                (resolvedBody ?: "").toRequestBody("application/json".toMediaType())
            ).build()
            HttpMethod.PUT -> requestBuilder.put(
                (resolvedBody ?: "").toRequestBody("application/json".toMediaType())
            ).build()
            HttpMethod.DELETE -> requestBuilder.delete().build()
            HttpMethod.PATCH -> requestBuilder.patch(
                (resolvedBody ?: "").toRequestBody("application/json".toMediaType())
            ).build()
        }

        return try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                action.responseVariable?.let { varName ->
                    val body = response.body?.string() ?: ""
                    variableManager.setUserVariable(varName, body)
                }
                Timber.d("HttpRequestAction: ${action.method} $resolvedUrl → ${response.code}")
                ActionResult.Success
            } else {
                ActionResult.Failure(
                    FailureReason.NETWORK_ERROR,
                    "HTTP ${response.code}: ${response.message}",
                    isRetryable = response.code >= 500
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "HttpRequestAction failed")
            ActionResult.Failure(FailureReason.NETWORK_ERROR, e.message ?: "Network error", isRetryable = true)
        }
    }
}
