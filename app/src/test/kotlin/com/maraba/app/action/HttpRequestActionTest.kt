package com.maraba.app.action

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.data.HttpRequestActionHandler
import com.maraba.app.data.model.Action
import com.maraba.app.data.model.HttpMethod
import com.maraba.app.domain.engine.VariableManager
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

/**
 * HttpRequestActionTest — unit tests for HTTP request action.
 * TODO: implement tests using OkHttp MockWebServer
 */
class HttpRequestActionTest {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var variableManager: VariableManager
    private lateinit var handler: HttpRequestActionHandler

    @BeforeEach
    fun setUp() {
        okHttpClient = OkHttpClient()
        variableManager = mockk()
        handler = HttpRequestActionHandler(okHttpClient, variableManager)
    }

    private fun makeContext() = ActionContext(
        macroId = UUID.randomUUID().toString(),
        macroName = "Test",
        resolveVariable = { it }
    )

    @Test
    fun `network error returns Failure`() = runTest {
        val action = Action.HttpRequest(
            id = UUID.randomUUID().toString(),
            method = HttpMethod.GET,
            url = "http://localhost:9999/invalid",
            responseVariable = null
        )
        val result = handler.execute(action, makeContext())
        assertTrue(result is ActionResult.Failure)
    }

    @Test
    fun `successful response returns Success`() = runTest {
        // TODO: implement with MockWebServer
    }
}
