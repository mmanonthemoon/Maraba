package com.maraba.app.data.model

import kotlinx.serialization.Serializable

/**
 * Macro — temel domain modeli (ADR-005)
 * Bir makro: trigger'lar → koşullar → aksiyonlar zinciridir.
 */
@Serializable
data class Macro(
    val id: String,                         // UUID
    val name: String,
    val description: String = "",
    val isEnabled: Boolean = true,
    val triggers: List<Trigger>,
    val conditions: List<Condition> = emptyList(),
    val actions: List<Action>,
    val createdAt: Long,
    val lastExecutedAt: Long? = null,
    val executionCount: Int = 0,
    val priority: Int = 0,                  // çakışmada öncelik, yüksek = önce
    val stopOnFailure: Boolean = false,      // bir aksiyon başarısız olursa dur
    val maxExecutionTimeMs: Long = 300_000L // 5 dakika timeout
)
