package com.maraba.app.domain.usecase.macro

import com.maraba.app.data.repository.MacroRepository
import javax.inject.Inject

/**
 * DeleteMacroUseCase — deletes a macro by id.
 */
class DeleteMacroUseCase @Inject constructor(
    private val macroRepository: MacroRepository
) {
    suspend operator fun invoke(macroId: String): Result<Unit> = runCatching {
        macroRepository.delete(macroId)
    }
}
