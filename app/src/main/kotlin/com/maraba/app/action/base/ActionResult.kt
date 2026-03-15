package com.maraba.app.action.base

/**
 * ActionResult — sealed class for action execution outcome (ADR-006).
 * when() expression'larda else branch kullanılmaz.
 */
sealed class ActionResult {

    object Success : ActionResult()

    data class Failure(
        val reason: FailureReason,
        val message: String,
        val isRetryable: Boolean = false
    ) : ActionResult()
}

enum class FailureReason {
    NO_ACCESSIBILITY,       // Accessibility Service bağlı değil
    NO_PERMISSION,          // İzin yok
    ROOT_REQUIRED,          // Root gerekiyor
    ELEMENT_NOT_FOUND,      // UI element bulunamadı
    TIMEOUT,                // Zaman aşımı
    NETWORK_ERROR,          // HTTP isteği başarısız
    INVALID_PARAMETER,      // Geçersiz parametre
    SERVICE_UNAVAILABLE,    // Servis bağlı değil
    UNKNOWN                 // Bilinmeyen hata
}
