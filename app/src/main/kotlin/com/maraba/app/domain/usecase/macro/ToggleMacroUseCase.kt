package com.maraba.app.domain.usecase.macro

import com.maraba.app.data.repository.MacroRepository
import javax.inject.Inject

/**
 * ToggleMacroUseCase — enables or disables a macro.
 */
class ToggleMacroUseCase @Inject constructor(
    private val macroRepository: MacroRepository
) {
    suspend operator fun invoke(macroId: String, enabled: Boolean): Result<Unit> = runCatching {
        macroRepository.setEnabled(macroId, enabled)
    }
}
